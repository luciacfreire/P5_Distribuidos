import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServidorChristian {
    public static void main(String[] args) throws IOException {
        int puerto = 5000;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor escuchando en el puerto " + puerto);

        while (true) {
            Socket cliente = servidor.accept();
            OutputStream os = cliente.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            // Obtener hora actual del servidor
            LocalDateTime ahora = LocalDateTime.now();
            pw.println(ahora.toString());

            System.out.println("Hora enviada: " + ahora);
            cliente.close();
        }
    }
}
