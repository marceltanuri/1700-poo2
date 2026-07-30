public class ServicioPedido {

    public void procesarPedido(Pedido pedido) {
        // 1. validación
        validador.validar(pedido);
    
        // 2. cálculo del total
        double total = calculadora.calcular(pedido);

        // 3. descuento por tipo de cliente
        if (pedido.getCliente().getTipo().equals("VIP"))
            total *= 0.80;
        else if (pedido.getCliente().getTipo().equals("ESTANDAR"))
            total *= 0.95;

        // 4. persistencia (acceso directo a la base de datos)
        pedidosRepository.save(pedido);

        // 5. notificación
        notificador.notificar(pedido.getCliente(), "Su pedido ha sido confirmado");

        // 6. factura en PDF
        generadorFactura.generar(pedido, total);
    
    }
}