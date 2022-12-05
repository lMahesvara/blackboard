
package control;

import blackboard.Blackboard;
import peticiones.AbstractPeticion;
import interfaces.IFachadaBlackboardControl;

public class FachadaBlackboardControl implements IFachadaBlackboardControl {

    private Blackboard blackboard;

    /**
     * Constructor
     */
    public FachadaBlackboardControl() {
        blackboard = Blackboard.getInstance();
    }

    /**
     * Pregunta si existen peticiones
     */
    @Override
    public boolean existenPeticiones() {
        return blackboard.existenPeticiones();
    }

    /**
     * Obtiene una peticion del blackboard
     * @return 
     */
    @Override
    public AbstractPeticion getPeticion() {
        return blackboard.getPeticion();
    }

    /**
     * Remueve una peticion del blackboard
     */
    @Override
    public void removePeticion() {
        blackboard.removePeticion();
    }

    /**
     * A;ade una peticoion al blackboard
     */
    @Override
    public void addPeticion(AbstractPeticion peticion) {
        blackboard.addProblem(peticion);
    }

}
