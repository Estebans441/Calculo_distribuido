import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CentralServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Crear un servidor socket en el puerto 12345
        ServerSocket server = new ServerSocket(12345);
        System.out.println("Servidor central de cálculo en ejecución");

        while (true) {
            // Aceptar la conexión de un cliente
            Socket client = server.accept();
            System.out.println("Conexión establecida con el cliente");

            // Recibir el arreglo del cliente
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            int[] array = (int[]) ois.readObject();

            // Enviar la mitad del arreglo a los servidores de operación
            int size = array.length / 2;
            for (int i = 0; i < 2; i++) {
                Socket operationServer = new Socket("localhost", 22222 + i);
                ObjectOutputStream oos = new ObjectOutputStream(operationServer.getOutputStream());
                oos.writeObject(array);
                oos.writeInt(i * size);
                oos.writeInt(size);
                oos.flush();
                operationServer.close();
            }

            // Recibir las respuestas de los servidores de operación
            int sum = 0;
            for (int i = 0; i < 2; i++) {
                Socket operationServer = server.accept();
                ObjectInputStream ois2 = new ObjectInputStream(operationServer.getInputStream());
                sum += ois2.readInt();
                operationServer.close();
            }

            // Enviar la respuesta al cliente
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeInt(sum);
            oos.flush();
            client.close();
        }
    }
}
