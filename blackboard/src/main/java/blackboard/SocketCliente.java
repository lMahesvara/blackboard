package blackboard;

import helpers.ConvertirPeticion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import peticiones.AbstractPeticion;

public class SocketCliente extends Thread {
    private Socket socket;
    private BufferedReader entrada;
    private PrintStream salida;
    
    /**
     * Constructor
     */
    public SocketCliente(Socket socket) {
        this.socket = socket;
    }

    /**
     * Hilo que recibe las peticiones del cliente
     */
    @Override
    public void run() {
        //Obtener ip address
        try {
            while (!socket.isClosed()) {
                //Obtener el Json de la peticion
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String peticion = entrada.readLine();
                System.out.println(peticion);

                //Generar hilo con la peticion
                ejecutarNuevoHilo(peticion);
            }
        } catch (Exception ex) {
            Server.getInstance().cerrarSocket(this);
            System.out.println("SE CERRÓ EL SOCKET");
            //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Envia una respuesta al cliente
     */
    public void sendResponse(AbstractPeticion peticion) {
        try {
            salida = new PrintStream(socket.getOutputStream());
            System.out.println("Se enviara una peticion: "+ this.socket +peticion);
            
            String json = ConvertirPeticion.JSONConverter(peticion);
            salida.println(json);
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cierra el socket
     */
    public void closeAll() {
        try {
            salida.close();
            entrada.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ejecuta un nuevo hilo
     */
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
