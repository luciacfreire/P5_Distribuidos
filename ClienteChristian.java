import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class ClienteChristian {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5000;

        try (Socket socket = new Socket(host, puerto)) {
            // Marcar tiempo antes de enviar
            Instant tiempoEnvio = Instant.now();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String horaServidor = in.readLine();

            // Marcar tiempo al recibir
            Instant tiempoRecepcion = Instant.now();

            // Calcular retardo
            long rtt = Duration.between(tiempoEnvio, tiempoRecepcion).toMillis();
            long delay = rtt / 2;

            // Convertir hora del servidor
            LocalDateTime horaServidorDateTime = LocalDateTime.parse(horaServidor);

            // Ajustar hora estimada del servidor
            LocalDateTime horaAjustada = horaServidorDateTime.plus(Duration.ofMillis(delay));

            // Imprimir y guardar en log
            String mensaje = String.format("Hora del servidor: %s\nRTT: %d ms\nHora ajustada: %s\n",
                    horaServidorDateTime, rtt, horaAjustada);

            System.out.println(mensaje);
            guardarLog(mensaje);

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            guardarLog("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    private static void guardarLog(String mensaje) {
        try {
            File carpetaLogs = new File("logs");
            if (!carpetaLogs.exists()) {
                carpetaLogs.mkdir();
            }
    
            FileWriter fw = new FileWriter("logs/cliente.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println("[" + LocalDateTime.now() + "] " + mensaje);
            out.close();
        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }
    
}
