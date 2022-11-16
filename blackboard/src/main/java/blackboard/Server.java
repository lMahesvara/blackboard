package blackboard;

import interfaces.IServer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
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
        } catch (Exception e) {

        }
    }

    public void notificarTodos(AbstractPeticion peticion) {
        sockets.forEach(socket -> {
            System.out.println("Notificar ");
            socket.sendResponse(peticion);
        });
    }
    
    public void notificarCliente(AbstractPeticion peticion){
        sockets.forEach(socket -> {
            if(socket.hashCode() == peticion.getHashcodeSC()){
                socket.sendResponse(peticion);
            }
        });
    }
}
