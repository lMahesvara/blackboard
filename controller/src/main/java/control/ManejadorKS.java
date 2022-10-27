
package control;

import entidades.BlackBoardObject;
import fuentes.AgregarUsuario;
import static helpers.Peticiones.*;
import interfaces.AbstractFuente;

public class ManejadorKS {
    
    public void ejecutar(BlackBoardObject bbo){
        if(bbo.getPeticion().equals(REGISTRAR_USUARIO)){
            AbstractFuente fuente = new AgregarUsuario();
            fuente.procesar(bbo);
        }
    }
}
