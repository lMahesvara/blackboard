
package control;

import entidades.BlackBoardObject;
import fabricas.FuentesFactory;
import static helpers.Peticiones.*;
import interfaces.AbstractFuente;
import interfaces.IFuentesFactory;
import interfaces.IManejadorKS;

public class ManejadorKS implements IManejadorKS{
    
    private IFuentesFactory fuentesFactory;
    
    public ManejadorKS(){
        this.fuentesFactory = new FuentesFactory();
    }
    
    @Override
    public void ejecutar(BlackBoardObject bbo){
        if(bbo.getPeticion().equals(REGISTRAR_USUARIO)){
            AbstractFuente fuente = fuentesFactory.crearAgregarUsuario();
            fuente.procesar(bbo);
        }else if(bbo.getPeticion().equals(LOGGEAR)){
            AbstractFuente fuente = fuentesFactory.crearLogTransacciones();
            fuente.procesar(bbo);
        }
    }
}
