

package fuentes;

import static helpers.Peticiones.LOGGEAR_ADVERTENCIA;
import static helpers.Peticiones.LOGGEAR_ERROR;
import static helpers.Peticiones.LOGGEAR_INFO;
import interfaces.AbstractFuente;
import org.apache.logging.log4j.*;
import peticiones.AbstractPeticion;
import peticiones.PeticionLog;

public class LoguearTransaccion extends AbstractFuente{
    
    private static Logger log = LogManager.getLogger(LoguearTransaccion.class);

    public LoguearTransaccion(){} 

    @Override
    public void procesar(AbstractPeticion peticion){
//        PeticionLog pL = (PeticionLog)peticion;
//        definirTransaccion(pL);
    }
    
    @Override
    public void agregarProblema(AbstractPeticion peticion){
        
    }

    public void definirTransaccion(PeticionLog pL){
        if(pL.getPeticion().equals(LOGGEAR_INFO)){
            log.info(pL.getMensaje());
        }else if(pL.getPeticion().equals(LOGGEAR_ERROR)){
            log.error(pL.getMensaje());
        }else if(pL.getPeticion().equals(LOGGEAR_ADVERTENCIA)){
            log.warn(pL.getMensaje());
        }
    }
}
