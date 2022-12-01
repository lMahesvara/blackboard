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

    public FachadaBlackboard() {
        blackboard = Blackboard.getInstance();
        serverInstance = Server.getInstance();
    }
    
    @Override
    public void addProblem(AbstractPeticion peticion) {
        blackboard.addProblem(peticion);
    }

    @Override
    public void notificarCliente(AbstractPeticion peticion) {
        serverInstance.notificarCliente(peticion);
    }

    @Override
    public void notificarTodo(AbstractPeticion peticion) {
        serverInstance.notificarTodos(peticion);
    }
    
}
