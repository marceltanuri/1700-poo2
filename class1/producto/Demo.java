import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class Demo {

    public static void main(String[] args) {
        Producto producto1 = new Producto("Laptop", 1200.00);
        Producto producto2 = new Producto("Smartphone", 800.00);

        List<Producto> productos = new ArrayList<>(List.of(producto1, producto2));

        Collections.sort(productos);
        System.out.println(productos);
    
    }


}