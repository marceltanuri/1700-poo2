public class MainNotificaciones {

    public static void main(String[] args) {
        Notification email = new EmailNotification();
        email.send("Carlos", "Tu pedido ha sido procesado.");

        Notification sms = new SMSNotification();
        sms.send("Ana", "Tu código de verificación es 1234.");

        Notification whatsapp = new WhatsappNotification();
        whatsapp.send("Pedro", "¡Hola! Tienes un nuevo mensaje de soporte.");
    }
    
}