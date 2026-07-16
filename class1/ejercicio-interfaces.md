# Ejercicio: Implementación de Interfaces

## Objetivo
Fijar el concepto de interfaces en Java mediante la creación de nuevas implementaciones.

## Descripción
En la carpeta `examples`, ya tenemos una interfaz `HelloWorld` y algunas implementaciones como `HelloWorldEspanol` y `HelloWorldPortugues`. 

Tu tarea es expandir nuestro soporte de idiomas creando dos nuevas clases que implementen la interfaz `HelloWorld`.

## Tareas

1. **Crear la implementación en Inglés:**
   - Crea un nuevo archivo llamado `HelloWorldEnglish.java`.
   - Haz que esta clase implemente la interfaz `HelloWorld`.
   - Implementa el método `greeting(String name)` para que imprima por consola `"Hello [name]!"`.

2. **Crear la implementación en Francés:**
   - Crea un nuevo archivo llamado `HelloWorldFrancais.java` (o `HelloWorldFrench.`).
   - Haz que esta clase implemente la interfaz `HelloWorld`.
   - Implementa el método `greeting(String name)` para que imprima por consola `"Bonjour [name]!"`.

3. **Probar las implementaciones:**
   - Abre el archivo `Main.java`.
   - Modifica el método `main` para instanciar tus nuevas clases (`HelloWorldEnglish` y `HelloWorldFrancais`).
   - Llama al método `greeting` en ambas instancias pasándole un nombre de prueba para verificar que funcionen correctamente.

## Resultado esperado en Main.java
Tu clase `Main` debería verse similar a esto tras las modificaciones:

```java
public class Main {
    public static void main(String[] args) {
        HelloWorld helloSpanish = new HelloWorldEspanol();
        helloSpanish.greeting("Marcel");

        HelloWorld helloEnglish = new HelloWorldEnglish();
        helloEnglish.greeting("Marcel");

        HelloWorld helloFrench = new HelloWorldFrancais();
        helloFrench.greeting("Marcel");
    }
}
```

¡Buena suerte!