public class NotificadorEmail implements Notificador {

    public void notificar(Cliente cliente, String mensaje) {
        EmailSender sender = new EmailSender();
        sender.enviar(pedido.getCliente().getEmail(),
                      "Pedido confirmado",
                      "Su total es $ " + total);
    }

}