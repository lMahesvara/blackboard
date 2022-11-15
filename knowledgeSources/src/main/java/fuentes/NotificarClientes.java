package fuentes;

import blackboard.Server;
import interfaces.AbstractFuente;
import peticiones.AbstractPeticion;

public class NotificarClientes extends AbstractFuente {

    @Override
    public void procesar(AbstractPeticion peticion) {
        Server server = Server.getInstance();
        server.notificarTodos(peticion);
    }

    @Override
    public void agregarProblema(AbstractPeticion peticion) {
        
    }

}
