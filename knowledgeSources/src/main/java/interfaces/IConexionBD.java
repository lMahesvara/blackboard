
package interfaces;

import javax.persistence.EntityManager;

public interface IConexionBD {
    public EntityManager crearConexion() throws IllegalStateException;
}
