package fuentes;

import blackboard.FachadaBlackboard;
import interfaces.AbstractFuente;
import interfaces.IFachadaBlackboard;
import peticiones.AbstractPeticion;

public class NotificarClientes extends AbstractFuente {

    @Override
    public void procesar(AbstractPeticion peticion) {
        IFachadaBlackboard fachadaBlack = new FachadaBlackboard();
        fachadaBlack.notificarTodo(peticion);
    }
}
