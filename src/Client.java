import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        // Crear un socket y conectarse al servidor central de cálculo
        Socket centralServer = new Socket("localhost", 12345);
        System.out.println("Conexión establecida con el servidor central de cálculo");

        // Pedir al usuario que introduzca el arreglo de enteros
        System.out.print("Introduce el tamaño del arreglo: ");
        int size = scanner.nextInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            System.out.print("Introduce el elemento " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }

        // Enviar el arreglo al servidor central de cálculo
        ObjectOutputStream oos = new ObjectOutputStream(centralServer.getOutputStream());
        oos.writeObject(array);
        oos.flush();

        // Recibir la respuesta del servidor central de cálculo
        ObjectInputStream ois = new ObjectInputStream(centralServer.getInputStream());
        int sum = ois.readInt();

        // Mostrar la suma al usuario
        System.out.println("La suma es: " + sum);

        // Cerrar el socket
        centralServer.close();
    }
}
