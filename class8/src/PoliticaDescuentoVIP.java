public class PoliticaDescuentoVIP implements PoliticaDescuento {

    public double aplicar(double total){
        return total * 0.80;
    }

}