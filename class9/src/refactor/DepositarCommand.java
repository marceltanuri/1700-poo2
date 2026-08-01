import java.util.Scanner;

public class DepositarCommand implements Command {
    private Cuenta cuenta;
    private Scanner sc;

    public DepositarCommand(Cuenta cuenta, Scanner sc) {
        this.cuenta = cuenta;
        this.sc = sc;
    }

    @Override
    public void execute() {
        System.out.print("Monto: ");
        double monto = Double.parseDouble(sc.nextLine());
        cuenta.depositar(monto);
    }
}
