package blackboard;

import entidades.Usuario;
import helpers.ConvertirPeticion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import peticiones.AbstractPeticion;

public class SocketCliente extends Thread {

    private Socket socket;
    private BufferedReader entrada;
    private PrintStream salida;
    

    public SocketCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //Obtener ip address
        try {
            while (socket.isConnected()) {
                //Obtener el Json de la peticion
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String peticion = entrada.readLine();
                System.out.println(peticion);

                //Generar hilo con la peticion
                ejecutarNuevoHilo(peticion);
            }
        } catch (Exception e) {
        }
    }

    public void sendResponse(AbstractPeticion peticion) {
        try {
            salida = new PrintStream(socket.getOutputStream());
            String json = ConvertirPeticion.JSONConverter(peticion);
            salida.println(json);
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ejecutarNuevoHilo(String json) {
        HiloSocket hilo = HilosFactory.crearHiloSocket(json, hashCode());
        hilo.start();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.socket);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SocketCliente other = (SocketCliente) obj;
        return Objects.equals(this.socket, other.socket);
    }
}
