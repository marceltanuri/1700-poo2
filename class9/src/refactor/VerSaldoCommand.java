public class VerSaldoCommand implements Command {
    private Cuenta cuenta;

    public VerSaldoCommand(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public void execute() {
        cuenta.verSaldo();
    }
}
