package interfaces;

import peticiones.AbstractPeticion;

public interface IFachadaBlackboard {
    public boolean existenPeticiones();
    public AbstractPeticion getPeticion();
    public void removePeticion();
}
