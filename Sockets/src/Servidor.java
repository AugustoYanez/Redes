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

    public void start(int port) { // funcion para empezar el progrma
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);

            clientSocket1 = serverSocket.accept(); // bloquea la ejecucion hasta que un cliente se conecte, cuando se conecta devuelve un socket
            System.out.println("Cliente 1 conectado: " + clientSocket1);
            clientSocket2 = serverSocket.accept();
            System.out.println("Cliente 2 conectado: " + clientSocket2);

            out1 = new PrintWriter(clientSocket1.getOutputStream(), true); // enviar datos al primer cliente a través de su flujo de salida
            in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream())); //  recibir datos del primer cliente a través de su flujo de entrada
            out2 = new PrintWriter(clientSocket2.getOutputStream(), true); // lo mismo para el otro cliente
            in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));

            new Thread(() -> comunicacionesCliente(in1, out2)).start(); // asignamos los hilos para que ejecuten la funcion
            new Thread(() -> comunicacionesCliente(in2, out1)).start();
        } catch (IOException e) { // busqueda y deteccion de errores
            e.printStackTrace();
        }
    }

    private void comunicacionesCliente(BufferedReader in, PrintWriter out) { // funcion que recibe flujos de entrada y salida, basicamente lee mensajes del cliente actual y los envia otro
        try {
            String mensajeCliente; // almacenar mensajes del cliente
            while ((mensajeCliente = in.readLine()) != null) { // mientras que lea todos los mensaje los escriba
                System.out.println("Mensaje recibido: " + mensajeCliente);
                out.println("Servidor: " + mensajeCliente);
                if (mensajeCliente.equalsIgnoreCase("chau")) { // si se ingresa "chau" se desconecta
                    break;
                }
            }
        } catch (IOException e) { // busqueda y deteccion de errores
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 3345 ;
        Servidor servidor = new Servidor();
        servidor.start(port); // establecer la funcion
    }
}
