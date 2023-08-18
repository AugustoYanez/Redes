import java.io.*;
import java.net.*;

public class Cliente {
    private Socket socket; // crear socket
    private PrintWriter out; //escribir datos en el flujo de salida del socket.
    private BufferedReader in; // leer datos del flujo de entrada del socket.
    private BufferedReader stdIn; // se usa para capturar los mensajes ingresados por el usuario en la consola del cliente.

    public void start(String host, int port) { // funcion start, recibe host y puerto
        try {
            socket = new Socket(host, port); // crear socker
            System.out.println("Conectado al servidor: " + socket);

            out = new PrintWriter(socket.getOutputStream(),  true); // parametros, el primero escribe datos de salida y el segundo modo de limpieza
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // el parametro leera los datos de flujo de entrada
            stdIn = new BufferedReader(new InputStreamReader(System.in));   // leer la entrada que el usuario proporciona a por  la consola

            new Thread(() -> leerRespuestasServer()).start(); // asociamos el hilo a el metodo de leer respuestas

            String entradasUser; // se usa para almacenar los mensajes ingresados por el usuario
            while ((entradasUser = stdIn.readLine()) != null) { // mientras  siga ingresando mensajes
                System.out.println("Mensaje enviado: " + entradasUser);
                out.println(entradasUser); // imprime mensajes
                if (entradasUser.equalsIgnoreCase("chau")) { // si ingresa chau finaliza
                    break;
                }
            }
// se cierra td
            in.close();
            out.close();
            socket.close();
            System.out.println("Desconectado del servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leerRespuestasServer() { // Este método se encargará de leer las respuestas del servidor y mostrarlas en la consola del cliente.
        try {
            String respuestServer; // para almacenar la respuesta del servidor
            while ((respuestServer = in.readLine()) != null) { // mientras que el flujo de entrada de datos no sea nulo
                System.out.println("Respuesta del cliente: " + respuestServer); // se escribe la respuesta
            }
        } catch (IOException e) { // busqueda e impresion de errores
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "localhost"; // crear host
        int port = 3345; // crear puerto
        Cliente cliente = new Cliente();
        cliente.start(host, port); // le agregamos la funcion start a cliente y le pasamos los parametros
    }
}
