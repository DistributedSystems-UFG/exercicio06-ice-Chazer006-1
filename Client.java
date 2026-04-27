import com.zeroc.Ice.*;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {

            ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -h 98.90.53.6 -p 5678");

            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);

            if (printer == null) {
                throw new Error("Proxy inválido");
            }

            String response = printer.printString("Hello from Goiania!");
            System.out.println("Resposta do Servidor: " + response);

            int sum = printer.add(15, 27);
            System.out.println("Resultado da soma remota (15+27): " + sum);

            String time = printer.getServerTime();
            System.out.println("Hora atual no servidor: " + time);

        } catch (LocalException e) {
            e.printStackTrace();
        }
    }
}
