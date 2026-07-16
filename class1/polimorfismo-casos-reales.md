# Polimorfismo con Interfaces: Casos Reales de la Industria

Material de apoyo — Programación Orientada a Objetos

Estos ejemplos evitan los clásicos "Animal/Figura/Vehículo" de los libros y muestran cómo las interfaces se usan **en sistemas reales**: pasarelas de pago, notificaciones, persistencia de datos y exportación de reportes.

---

## Caso 1: Pasarela de pagos en un e-commerce 💳

**Problema real:** una tienda online acepta tarjeta, PIX/transferencia y MercadoPago. Mañana el negocio quiere agregar PayPal. El código de checkout **no puede cambiar** cada vez que se agrega un medio de pago.

```java
public interface ProcesadorPago {
    ResultadoPago cobrar(Pedido pedido);
    boolean soportaReembolso();
}
```

```java
public class PagoTarjeta implements ProcesadorPago {
    private final GatewayTarjeta gateway;

    public PagoTarjeta(GatewayTarjeta gateway) {
        this.gateway = gateway;
    }

    @Override
    public ResultadoPago cobrar(Pedido pedido) {
        // Tokeniza la tarjeta y autoriza contra el banco
        String token = gateway.tokenizar(pedido.getDatosTarjeta());
        boolean autorizado = gateway.autorizar(token, pedido.getTotal());
        return autorizado
            ? ResultadoPago.aprobado("AUTH-" + token)
            : ResultadoPago.rechazado("Fondos insuficientes");
    }

    @Override
    public boolean soportaReembolso() {
        return true;
    }
}
```

```java
public class PagoTransferencia implements ProcesadorPago {

    @Override
    public ResultadoPago cobrar(Pedido pedido) {
        // Genera un código y espera confirmación bancaria (asíncrono)
        String codigo = GeneradorCodigos.nuevo();
        return ResultadoPago.pendiente(codigo);
    }

    @Override
    public boolean soportaReembolso() {
        return false; // el reembolso es manual en este medio
    }
}
```

**Aquí está el polimorfismo:** el servicio de checkout trabaja con el **contrato**, nunca con la implementación concreta.

```java
public class ServicioCheckout {

    public void finalizarCompra(Pedido pedido, ProcesadorPago procesador) {
        ResultadoPago resultado = procesador.cobrar(pedido); // ← polimorfismo

        switch (resultado.getEstado()) {
            case APROBADO -> pedido.marcarPagado(resultado.getComprobante());
            case PENDIENTE -> pedido.marcarEsperandoPago();
            case RECHAZADO -> pedido.marcarFallido(resultado.getMotivo());
        }
    }
}
```

```java
// El mismo método funciona con cualquier medio de pago:
checkout.finalizarCompra(pedido, new PagoTarjeta(gateway));
checkout.finalizarCompra(pedido, new PagoTransferencia());
checkout.finalizarCompra(pedido, new PagoMercadoPago(credenciales));
```

> 💡 **Para discutir en clase:** ¿cuántas líneas de `ServicioCheckout` hay que cambiar para agregar PayPal? **Cero.** Ese es el valor del polimorfismo.

---

## Caso 2: Sistema de notificaciones 📬

**Problema real:** una app de delivery avisa al cliente cuando su pedido sale. Algunos usuarios prefieren email, otros SMS, otros push. Además, el equipo de marketing quiere agregar WhatsApp el próximo trimestre.

```java
public interface CanalNotificacion {
    void enviar(Usuario destinatario, Mensaje mensaje);
    boolean estaDisponiblePara(Usuario usuario);
}
```

```java
public class CanalEmail implements CanalNotificacion {
    private final ServidorSmtp smtp;

    public CanalEmail(ServidorSmtp smtp) { this.smtp = smtp; }

    @Override
    public void enviar(Usuario destinatario, Mensaje mensaje) {
        smtp.enviarCorreo(
            destinatario.getEmail(),
            mensaje.getTitulo(),
            PlantillaHtml.renderizar(mensaje) // email va con HTML
        );
    }

    @Override
    public boolean estaDisponiblePara(Usuario usuario) {
        return usuario.getEmail() != null && usuario.emailVerificado();
    }
}
```

```java
public class CanalPush implements CanalNotificacion {
    private final ServicioPush firebase;

    public CanalPush(ServicioPush firebase) { this.firebase = firebase; }

    @Override
    public void enviar(Usuario destinatario, Mensaje mensaje) {
        // Push tiene límite de caracteres: se adapta el contenido
        String textoCorto = mensaje.getCuerpo().length() > 120
            ? mensaje.getCuerpo().substring(0, 117) + "..."
            : mensaje.getCuerpo();
        firebase.enviar(destinatario.getTokenDispositivo(), textoCorto);
    }

    @Override
    public boolean estaDisponiblePara(Usuario usuario) {
        return usuario.getTokenDispositivo() != null;
    }
}
```

**Polimorfismo en acción — un solo bucle atiende todos los canales:**

```java
public class NotificadorPedidos {
    private final List<CanalNotificacion> canales;

    public NotificadorPedidos(List<CanalNotificacion> canales) {
        this.canales = canales;
    }

    public void notificarPedidoEnCamino(Usuario usuario, Pedido pedido) {
        Mensaje mensaje = Mensaje.de("¡Tu pedido va en camino! 🛵",
                                      "Pedido #" + pedido.getNumero());

        for (CanalNotificacion canal : canales) {        // ← polimorfismo
            if (canal.estaDisponiblePara(usuario)) {
                canal.enviar(usuario, mensaje);
            }
        }
    }
}
```

> 💡 **Detalle profesional:** cada canal **adapta el mensaje a su medio** (HTML para email, texto corto para push). El contrato es el mismo; el comportamiento, distinto. Eso *es* polimorfismo.

---

## Caso 3: Repositorio de datos — el truco que hace posible el testing 🧪

**Problema real:** el equipo guarda usuarios en PostgreSQL. Pero los tests automáticos no pueden depender de una base de datos real (lentos, frágiles). Solución usada en prácticamente toda la industria: el **patrón Repository** con interfaz.

```java
public interface RepositorioUsuarios {
    void guardar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> listarActivos();
}
```

**Implementación de producción (base de datos):**

```java
public class RepositorioUsuariosPostgres implements RepositorioUsuarios {
    private final ConexionBD conexion;

    public RepositorioUsuariosPostgres(ConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(Usuario usuario) {
        conexion.ejecutar(
            "INSERT INTO usuarios (email, nombre, activo) VALUES (?, ?, ?)",
            usuario.getEmail(), usuario.getNombre(), usuario.isActivo()
        );
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return conexion.consultarUno(
            "SELECT * FROM usuarios WHERE email = ?", email
        ).map(MapeadorUsuario::desdeFila);
    }

    @Override
    public List<Usuario> listarActivos() {
        return conexion.consultar("SELECT * FROM usuarios WHERE activo = true")
                       .stream()
                       .map(MapeadorUsuario::desdeFila)
                       .toList();
    }
}
```

**Implementación para tests (en memoria, sin base de datos):**

```java
public class RepositorioUsuariosEnMemoria implements RepositorioUsuarios {
    private final Map<String, Usuario> datos = new HashMap<>();

    @Override
    public void guardar(Usuario usuario) {
        datos.put(usuario.getEmail(), usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(datos.get(email));
    }

    @Override
    public List<Usuario> listarActivos() {
        return datos.values().stream()
                    .filter(Usuario::isActivo)
                    .toList();
    }
}
```

**El servicio de negocio no sabe (ni le importa) dónde se guardan los datos:**

```java
public class ServicioRegistro {
    private final RepositorioUsuarios repositorio; // ← el tipo es la INTERFAZ

    public ServicioRegistro(RepositorioUsuarios repositorio) {
        this.repositorio = repositorio;
    }

    public void registrar(String email, String nombre) {
        if (repositorio.buscarPorEmail(email).isPresent()) {
            throw new IllegalStateException("Email ya registrado");
        }
        repositorio.guardar(new Usuario(email, nombre));
    }
}
```

```java
// En producción:
var servicio = new ServicioRegistro(new RepositorioUsuariosPostgres(conexion));

// En los tests: ¡misma clase, cero base de datos!
var servicio = new ServicioRegistro(new RepositorioUsuariosEnMemoria());
```

> 💡 **Este es probablemente el uso más frecuente de interfaces en el mundo laboral.** Frameworks como Spring se construyen alrededor de esta idea (inyección de dependencias).

---

## Caso 4: Exportación de reportes 📄

**Problema real:** el área de finanzas pide el reporte de ventas en Excel; contabilidad lo quiere en CSV para importarlo a otro sistema; gerencia lo quiere en PDF para imprimir.

```java
public interface ExportadorReporte {
    byte[] exportar(ReporteVentas reporte);
    String extensionArchivo();
    String tipoMime();
}
```

```java
public class ExportadorCsv implements ExportadorReporte {

    @Override
    public byte[] exportar(ReporteVentas reporte) {
        StringBuilder csv = new StringBuilder("fecha,producto,monto\n");
        for (Venta v : reporte.getVentas()) {
            csv.append(v.getFecha()).append(",")
               .append(v.getProducto()).append(",")
               .append(v.getMonto()).append("\n");
        }
        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String extensionArchivo() { return ".csv"; }

    @Override
    public String tipoMime() { return "text/csv"; }
}
```

```java
public class ExportadorPdf implements ExportadorReporte {
    private final MotorPdf motor; // librería como iText/OpenPDF

    public ExportadorPdf(MotorPdf motor) { this.motor = motor; }

    @Override
    public byte[] exportar(ReporteVentas reporte) {
        DocumentoPdf doc = motor.nuevoDocumento();
        doc.agregarTitulo("Reporte de Ventas — " + reporte.getPeriodo());
        doc.agregarTabla(reporte.getVentas());
        doc.agregarPie("Total: $" + reporte.getTotal());
        return doc.generarBytes();
    }

    @Override
    public String extensionArchivo() { return ".pdf"; }

    @Override
    public String tipoMime() { return "application/pdf"; }
}
```

**El endpoint HTTP elige la implementación en tiempo de ejecución:**

```java
public class ControladorReportes {
    private final Map<String, ExportadorReporte> exportadores = Map.of(
        "csv",  new ExportadorCsv(),
        "pdf",  new ExportadorPdf(motorPdf),
        "xlsx", new ExportadorExcel()
    );

    public Respuesta descargar(String formato, ReporteVentas reporte) {
        ExportadorReporte exportador = exportadores.get(formato);
        if (exportador == null) {
            return Respuesta.error(400, "Formato no soportado: " + formato);
        }

        byte[] archivo = exportador.exportar(reporte);   // ← polimorfismo
        return Respuesta.archivo(
            "ventas" + exportador.extensionArchivo(),
            exportador.tipoMime(),
            archivo
        );
    }
}
```

> 💡 **Observa el `Map<String, ExportadorReporte>`:** la implementación se elige **en tiempo de ejecución** según lo que pida el usuario. Sin `if/else` gigantes, sin tocar el controlador para agregar formatos.

---

## Bonus: interfaces que ya usas sin saberlo 🔍

El polimorfismo con interfaces está **dentro del propio Java**:

```java
// List es una INTERFAZ. ArrayList y LinkedList son implementaciones.
List<String> nombres = new ArrayList<>();
nombres = new LinkedList<>();   // el resto del código no cambia

// Runnable es una interfaz de un solo método: la base de los hilos
Runnable tarea = () -> System.out.println("Ejecutando en otro hilo");
new Thread(tarea).start();

// Comparator: cambiar el criterio de orden sin tocar la clase
usuarios.sort(Comparator.comparing(Usuario::getNombre));
usuarios.sort(Comparator.comparing(Usuario::getFechaRegistro).reversed());
```

Cuando declaras `List<String> nombres` en vez de `ArrayList<String> nombres`, **ya estás programando contra interfaces** — el mismo principio de los 4 casos anteriores.

---

## Síntesis para la pizarra 🧠

| Caso | Interfaz | Lo que varía | Lo que NO cambia |
|------|----------|--------------|------------------|
| Pagos | `ProcesadorPago` | tarjeta, transferencia, MercadoPago | el checkout |
| Notificaciones | `CanalNotificacion` | email, SMS, push | el notificador |
| Persistencia | `RepositorioUsuarios` | Postgres, memoria | la lógica de negocio |
| Reportes | `ExportadorReporte` | CSV, PDF, Excel | el controlador |

**La regla de oro:** *programa contra la interfaz, no contra la implementación.*
El código que **usa** el contrato queda estable; solo crecen las implementaciones.
