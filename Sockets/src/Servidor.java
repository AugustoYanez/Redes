import java.io.*;
import java.net.*;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket);

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + clientMessage);
                    out.println("Servidor: Recibido");
                    if (clientMessage.equalsIgnoreCase("bye")) {
                        break;
                    }
                }

                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Cliente desconectado: " + clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        Servidor servidor = new Servidor();
        servidor.start(port);
    }
}
