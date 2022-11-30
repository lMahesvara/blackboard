package blackboard;

import static helpers.Peticiones.CERRAR_APP;
import static helpers.Peticiones.CERRAR_SESION;
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

    private Server() {
    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    @Override
    public void run() {
        this.conexion();
    }

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

    public void notificarTodos(AbstractPeticion peticion) {
        sockets.forEach(socket -> {
            System.out.println("Notificar ");
            socket.sendResponse(peticion);
        });
    }
    
    public void notificarCliente(AbstractPeticion peticion){
        if(peticion.getPeticionRespuesta().equals(CERRAR_APP)){
            sockets.forEach(socket -> {
                if(socket.hashCode() == peticion.getHashcodeSC()){
                    socket.sendResponse(peticion);
                    socket.closeAll();
                    sockets.remove(socket);
                    System.out.println("Socket cerrado");
                }
            });        
        }else{
            System.out.println("Notificar cliente: ");
            sockets.forEach(socket -> {
               if(socket.hashCode() == peticion.getHashcodeSC()){
                   System.out.println(socket);
                   socket.sendResponse(peticion);
               }
            });       
        }
    }
}
