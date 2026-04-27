import com.zeroc.Ice.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class PrinterI implements Demo.Printer {
    @Override
    public String printString(String s, Current current) {
        System.out.println("O cliente disse: " + s);
        return "Recebido com sucesso!";
    }

    @Override
    public int add(int a, int b, Current current) {
        System.out.println("Requisição de soma: " + a + " + " + b);
        return a + b;
    }

    @Override
    public String getServerTime(Current current) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }
}

public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -h 0.0.0.0 -p 5678");
            
            com.zeroc.Ice.Object object = new PrinterI();
            adapter.add(object, Util.stringToIdentity("SimplePrinter"));
            
            adapter.activate();
            System.out.println("Servidor ICE pronto e aguardando...");
            
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
