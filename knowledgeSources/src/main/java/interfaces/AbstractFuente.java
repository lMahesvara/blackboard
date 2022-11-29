
package interfaces;

import peticiones.AbstractPeticion;

public abstract class AbstractFuente {
    public void procesar(AbstractPeticion peticion){}
    public void agregarProblema(AbstractPeticion peticion){}
}
