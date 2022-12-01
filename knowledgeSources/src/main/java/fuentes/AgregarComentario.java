package fuentes;

import blackboard.Blackboard;
import entidades.Comentario;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import peticiones.AbstractPeticion;
import peticiones.PeticionComentario;
import peticiones.PeticionLog;

public class AgregarComentario extends AbstractFuente{
    private final IConexionBD conexionBD;
    private final EntityManager em;

    public AgregarComentario(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }
    
    public void procesar(AbstractPeticion peticion) {
        PeticionComentario pC = (PeticionComentario) peticion;
        Comentario comentario = pC.getComentario();
        try {
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
            em.close();

            construirPeticionLog(pC);
            construirPeticionNotificarClientes(pC);
        } catch (Exception e) {
        }
    }

    public void construirPeticionLog(PeticionComentario peticion) {
        String mensaje = "[COMENTARIO AGREGADO] [ID Usuario: " + peticion.getComentario().getUsuario() + "]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        agregarProblema(nuevaPeticion);
    }

    public void construirPeticionNotificarClientes(PeticionComentario peticion) {
        agregarProblema(new PeticionComentario(Peticiones.NOTIFICAR_TODOS, Peticiones.AGREGAR_COMENTARIO, peticion.getHashcodeSC(), null, peticion.getComentario()));
    }
}
