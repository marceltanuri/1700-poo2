public class ServicioPedido {

    public void procesarPedido(Pedido pedido) {
        // 1. validación
        validador.validar(pedido);
    
        // 2. cálculo del total
        double total = calculadora.calcular(pedido);

        // 3. descuento por tipo de cliente
        total = politicaDescuento.aplicar(total);

        // 4. persistencia (acceso directo a la base de datos)
        pedidosRepository.save(pedido);

        // 5. notificación
        notificador.notificar(pedido.getCliente(), "Su pedido ha sido confirmado");

        // 6. factura en PDF
        generadorFactura.generar(pedido, total);
    
    }
}