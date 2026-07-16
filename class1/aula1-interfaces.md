---
marp: true
theme: default
paginate: true
size: 16:9
header: "POO — Interfaces"
footer: "Aula 1 · Programación Orientada a Objetos"
style: |
  section {
    font-size: 26px;
  }
  section.lead h1 {
    font-size: 60px;
  }
  code {
    font-size: 0.9em;
  }
  h1 {
    color: #1a5276;
  }
  h2 {
    color: #2874a6;
  }
---

<!-- _class: lead -->
<!-- _paginate: false -->

# Interfaces en POO

## Aula 1: ¿Qué son y cómo utilizarlas?

**Programación Orientada a Objetos**
Curso para principiantes

---

# Objetivos de la clase

Al final de esta clase, serás capaz de:

- **Explicar** qué es una interfaz y para qué sirve
- **Reconocer** interfaces en código existente
- **Utilizar** una interfaz ya definida en tus programas
- **Comparar objetos** usando la interfaz `Comparable`

---

# Agenda

1. 📖 **Expositiva:** ¿Qué son las interfaces?
2. 🔍 **Estudio de caso:** Comparar objetos con `Comparable`
3. 🧭 **Investigación individual:** Explorando la documentación
4. 💻 **Práctica:** Ejercicios de fijación

---

<!-- _class: lead -->

# Parte 1
## ¿Qué son las interfaces?

---

# Una analogía: el control remoto 📺

Piensa en un **control remoto**:

- Tiene botones: *encender*, *apagar*, *subir volumen*...
- Tú **no sabes** cómo funciona por dentro
- Solo te importa **qué puede hacer**

Una **interfaz** en POO es exactamente eso:

> Un **contrato** que dice **qué** debe hacer una clase,
> sin decir **cómo** lo hace.

---

# Definición formal

Una **interfaz** es un tipo que define un conjunto de **métodos abstractos** (sin cuerpo) que las clases deben implementar.

```java
public interface Reproducible {
    void reproducir();   // ¿qué hace? → lo define la clase
    void pausar();
    void detener();
}
```

- Los métodos **no tienen cuerpo** (no hay `{ ... }`)
- La interfaz solo declara la **firma** de los métodos
- Es un **contrato**: quien la use, debe cumplirlo

---

# ¿Por qué usar interfaces?

| Beneficio | ¿Qué significa? |
|-----------|-----------------|
| **Estandarización** | Todas las clases hablan el "mismo idioma" |
| **Flexibilidad** | Puedes cambiar la implementación sin romper el código |
| **Polimorfismo** | Tratar objetos distintos de la misma manera |
| **Trabajo en equipo** | Cada persona implementa su parte del contrato |

---

# Cómo utilizar una interfaz

Una clase **implementa** una interfaz con la palabra clave `implements`:

```java
public class Cancion implements Reproducible {

    @Override
    public void reproducir() {
        System.out.println("🎵 Reproduciendo canción...");
    }

    @Override
    public void pausar() {
        System.out.println("⏸ Canción en pausa");
    }

    @Override
    public void detener() {
        System.out.println("⏹ Canción detenida");
    }
}
```

---

# La interfaz como tipo

Puedes usar la interfaz como **tipo de variable**:

```java
Reproducible media = new Cancion();
media.reproducir();   // 🎵 Reproduciendo canción...

media = new Video();  // ¡Otro objeto, mismo contrato!
media.reproducir();   // 🎬 Reproduciendo video...
```

✅ El código que usa `Reproducible` **no necesita saber** si es una canción o un video.

Esto se llama **polimorfismo**.

---

<!-- _class: lead -->

# Parte 2
## Estudio de caso:
## Comparar objetos con `Comparable`

---

# El problema 🤔

Java sabe ordenar números y textos:

```java
List<Integer> numeros = List.of(5, 2, 9);
List<String> nombres = List.of("Ana", "Luis", "Bea");
// Collections.sort(...) funciona ✅
```

Pero... ¿cómo ordena Java **tus propios objetos**?

```java
List<Estudiante> estudiantes = ...;
Collections.sort(estudiantes);  // ❌ ¡Error de compilación!
```

Java **no sabe** qué significa que un estudiante sea "menor" que otro.

---

# La solución: la interfaz `Comparable`

Java ofrece la interfaz `Comparable<T>` con **un solo método**:

```java
public interface Comparable<T> {
    int compareTo(T otro);
}
```

El método `compareTo` devuelve:

| Retorno | Significado |
|---------|-------------|
| **Negativo** | `this` viene **antes** que `otro` |
| **Cero** | Son **equivalentes** |
| **Positivo** | `this` viene **después** que `otro` |

---

# Implementando `Comparable`

```java
public class Estudiante implements Comparable<Estudiante> {
    private String nombre;
    private double promedio;

    public Estudiante(String nombre, double promedio) {
        this.nombre = nombre;
        this.promedio = promedio;
    }

    @Override
    public int compareTo(Estudiante otro) {
        // Ordenar por promedio, de menor a mayor
        return Double.compare(this.promedio, otro.promedio);
    }
}
```

---

# ¡Ahora sí funciona! ✅

```java
List<Estudiante> estudiantes = new ArrayList<>();
estudiantes.add(new Estudiante("Ana", 8.5));
estudiantes.add(new Estudiante("Luis", 9.2));
estudiantes.add(new Estudiante("Bea", 7.8));

Collections.sort(estudiantes);
// Resultado: Bea (7.8), Ana (8.5), Luis (9.2)
```

Al implementar `Comparable`, tu clase **cumple el contrato** y Java ya sabe cómo ordenarla. 🎉

---

# ¿Y si quiero otro orden?

Para ordenar por **nombre** en vez de promedio:

```java
@Override
public int compareTo(Estudiante otro) {
    return this.nombre.compareTo(otro.nombre);
}
```

💡 Observa: `String` **también implementa** `Comparable`,
por eso podemos usar su `compareTo`.

---

<!-- _class: lead -->

# Parte 3
## Investigación individual 🧭

---

# Tu misión de investigación

Busca en la **documentación oficial de Java** y en **foros** (Stack Overflow, etc.):

1. ¿Qué otras clases de Java ya implementan `Comparable`?
2. ¿Qué pasos son necesarios para **implementar una interfaz** en una clase propia?
3. ¿Qué pasa si una clase implementa una interfaz pero **no define todos los métodos**?

📌 **Entregable:** anota tus hallazgos con ejemplos de código.
⏱ **Tiempo sugerido:** 20 minutos

---

# Fuentes recomendadas

- 📚 **Documentación oficial:**
  `docs.oracle.com/javase` → busca "interface Comparable"
- 💬 **Foros:** Stack Overflow (en español: es.stackoverflow.com)
- 📝 **Tutoriales:** Oracle Java Tutorials → "Interfaces and Inheritance"

> 💡 Consejo: aprender a **leer documentación** es una de las habilidades más valiosas de un programador.

---

<!-- _class: lead -->

# Parte 4
## Práctica: Ejercicios de fijación 💻

---

# Ejercicio 1: Producto por precio

Crea una clase `Producto` con `nombre` y `precio`.

1. Implementa `Comparable<Producto>` ordenando por **precio** (menor a mayor)
2. Crea una lista con 5 productos y ordénala con `Collections.sort`
3. Imprime la lista ordenada

**Desafío extra:** invierte el orden (mayor a menor).

---

# Ejercicio 2: Jugadores por puntaje

Crea una clase `Jugador` con `apodo` y `puntos`.

1. Implementa `Comparable<Jugador>` para crear un **ranking** (más puntos primero)
2. Si dos jugadores tienen los mismos puntos, ordena por **apodo** alfabéticamente

💡 Pista: puedes combinar dos comparaciones dentro de `compareTo`.

---

# Ejercicio 3: Fechas de eventos

Crea una clase `Evento` con `titulo`, `dia`, `mes` y `año`.

1. Implementa `Comparable<Evento>` para ordenar cronológicamente
2. Prueba con al menos 4 eventos de años y meses distintos

**Pregunta de reflexión:** ¿en qué orden debes comparar año, mes y día? ¿Por qué?

---

# Resumen de la clase 📌

- Una **interfaz** es un **contrato**: define *qué* hacer, no *cómo*
- Las clases usan `implements` para cumplir el contrato
- La interfaz sirve como **tipo**, habilitando el **polimorfismo**
- `Comparable` permite que Java **ordene tus objetos**
- `compareTo` devuelve negativo, cero o positivo

---

<!-- _class: lead -->

# Próxima clase 👀

## Cómo **implementar** tus propias interfaces
## Interfaces vs. Clases Abstractas

**¡Trae tus dudas de la investigación!**
