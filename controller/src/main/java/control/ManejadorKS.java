
package control;

import fabricas.FuentesFactory;
import interfaces.AbstractFuente;
import interfaces.IFuentesFactory;
import interfaces.IManejadorKS;
import peticiones.AbstractPeticion;

public class ManejadorKS implements IManejadorKS{
    
    private IFuentesFactory fuentesFactory;
    
    public ManejadorKS(){
        this.fuentesFactory = new FuentesFactory();
    }
    
    @Override
    public void ejecutar(AbstractPeticion peticion){
        AbstractFuente fuente = fuentesFactory.crearFuente(peticion);
        fuente.procesar(peticion);
        
//        if(peticion.getPeticion().equals(REGISTRAR_USUARIO)){
//            AbstractFuente fuente = fuentesFactory.crearAgregarUsuario();
//            fuente.procesar(peticion);
//        }else if(peticion.getPeticion().equals(LOGGEAR)){
//            AbstractFuente fuente = fuentesFactory.crearLogTransacciones();
//            fuente.procesar(peticion);
//        }
    }
}
