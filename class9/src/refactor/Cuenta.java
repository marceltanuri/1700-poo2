public class Cuenta {
    private double saldo = 0;

    public void depositar(double monto) {
        saldo += monto;
    }

    public void retirar(double monto) {
        if (monto > saldo) {
            System.out.println("Sin saldo");
        } else {
            saldo -= monto;
        }
    }

    public void verSaldo() {
        System.out.println("Saldo: " + saldo);
    }
}
