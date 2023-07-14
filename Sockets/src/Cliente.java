import java.io.*;
import java.net.*;

public class Cliente {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public void start(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Conectado al servidor: " + socket);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                String serverResponse = in.readLine();
                System.out.println("Respuesta del servidor: " + serverResponse);
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            in.close();
            out.close();
            stdIn.close();
            socket.close();
            System.out.println("Desconectado del servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        Cliente cliente = new Cliente();
        cliente.start(host, port);
    }
}
