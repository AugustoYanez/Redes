import java.io.*;
import java.net.*;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket clientSocket1;
    private Socket clientSocket2;
    private PrintWriter out1;
    private PrintWriter out2;
    private BufferedReader in1;
    private BufferedReader in2;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);

            clientSocket1 = serverSocket.accept();
            System.out.println("Cliente 1 conectado: " + clientSocket1);
            clientSocket2 = serverSocket.accept();
            System.out.println("Cliente 2 conectado: " + clientSocket2);

            out1 = new PrintWriter(clientSocket1.getOutputStream(), true);
            in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
            out2 = new PrintWriter(clientSocket2.getOutputStream(), true);
            in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));

            new Thread(() -> handleClientCommunication(in1, out2)).start();
            new Thread(() -> handleClientCommunication(in2, out1)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientCommunication(BufferedReader in, PrintWriter out) {
        try {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + clientMessage);
                out.println("Servidor: " + clientMessage);
                if (clientMessage.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 1024 ;
        Servidor servidor = new Servidor();
        servidor.start(port);
    }
}
