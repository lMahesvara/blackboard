
package control;

import entidades.BlackBoardObject;
import interfaces.IManejadorKS;
import peticiones.AbstractPeticion;

public class Control {

    IManejadorKS manejadorKS;
    
    public Control() {
        this.manejadorKS = new ManejadorKS();
    }
    
    public void update(AbstractPeticion peticion){
        manejadorKS.ejecutar(peticion);
    }
    
}
