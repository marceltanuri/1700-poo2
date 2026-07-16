public class EmailNotification implements Notification {

    @Override
    public void send(String name, String message) {
        System.out.println("Sending Email to " + name + ": " + message);
    }

}