# Ejercicio: Sistema de Notificaciones con Interfaces

## Objetivo
Reforzar el concepto de interfaces en Java diseñando un sistema de notificaciones con diferentes canales.

## Descripción
Imagina que estás construyendo un sistema de alertas para una aplicación. Los usuarios pueden elegir recibir notificaciones por diferentes medios: Email, SMS o WhatsApp. 

Tu tarea es crear una interfaz común para todas las notificaciones y luego implementar cada uno de estos canales. No es necesario realizar la conexión real con las APIs de mensajería; bastará con imprimir mensajes en la consola simulando el envío.

## Tareas

1. **Crear la interfaz `Notificacion`:**
   - Crea un archivo llamado `Notificacion.java`.
   - Define una interfaz con un único método: `void enviar(String usuario, String mensaje);`.

2. **Crear la implementación por Email:**
   - Crea un archivo llamado `NotificacionEmail.java`.
   - Haz que la clase implemente la interfaz `Notificacion`.
   - En el método `enviar`, imprime en consola algo como: `"Enviando Email a " + usuario + " - Mensaje: " + mensaje`.

3. **Crear la implementación por SMS:**
   - Crea un archivo llamado `NotificacionSMS.java`.
   - Haz que la clase implemente la interfaz `Notificacion`.
   - En el método `enviar`, imprime en consola: `"Enviando SMS a " + usuario + " - Mensaje: " + mensaje`.

4. **Crear la implementación por WhatsApp:**
   - Crea un archivo llamado `NotificacionWhatsApp.java`.
   - Haz que la clase implemente la interfaz `Notificacion`.
   - En el método `enviar`, imprime en consola: `"Enviando WhatsApp a " + usuario + " - Mensaje: " + mensaje`.

5. **Probar las implementaciones:**
   - Crea una nueva clase con un método `main` (por ejemplo, `MainNotificaciones.java`).
   - Instancia las tres clases de notificación y prueba enviar un mensaje a través de cada una de ellas para verificar el comportamiento (polimorfismo).

## Resultado esperado en la clase de prueba (MainNotificaciones)
Tu código debería verse similar a esto tras las modificaciones:

```java
public class MainNotificaciones {
    public static void main(String[] args) {
        Notificacion email = new NotificacionEmail();
        email.enviar("Carlos", "Tu pedido ha sido procesado.");

        Notificacion sms = new NotificacionSMS();
        sms.enviar("Ana", "Tu código de verificación es 1234.");

        Notificacion whatsapp = new NotificacionWhatsApp();
        whatsapp.enviar("Pedro", "¡Hola! Tienes un nuevo mensaje de soporte.");
    }
}
```

¡Buena suerte!