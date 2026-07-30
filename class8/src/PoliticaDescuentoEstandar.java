public class PoliticaDescuentoEstandar implements PoliticaDescuento {

    public double aplicar(double total){
        return total * 0.95;
    }

}