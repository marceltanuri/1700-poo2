public class GeneradorFacturaPDF implements GeneradorFactura {
    public void generar(Pedido pedido, double total) {
        PdfWriter writer = new PdfWriter("factura_" + pedido.getId() + ".pdf");
        writer.escribirLinea("Total: " + total);
        writer.guardar();
    }
}