/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.Server;
import interfaces.AbstractFuente;
import peticiones.AbstractPeticion;

/**
 *
 * @author erick
 */
public class NotificarCliente extends AbstractFuente{
    @Override
    public void procesar(AbstractPeticion peticion) {
        Server server = Server.getInstance();
        server.notificarCliente(peticion);
    }

    @Override
    public void agregarProblema(AbstractPeticion peticion) {
        
    }
}
