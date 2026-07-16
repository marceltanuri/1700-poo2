import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Demo{

    public static void main(String[] args) {
        List<Integer> numeros = new ArrayList<>(List.of(5, 2, 9));
        List<String> nombres = new ArrayList<>(List.of("Ana", "Luis", "Bea"));

        List<Estudiante> estudiantes = new ArrayList<>(List.of(new Estudiante("Maria", 10.0), new Estudiante("Marcel", 8.0), new Estudiante("Jose", 9.5)));
        
        Collections.sort(numeros);
        System.out.println(numeros);

        Collections.sort(nombres);
        System.out.println(nombres);

        Collections.sort(estudiantes);
        System.out.println(estudiantes);
    }


}
