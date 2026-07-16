public class WhatsappNotification implements Notification {

    @Override
    public void send(String name, String message) {
        System.out.println("Sending whatsApp message to " + name + ": " + message);
    }

}