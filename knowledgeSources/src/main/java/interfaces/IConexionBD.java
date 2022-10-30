
package interfaces;

import jakarta.persistence.EntityManager;

public interface IConexionBD {
    public EntityManager crearConexion() throws IllegalStateException;
}
