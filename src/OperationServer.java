import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OperationServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Obtener el número de servidor de operación
        int serverNum = Integer.parseInt(args[0]);
        // Crear un servidor socket en el puerto 22222 o 22223
        ServerSocket server = new ServerSocket(22222 + serverNum);
        System.out.println("Servidor de operación " + (serverNum + 1) + " en ejecución");

        while (true) {
            // Aceptar la conexión del servidor central de cálculo
            Socket centralServer = server.accept();
            System.out.println("Conexión establecida con el servidor central de cálculo");

            // Recibir el arreglo y los índices desde el servidor central de cálculo
            ObjectInputStream ois = new ObjectInputStream(centralServer.getInputStream());
            int[] array = (int[]) ois.readObject();
            int start = ois.readInt();
            int size = ois.readInt();

            // Calcular la suma de la mitad del arreglo
            int sum = 0;
            for (int i = start; i < start + size; i++) {
                sum += array[i];
            }

            // Enviar la respuesta al servidor central de cálculo
            Socket centralServer2 = new Socket("localhost", 12345);
            ObjectOutputStream oos = new ObjectOutputStream(centralServer2.getOutputStream());
            oos.writeInt(sum);
            oos.flush();
            centralServer2.close();
        }
    }
}
