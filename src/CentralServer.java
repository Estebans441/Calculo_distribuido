import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CentralServer {
    private static String[] opServersIp = {"10.43.100.191", "10.43.100.191"};

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Crear un servidor socket en el puerto 12345
        ServerSocket server = new ServerSocket(12345);
        System.out.println("----------------------------------------------");
        System.out.println("Servidor central de cálculo en ejecución");
        System.out.println("IP: " + server.getInetAddress());
        System.out.println("Puerto: " + server.getLocalPort());
        System.out.println("----------------------------------------------");

        while (true) {
            // Aceptar la conexión de un cliente
            Socket client = server.accept();
            System.out.println("  > Conexión establecida con el cliente " + client.getInetAddress());

            // Recibir la matriz del cliente
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            float[][] data = (float[][]) ois.readObject();

            // Enviar la matriz a los servidores de operación
            for (int i = 0; i < 2; i++) {
                Socket operationServer = new Socket(opServersIp[i], 22222 + i);
                ObjectOutputStream oos = new ObjectOutputStream(operationServer.getOutputStream());
                oos.writeObject(data);
                oos.writeInt(i);
                oos.flush();
                operationServer.close();
            }

            // Recibir las respuestas de los servidores de operación
            float var = 0;
            for (int i = 0; i < 2; i++) {
                Socket operationServer = server.accept();
                ObjectInputStream ois2 = new ObjectInputStream(operationServer.getInputStream());
                var += ois2.readFloat();
                operationServer.close();
            }
            System.out.println("        - Respuesta: " + String.valueOf(var));
            System.out.println();

            // Enviar la respuesta al cliente
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeFloat(var);
            oos.flush();
            client.close();
        }
    }
}
