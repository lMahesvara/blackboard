package blackboard;

import interfaces.IServer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import peticiones.AbstractPeticion;

public class Server extends Thread implements IServer {
    List<SocketCliente> sockets = new LinkedList<>();
    private static Server instance;

    /**
     *  Constructor por defecto
     */
    private Server() {
    }

    /**
     *  Obtiene la instancia de la clase Server (Si no existe crea una nueva)
     */
    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    /**
     *  Inicializa el hilo
     */
    @Override
    public void run() {
        this.conexion();
    }

    /**
     *  Recibe las peticiones del cliente
     */
    @Override
    public void conexion() {
        try {
            int puerto = 9000;
            ServerSocket server = new ServerSocket(puerto);
            Socket socket = new Socket();

            while (true) {
                System.out.println("Esperando conexión");

                socket = server.accept();
                System.out.println("Conectado con el cliente");
                

                SocketCliente socketCliente = new SocketCliente(socket);
                sockets.add(socketCliente);
                socketCliente.start();
                System.out.println(sockets.size());

            }
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *  Notifica a todos los clientes
     */
    public void notificarTodos(AbstractPeticion peticion) {
        sockets.forEach(socket -> {
            System.out.println("Notificar ");
            socket.sendResponse(peticion);
        });
    }
    
    /**
     *  Notifica a un solo cliente
     */
    public void notificarCliente(AbstractPeticion peticion){
        System.out.println("Notificar cliente: ");
        sockets.forEach(socket -> {
           if(socket.hashCode() == peticion.getHashcodeSC()){
               System.out.println(socket);
               socket.sendResponse(peticion);
           }
        });    
    }
    
    /**
     * Cierra el socket del cliente
     */
    public void cerrarSocket(SocketCliente socketCliente){
       sockets.forEach(socket -> {
           if(socket.hashCode() == socketCliente.hashCode()){
               sockets.remove(socket);
               System.out.println("Socket cerrado");
           }
       });      
    }
}
