
package blackboard;

import entidades.BlackBoardObject;
import peticiones.AbstractPeticion;

public class HiloSocket extends Thread{
    private AbstractPeticion peticion;

    public HiloSocket(AbstractPeticion peticion) {
        this.peticion = peticion;
    }

    public HiloSocket() {
    }

    @Override
    public void run() {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
        this.interrupt();
    }
    
    
}
