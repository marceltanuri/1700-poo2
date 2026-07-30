public class PedidosRepositoryMySQL implements PedidosRepository {

    public Pedido save(Pedido pedido){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tienda");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pedidos ...");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pedido;
    }


}