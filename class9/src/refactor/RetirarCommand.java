import java.util.Scanner;

public class RetirarCommand implements Command {
    private Cuenta cuenta;
    private Scanner sc;

    public RetirarCommand(Cuenta cuenta, Scanner sc) {
        this.cuenta = cuenta;
        this.sc = sc;
    }

    @Override
    public void execute() {
        System.out.print("Monto: ");
        double monto = Double.parseDouble(sc.nextLine());
        cuenta.retirar(monto);
    }
}
