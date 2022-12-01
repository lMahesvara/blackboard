
package control;

import blackboard.Blackboard;
import peticiones.AbstractPeticion;
import interfaces.IFachadaBlackboardControl;

public class FachadaBlackboardControl implements IFachadaBlackboardControl {

    private Blackboard blackboard;

    public FachadaBlackboardControl() {
        blackboard = Blackboard.getInstance();
    }

    @Override
    public boolean existenPeticiones() {
        return blackboard.existenPeticiones();
    }

    @Override
    public AbstractPeticion getPeticion() {
        return blackboard.getPeticion();
    }

    @Override
    public void removePeticion() {
        blackboard.removePeticion();
    }

    @Override
    public void addPeticion(AbstractPeticion peticion) {
        blackboard.addProblem(peticion);
    }

}
