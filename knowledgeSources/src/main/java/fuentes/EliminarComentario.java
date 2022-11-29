package fuentes;

import blackboard.Blackboard;
import entidades.Comentario;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import peticiones.AbstractPeticion;
import peticiones.PeticionLog;
import peticiones.PeticionComentario;

public class EliminarComentario extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    public EliminarComentario(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    @Override
    public void procesar(AbstractPeticion peticion) {
        PeticionComentario pC = (PeticionComentario) peticion;
        Comentario comentario = pC.getComentario();
        try {
            em.getTransaction().begin();
            if (comentario == null) {
                pC.setComentario(null);
                return;
            }
            Comentario c = em.find(Comentario.class, comentario.getId());
            em.remove(c);
            em.getTransaction().commit();
            em.close();

            construirPeticionLog(pC);
            construirPeticionNotificarClientes(pC);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }

    public void construirPeticionLog(PeticionComentario peticion) {
        String mensaje = "[COMENTARIO ELIMINADO] [ID Usuario: " + peticion.getComentario().getUsuario() + "]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        agregarProblema(nuevaPeticion);
    }

    public void construirPeticionNotificarClientes(PeticionComentario peticion) {
        agregarProblema(new PeticionComentario(Peticiones.NOTIFICAR_TODOS, Peticiones.ELIMINAR_COMENTARIO, peticion.getHashcodeSC(), null, peticion.getComentario()));
    }

}
