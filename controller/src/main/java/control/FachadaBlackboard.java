
package control;

import blackboard.Blackboard;
import interfaces.IFachadaBlackboard;
import peticiones.AbstractPeticion;

public class FachadaBlackboard implements IFachadaBlackboard {

    private Blackboard blackboard;

    public FachadaBlackboard() {
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
