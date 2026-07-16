public class Main {
	public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorldEnglish();
        helloWorld.greeting("Marcel");

        helloWorld = new HelloWorldEspanol();
        helloWorld.greeting("Marcel");

        helloWorld = new HelloWorldPortugues();
        helloWorld.greeting("Marcel");
    }
}