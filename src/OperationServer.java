import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.pow;

public class OperationServer {
    private static String centralServerIp = "10.43.100.187";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Obtener el número de servidor de operación
        int serverNum = Integer.parseInt(args[0]);
        // Crear un servidor socket en el puerto 22222 o 22223
        ServerSocket server = new ServerSocket(22222 + serverNum);
        System.out.println("----------------------------------------------");
        System.out.println("Servidor de operación " + (serverNum + 1) + " en ejecución");
        System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("Puerto: " + server.getLocalPort());
        System.out.println("----------------------------------------------");

        while (true) {
            // Aceptar la conexión del servidor central de cálculo
            Socket centralServer = server.accept();
            System.out.println("  > Conexión establecida con el servidor central de cálculo " + centralServer.getInetAddress());

            // Recibir la matriz y la parte de la operacion a realizar
            ObjectInputStream ois = new ObjectInputStream(centralServer.getInputStream());
            float[][] data = (float[][]) ois.readObject();
            int op = ois.readInt();

            // Calcular la parte correspondiente de la operacion
            float res;
            if (op == 0) res = op1(data);
            else res = op2(data);
            System.out.println("        - Respuesta de una parte: " + String.valueOf(res));
            System.out.println();
            // Enviar la respuesta al servidor central de cálculo
            Socket centralServer2 = new Socket(centralServerIp, 12345);
            ObjectOutputStream oos = new ObjectOutputStream(centralServer2.getOutputStream());
            oos.writeFloat(res);
            oos.flush();
            centralServer2.close();
        }
    }

    //Parte 1 de la operacion de varianza en la que se calcula el valor esperado de x al cuadrado
    public static float op1(float[][] data) {
        float res = 0;
        for (int i = 0; i < data.length; i++) {
            res += pow(data[i][0], 2) * data[i][1];
        }
        return res;
    }

    //Parte 2 de la operacion de varianza en la que se calcula el valor esperado al cuadrado
    public static float op2(float[][] data) {
        float res = 0;
        for (int i = 0; i < data.length; i++)
            res += data[i][0] * data[i][1];
        res = (float) (pow(res, 2) * -1);
        return res;
    }
}
