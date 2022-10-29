

package conexion;

import interfaces.IConexionBD;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author robertorw
 */
public class ConexionBD implements IConexionBD{

    @Override
    public EntityManager crearConexion() throws IllegalStateException {
        //Obtiene acceso alemFactory a partir de la persistence unit utilizada
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("persistencia"); 
        // Creamos una em(bd) para realizar operaciones con la bd
        EntityManager em = emFactory.createEntityManager();
        return em;
    }
    
}
