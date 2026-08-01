import java.util.Scanner;

public class NuevoCommand implements Command {

    public NuevoCommand() {
    }

    @Override
    public void execute() {
        System.out.print("Nuevo Command: ");
    }
}
