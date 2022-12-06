
package blackboard;

import entidades.BlackBoardObject;
import peticiones.AbstractPeticion;

public class HiloSocket extends Thread{
    private AbstractPeticion peticion;

    /**
     * Constructor
     */
    public HiloSocket(AbstractPeticion peticion) {
        this.peticion = peticion;
    }

    public HiloSocket() {
    }

    /**
     * Corre el hilo del blackboard
     */
    @Override
    public void run() {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
        this.interrupt();
    }
    
    
}
