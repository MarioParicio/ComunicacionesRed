import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    private static String ruta = "D:\\Users\\dam2\\Desktop\\asfa";

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombreCliente) {
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
    }

    @Override
    public void run() {
        boolean ejecutando = true;
        int opcion;
        File f = new File("numeros.txt");

        while (ejecutando) {

            try {
                opcion = in.readInt();
                switch (opcion) {
                    case 1:
                        // Recibo el numero aleatorio
                        int numeroAleatorio = in.readInt();
                        // escribo el numero
                        escribirNumeroAleatorio(f, numeroAleatorio);
                        System.out.println("Se escribio el número del cliente: " + nombreCliente);
                        // Mando el mensaje de confirmacion al cliente
                        out.writeUTF("Servidor: Numero guardado correctamente");
                        break;

                    case 2:
                        // Obtener listado de ficheros en directorio de trabajo
                        System.out.println("Entrando en el case 2");
                        File directorio = new File(ruta);
                        File[] ficheros = directorio.listFiles();
                        String listadoFicheros = "";
                        for (File fichero : ficheros) {
                            listadoFicheros += fichero.getName() + "\n";
                        }
                        // Enviar listado de ficheros al cliente


                        out.writeUTF(listadoFicheros);

                        break;


                    case 3:

                        // Recibir nombre del fichero solicitado por el cliente
                        String nombreFichero = in.readUTF();
                        System.out.println("Nombre del fichero solicitado: " + nombreFichero);
                        // Abrir fichero y leer contenido
                        File ficheroSolicitado = new File(ruta + File.separator + nombreFichero);
                        String contenidoFichero = "";
                        try (BufferedReader br = new BufferedReader(new FileReader(ficheroSolicitado))) {
                            String linea;
                            while ((linea = br.readLine()) != null) {
                                contenidoFichero += linea + "\n";
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        // Enviar contenido del fichero al cliente

                        if (contenidoFichero.equals("")) {
                            out.writeUTF("El fichero solicitado no existe");
                        } else {
                            out.writeUTF(contenidoFichero);
                        }
                        break;


                    case 4:
                        break;

                    case 5:
                        break;

                    case 6:
                        System.out.println(
                                "\n***************************************************************************\n  Un hilo de ejecución ha finalizado:  Un usuario ha abandonado su sesión\n***************************************************************************\n");
                        ejecutando = false;
                        break;
                    default:
                        out.writeUTF("Sólo numeros del 1 al 6");
                }

            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void escribirNumeroAleatorio(File f, int numeroAleatorio) throws IOException {

        FileWriter fw = new FileWriter(f, true);
        // String datoAFichero = numeroAleatorio + ": "+ nombreCliente + "\n";
        // System.out.println(datoAFichero);
        // fw.write(datoAFichero);
        fw.write(nombreCliente + ": " + numeroAleatorio + "\r\n");
        fw.close();

    }
}