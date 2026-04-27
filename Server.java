import com.zeroc.Ice.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Implementação da interface gerada pelo Slice
class PrinterI implements Demo.Printer {
    @Override
    public String printString(String s, Current current) {
        System.out.println("Recebido: " + s);
        return "Mensagem processada com sucesso!";
    }

    @Override
    public int add(int a, int b, Current current) {
        System.out.println("Somando: " + a + " + " + b);
        return a + b;
    }

    @Override
    public String getServerTime(Current current) {
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}

public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            // Cria um adaptador de objetos na porta 5678
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -h 0.0.0.0 -p 5678");
            
            // Instancia o objeto e o adiciona ao adaptador
            com.zeroc.Ice.Object object = new PrinterI();
            adapter.add(object, Util.stringToIdentity("SimplePrinter"));
            
            // Ativa o adaptador
            adapter.activate();
            System.out.println("Servidor ICE iniciado e aguardando requisições...");
            
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}