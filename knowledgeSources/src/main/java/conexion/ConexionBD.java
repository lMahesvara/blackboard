

package conexion;

import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author robertorw
 */
public class ConexionBD implements IConexionBD{

    /**
     * Crea la conexion con la base de datos
     * @return Conexion con la base de datos     
     */
    @Override
    public EntityManager crearConexion() throws IllegalStateException {
        //Obtiene acceso alemFactory a partir de la persistence unit utilizada
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("persistencia"); 
        // Creamos una em(bd) para realizar operaciones con la bd
        EntityManager em = emFactory.createEntityManager();
        return em;
    }
    
}
