public class PedidosRepositoryOracle implements PedidosRepository {

    public Pedido save(Pedido pedido){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle://localhost/tienda2");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pedidos ...");
            // otras configs de Oracle
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pedido;
    }


}