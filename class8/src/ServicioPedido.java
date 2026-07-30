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
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tienda");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pedidos ...");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 5. notificación
        EmailSender sender = new EmailSender();
        sender.enviar(pedido.getCliente().getEmail(),
                      "Pedido confirmado",
                      "Su total es $ " + total);

        // 6. factura en PDF
        PdfWriter writer = new PdfWriter("factura_" + pedido.getId() + ".pdf");
        writer.escribirLinea("Total: " + total);
        writer.guardar();
    }
}