

package fuentes;

import entidades.BlackBoardObject;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;

public class LogTransacciones extends AbstractFuente{
    
    private final IConexionBD conexionBD;
    private final EntityManager em;
    
    public LogTransacciones(IConexionBD conexionBD){
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    } 
    
    public void procesar(BlackBoardObject bbo){
        
    }
    public void agregarProblema(){}
}
