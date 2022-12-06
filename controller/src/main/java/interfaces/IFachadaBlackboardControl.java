package interfaces;

import peticiones.AbstractPeticion;

public interface IFachadaBlackboardControl {
    public boolean existenPeticiones();
    public AbstractPeticion getPeticion();
    public void removePeticion();
    public void addPeticion(AbstractPeticion peticion);
}
