import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {
        
        
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc;
            
            System.out.println("\nServidor iniciado ... \nServidor en ejecución ...");
            while(true){
                // Espero la conexion del cliente
               // System.out.println(server.accept());
                sc = server.accept();
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                // Pido al cliente el nombre al cliente
                out.writeUTF("\nIndica tu nombre: ");
                String nombreCliente = in.readUTF();
                
                // Inicio el hilo
                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                hilo.start();
                
                System.out.println("\nCreada la conexion con el cliente " + nombreCliente);
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
