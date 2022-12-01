
package interfaces;

import blackboard.Blackboard;
import peticiones.AbstractPeticion;

public abstract class AbstractFuente {
    public void procesar(AbstractPeticion peticion){}
    public void agregarProblema(AbstractPeticion peticion){
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }
}
