public class CalculadoraTotal {
    public double calcular(Pedido pedido) {
        return pedido.getItems().stream()
                .mapToDouble(i -> i.getPrecio() * i.getCantidad())
                .sum();
    }
}