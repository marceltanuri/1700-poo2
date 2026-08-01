import java.util.Scanner;


public class Demo {

public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cuenta cuenta = new Cuenta();
        Menu menu = new Menu();

        menu.registerCommand("1", new DepositarCommand(cuenta, sc));
        menu.registerCommand("2", new RetirarCommand(cuenta, sc));
        menu.registerCommand("3", new VerSaldoCommand(cuenta));
        menu.registerCommand("4", new NuevoCommand());


        boolean salir = false;

        while (!salir) {
            System.out.println("1. Depositar  2. Retirar  3. Saldo   4. Nuevo   0. Salir");
            String op = sc.nextLine();

            if ("0".equals(op)) {
                salir = true;
            } else {
                menu.executeCommand(op);
            }
        }
        
        sc.close();
    }

}