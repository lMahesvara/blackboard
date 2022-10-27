
package control;

import entidades.BlackBoardObject;

public class Control {

    public Control() {
    }
    
    
    
    public void update(BlackBoardObject bbo){
        ManejadorKS manejador = new ManejadorKS();
        manejador.ejecutar(bbo);
    }
    
}
