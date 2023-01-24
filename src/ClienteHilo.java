import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;

    public static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {

        Scanner sn = new Scanner(System.in);

        String mensaje;
        int opcion = 0;
        boolean salir = false;

        while (!salir) {

            try {
                System.out.print("\n--- Pulse ENTER para continuar --- ");
                sn.nextLine();
                limpiarConsola();
                System.out.println("\n\n*************** Menu aplicación ***************");
                System.out.println("1. Almacenar numero en el archivo");
                System.out.println("2. Listar ficheros");
                System.out.println("3. Mostar ficheros");
                System.out.println("4. ");
                System.out.println("5. ");
                System.out.println("6. Salir");
                System.out.print("\nElige una opción: ");
                opcion = sn.nextInt();
                out.writeInt(opcion);

                switch (opcion) {
                    case 1:
                        // Genero un numero aleatorio
                        int numeroaleatorio = generaNumeroAleatorio(1, 100);
                        System.out.println("\nCliente: Numero generado: " + numeroaleatorio);
                        // Le mando al servidor el numero aleatorio
                        out.writeInt(numeroaleatorio);
                        // Recibo y muestro el mensaje
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        sn.nextLine();
                        break;
                    case 2:

                        // Obtener listado de ficheros en directorio de trabajo
                        File directorio = new File("D:\\Users\\dam2\\Desktop\\Interfaces\\primeraEvaluacion_Interfaces\\ComunicacionesRed");
                        File[] ficheros = directorio.listFiles();
                        String listadoFicheros = "";
                        for (File fichero : ficheros) {
                            listadoFicheros += fichero.getName() + "\n";
                        }
                        // Enviar listado de ficheros al cliente
                        out.writeUTF(listadoFicheros);
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:
                        salir = !salir;
                        break;
                    default:
                        mensaje = in.readUTF();
                        System.out.println(mensaje);

                }

            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        sn.close();
    }

    public int generaNumeroAleatorio(int minimo, int maximo) {
        int num = (int) Math.floor(Math.random() * (maximo - minimo + 1) + (minimo));
        return num;
    }
}
