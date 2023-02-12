import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String centralServerIp = "10.43.100.191";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        // Crear un socket y conectarse al servidor central de cálculo
        Socket centralServer = new Socket(centralServerIp, 12345);
        System.out.println("Conexión establecida con el servidor central de cálculo");

        // Pedir al usuario que introduzca los datos
        System.out.print("Introduce el tamaño del rango del problema: ");
        int size = scanner.nextInt();
        float[][] data = new float[size][2];
        for (int i = 0; i < size; i++) {
            System.out.print("Variable aleatoria " + (i + 1) + ": ");
            data[i][0] = scanner.nextFloat();
            System.out.print("f(x): ");
            data[i][1] = scanner.nextFloat();
        }

        // Enviar la matriz al servidor central de cálculo
        ObjectOutputStream oos = new ObjectOutputStream(centralServer.getOutputStream());
        oos.writeObject(data);
        oos.flush();

        // Recibir la respuesta del servidor central de cálculo
        ObjectInputStream ois = new ObjectInputStream(centralServer.getInputStream());
        float var = ois.readFloat();

        // Mostrar la respuesta al usuario
        System.out.println("La varianza es: " + var);

        // Cerrar el socket
        centralServer.close();
    }
}
