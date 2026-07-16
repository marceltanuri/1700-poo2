---
marp: true
theme: default
paginate: true
size: 16:9
header: "POO — Interfaces"
footer: "Aula 2 · Programación Orientada a Objetos"
style: |
  section {
    font-size: 26px;
  }
  section.lead h1 {
    font-size: 60px;
  }
  code {
    font-size: 0.9em;
  }
  h1 {
    color: #1a5276;
  }
  h2 {
    color: #2874a6;
  }
---

<!-- _class: lead -->
<!-- _paginate: false -->

# Interfaces en POO

## Aula 2: Cómo implementar y utilizar interfaces

**Programación Orientada a Objetos**
Curso para principiantes

---

# Repaso rápido de la Aula 1 🔄

- Una interfaz es un **contrato**: define *qué* debe hacer una clase
- Se cumple el contrato con la palabra clave `implements`
- La interfaz puede usarse como **tipo** → polimorfismo
- Ejemplo real: `Comparable` para ordenar objetos

**Pregunta de calentamiento:** ¿qué devuelve `compareTo`? 🤔

---

# Objetivos de la clase

Al final de esta clase, serás capaz de:

- **Crear** tus propias interfaces desde cero
- **Implementar** una interfaz en una o varias clases
- **Diferenciar** interfaces de clases abstractas
- **Decidir** cuándo usar cada una

---

# Agenda

1. 📖 **Expositiva:** Implementación de interfaces
2. 🚗 **Estudio de caso:** Interfaz `Vehiculo`
3. 📖 **Expositiva:** Interfaces vs. Clases Abstractas
4. 🧭 **Investigación individual:** ¿Qué más se puede hacer con interfaces?
5. 💻 **Práctica:** Ejercicios de fijación

---

<!-- _class: lead -->

# Parte 1
## Cómo implementar una interfaz

---

# Paso 1: Definir la interfaz

Usa la palabra clave `interface`:

```java
public interface Vehiculo {
    void encender();
    void acelerar(int incremento);
    void desacelerar(int decremento);
    void apagar();
}
```

📌 Reglas importantes:
- Los métodos son **públicos y abstractos** por defecto
- **No** tienen cuerpo (terminan en `;`)
- Una interfaz **no puede ser instanciada**: `new Vehiculo()` ❌

---

# Paso 2: Implementar en una clase

```java
public class Auto implements Vehiculo {
    private int velocidad = 0;
    private boolean encendido = false;

    @Override
    public void encender() {
        encendido = true;
        System.out.println("🚗 Motor encendido");
    }

    @Override
    public void acelerar(int incremento) {
        if (encendido) velocidad += incremento;
        System.out.println("Velocidad: " + velocidad + " km/h");
    }
    // ... desacelerar() y apagar()
}
```

---

# Paso 3: Otra clase, mismo contrato

```java
public class Motocicleta implements Vehiculo {
    private int velocidad = 0;
    private boolean encendido = false;

    @Override
    public void encender() {
        encendido = true;
        System.out.println("🏍 ¡Moto lista, brum brum!");
    }

    @Override
    public void acelerar(int incremento) {
        if (encendido) velocidad += incremento * 2; // ¡más rápida!
        System.out.println("Velocidad: " + velocidad + " km/h");
    }
    // ... desacelerar() y apagar()
}
```

---

# Paso 4: Usar el polimorfismo 🎭

```java
public class Garaje {
    public static void main(String[] args) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Auto());
        vehiculos.add(new Motocicleta());

        for (Vehiculo v : vehiculos) {
            v.encender();
            v.acelerar(20);
            v.apagar();
        }
    }
}
```

✅ El `Garaje` **no necesita saber** qué tipo de vehículo es.
Cada clase responde **a su manera** al mismo contrato.

---

# Reglas del contrato ⚖️

| Situación | ¿Compila? |
|-----------|-----------|
| Implementar **todos** los métodos | ✅ Sí |
| Olvidar **un** método | ❌ Error de compilación |
| Implementar **varias** interfaces a la vez | ✅ Sí |
| Instanciar la interfaz directamente | ❌ No |

```java
// Una clase puede implementar varias interfaces:
public class AutoElectrico implements Vehiculo, Recargable { ... }
```

💡 ¡Esto es algo que la **herencia de clases no permite** en Java!

---

<!-- _class: lead -->

# Parte 2
## Estudio de caso 🚗
### Definiendo las acciones de un vehículo

---

# El escenario

Una empresa de videojuegos quiere modelar **vehículos**:
autos, motos, camiones, ¡y hasta naves espaciales! 🚀

Todos deben poder:

- `encender()` / `apagar()`
- `acelerar(int)` / `desacelerar(int)`

**Pero cada uno lo hace diferente:**
el camión acelera lento, la nave no tiene ruedas...

**Discusión en grupo (5 min):**
¿Por qué una interfaz es mejor aquí que copiar y pegar código?

---

# Diseñando la solución juntos ✏️

```java
public interface Vehiculo {
    void encender();
    void apagar();
    void acelerar(int incremento);
    void desacelerar(int decremento);
}
```

**Preguntas para la clase:**

1. ¿Qué pasa si el juego agrega un método `frenar()` a la interfaz?
2. ¿Deberíamos agregar `abrirPuertas()`? ¿Todas las clases lo necesitan?
3. ¿Cómo agregarías un submarino 🌊 sin romper el código existente?

---

<!-- _class: lead -->

# Parte 3
## Interfaces vs. Clases Abstractas

---

# ¿Qué es una clase abstracta?

Una **clase abstracta** es una clase que **no puede ser instanciada** y puede mezclar:

- Métodos **abstractos** (sin cuerpo, como una interfaz)
- Métodos **concretos** (con código ya implementado)
- **Atributos** con estado

```java
public abstract class VehiculoBase {
    protected int velocidad = 0;        // atributo compartido

    public void detener() {             // método concreto
        velocidad = 0;
    }

    public abstract void encender();    // método abstracto
}
```

---

# Comparación lado a lado

| Característica | Interfaz | Clase Abstracta |
|----------------|----------|-----------------|
| Palabra clave | `interface` / `implements` | `abstract class` / `extends` |
| Métodos con cuerpo | Solo `default` (Java 8+) | ✅ Sí |
| Atributos de instancia | ❌ No (solo constantes) | ✅ Sí |
| Constructor | ❌ No | ✅ Sí |
| ¿Cuántas puede tener una clase? | **Varias** | **Solo una** |
| Relación que expresa | "**puede hacer**" | "**es un(a)**" |

---

# ¿Cuándo usar cada una? 🧭

**Usa una interfaz cuando:**
- Quieres definir un **comportamiento** que clases *no relacionadas* pueden compartir
- Necesitas que una clase cumpla **varios contratos**
- Ej.: `Comparable`, `Reproducible`, `Vehiculo`

**Usa una clase abstracta cuando:**
- Hay una **jerarquía clara** con código y atributos en común
- Quieres dar una **implementación parcial** a las subclases
- Ej.: `VehiculoBase` con el atributo `velocidad` ya incluido

---

# ¡Pueden trabajar juntas! 🤝

```java
public abstract class VehiculoTerrestre implements Vehiculo {
    protected int velocidad = 0;
    protected boolean encendido = false;

    @Override
    public void desacelerar(int decremento) {
        velocidad = Math.max(0, velocidad - decremento);
    }
    // encender(), apagar(), acelerar() quedan para las subclases
}

public class Camion extends VehiculoTerrestre {
    // solo implementa lo que falta
}
```

---

<!-- _class: lead -->

# Parte 4
## Investigación individual 🧭

---

# Tu misión de investigación

Explora la **documentación oficial** y **foros** para responder:

1. ¿Qué son los métodos **`default`** en interfaces? ¿Desde qué versión de Java existen?
2. ¿Qué son las **constantes** en una interfaz?
3. ¿Puede una interfaz **extender otra interfaz**? Busca un ejemplo
4. Encuentra **2 interfaces famosas** de Java (además de `Comparable`) y explica para qué sirven

📌 **Entregable:** resumen con ejemplos · ⏱ **Tiempo:** 20 minutos

---

<!-- _class: lead -->

# Parte 5
## Práctica: Ejercicios de fijación 💻

---

# Ejercicio 1: El garaje completo

1. Crea la interfaz `Vehiculo` (encender, apagar, acelerar, desacelerar)
2. Implementa **3 clases**: `Auto`, `Motocicleta` y `Camion`
   - Cada una con un comportamiento distinto al acelerar
3. En un `main`, crea una `List<Vehiculo>` y haz que todos aceleren en un bucle

---

# Ejercicio 2: Interfaz + Clase Abstracta

1. Crea una clase abstracta `VehiculoBase` que implemente `Vehiculo`
2. Coloca en ella el atributo `velocidad` y el método `desacelerar` ya implementados
3. Haz que `Auto` y `Camion` **extiendan** `VehiculoBase`

**Pregunta de reflexión:** ¿cuánto código repetido eliminaste? 🤔

---

# Ejercicio 3: Varios contratos

1. Crea una interfaz `Electrico` con los métodos `recargar()` y `nivelBateria()`
2. Crea la clase `AutoElectrico` que implemente **`Vehiculo` y `Electrico`**
3. Demuestra que el mismo objeto puede usarse como `Vehiculo` **y** como `Electrico`

**Desafío extra:** agrega `Comparable<AutoElectrico>` para ordenar autos por nivel de batería. 🔋

---

# Resumen de la clase 📌

- Se define una interfaz con `interface` y se cumple con `implements`
- Implementar = escribir **todos** los métodos del contrato
- Una clase puede implementar **varias interfaces**
- **Interfaz** = "puede hacer" · **Clase abstracta** = "es un(a)"
- Clases abstractas permiten **atributos y código compartido**
- Ambas pueden **combinarse** en un buen diseño

---

<!-- _class: lead -->

# ¡Buen trabajo! 🎉

## Dudas y comentarios

**Para practicar en casa:**
completa los desafíos extra de los ejercicios
