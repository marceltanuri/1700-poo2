public class Estudiante implements Comparable<Estudiante>{
    private String nombre;
    private double promedio;

    public Estudiante(String nombre, double promedio) {
        this.nombre = nombre;
        this.promedio = promedio;
    }

    @Override
    public int compareTo(Estudiante o) {
        return Double.compare(this.promedio, o.promedio);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", promedio=" + promedio +
                '}';
    }

}