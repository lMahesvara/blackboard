
package interfaces;

import blackboard.FachadaBlackboard;
import peticiones.AbstractPeticion;

public abstract class AbstractFuente {
    private IFachadaBlackboard fachadaBlackboard;
    
    public void procesar(AbstractPeticion peticion){}
    public void agregarProblema(AbstractPeticion peticion){
        fachadaBlackboard = new FachadaBlackboard();
        fachadaBlackboard.addProblem(peticion);
    }
}
