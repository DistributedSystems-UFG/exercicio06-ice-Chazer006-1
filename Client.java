import com.zeroc.Ice.*;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {

            ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -h 98.90.53.6 -p 5678");

            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);

            if (printer == null) {
                throw new Error("Proxy inválido!");
            }

            String response = printer.printString("Hello from Goiania!");
            System.out.println("Servidor respondeu: " + response);

            int val1 = 10;
            int val2 = 25;
            int result = printer.add(val1, val2);
            System.out.println("Resultado da soma remota (" + val1 + " + " + val2 + "): " + result);

            String time = printer.getServerTime();
            System.out.println("Horário atual no servidor: " + time);

        } catch (LocalException e) {
            e.printStackTrace();
        }
    }
}
