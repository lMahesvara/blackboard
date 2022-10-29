
package control;

import entidades.BlackBoardObject;
import interfaces.IManejadorKS;

public class Control {

    IManejadorKS manejadorKS;
    
    public Control() {
        this.manejadorKS = new ManejadorKS();
    }
    
    public void update(BlackBoardObject bbo){
        manejadorKS.ejecutar(bbo);
    }
    
}
