package blackboard;

import interfaces.IServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements IServer {

    public static void main(String[] args) {
        IServer server = new Server();
        server.conexion();
    }

    @Override
    public void conexion() {
        try {
            int puerto = 9000;
            ServerSocket server = new ServerSocket(puerto);
            Socket socket = new Socket();
            BufferedReader entrada;

            while (true) {
                System.out.println("Esperando conexión");
                socket = server.accept();
                System.out.println("Conectado con el cliente");

                //Obtener ip address
                InetSocketAddress sockaddr = (InetSocketAddress) socket.getRemoteSocketAddress();
                InetAddress inaddr = sockaddr.getAddress();
                System.out.println(((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress().toString().split("/")[1]);

                //Obtener el Json de la peticion
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String peticion = entrada.readLine();
                System.out.println(peticion);
                socket.close();

                //Generar hilo con la peticion
                ejecutarNuevoHilo(peticion);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void sendResponse() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void ejecutarNuevoHilo(String json) {
        //HiloSocket hilo = HilosFactory.crearHilo(json);
        //hilo.start();
    }
}
