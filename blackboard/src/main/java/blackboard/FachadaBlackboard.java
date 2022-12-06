/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackboard;

import interfaces.IFachadaBlackboard;
import peticiones.AbstractPeticion;

/**
 *
 * @author Vastem
 */
public class FachadaBlackboard implements IFachadaBlackboard{
    private Blackboard blackboard;
    private Server serverInstance;

    /**
     * Constructor
     */
    public FachadaBlackboard() {
        blackboard = Blackboard.getInstance();
        serverInstance = Server.getInstance();
    }
    
    /**
     * Añade un problema al blackboard
     */
    @Override
    public void addProblem(AbstractPeticion peticion) {
        blackboard.addProblem(peticion);
    }

    /**
     * Notifica a un cliente
     */
    @Override
    public void notificarCliente(AbstractPeticion peticion) {
        serverInstance.notificarCliente(peticion);
    }

    /**
     * Notifica a todos los clientes
     */
    @Override
    public void notificarTodo(AbstractPeticion peticion) {
        serverInstance.notificarTodos(peticion);
    }
    
}
