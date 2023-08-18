import java.io.*;
import java.net.*;

public class Cliente2 {
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

            new Thread(() -> leerRespuestasServer()).start();

            String entradasUser;
            while ((entradasUser = stdIn.readLine()) != null) {
                System.out.println("Mensaje enviado: " + entradasUser);
                out.println(entradasUser);
                if (entradasUser.equalsIgnoreCase("chau")) {
                    break;
                }
            }

            in.close();
            out.close();
            socket.close();
            System.out.println("Desconectado del servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leerRespuestasServer() {
        try {
            String respuestServer;
            while ((respuestServer = in.readLine()) != null) {
                System.out.println("Respuesta del cliente: " + respuestServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 3345;
        Cliente2 cliente2 = new Cliente2();
        cliente2.start(host, port);
    }
}
