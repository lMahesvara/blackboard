
package interfaces;

import peticiones.AbstractPeticion;


public interface IFuentesFactory {
    public AbstractFuente crearFuente(AbstractPeticion peticion);
}
