public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double saldo = 0;
        boolean salir = false;

        while (!salir) {
            System.out.println("1. Depositar  2. Retirar  3. Saldo  0. Salir");
            String op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.print("Monto: ");
                    saldo += Double.parseDouble(sc.nextLine());
                    // ...y aquí también validación, log, etc.
                    break;
                case "2":
                    System.out.print("Monto: ");
                    double m = Double.parseDouble(sc.nextLine());
                    if (m > saldo) System.out.println("Sin saldo");
                    else saldo -= m;
                    break;
                case "3":
                    System.out.println("Saldo: " + saldo);
                    break;
                case "0":
                    salir = true;
                    break;
            }
        }
    }
}