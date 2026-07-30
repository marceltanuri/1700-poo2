public class ValidadorPedido {
    public void validar(Pedido pedido) {
        if (pedido.getItems().isEmpty())
            throw new PedidoInvalidoException("Pedido vacío");
    }
}