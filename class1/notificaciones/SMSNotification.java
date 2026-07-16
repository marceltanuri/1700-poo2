public class SMSNotification implements Notification {

    @Override
    public void send(String name, String message) {
        System.out.println("Sending SMS message to " + name + ": " + message);
    }

}