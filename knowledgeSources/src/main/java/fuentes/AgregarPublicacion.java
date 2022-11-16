package fuentes;

import blackboard.Blackboard;
import entidades.Publicacion;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import peticiones.AbstractPeticion;
import peticiones.PeticionLog;
import peticiones.PeticionPublicacion;

public class AgregarPublicacion extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    public AgregarPublicacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    public void procesar(AbstractPeticion peticion) {
        PeticionPublicacion pP = (PeticionPublicacion) peticion;
        Publicacion publicacion = pP.getPublicacion();
        try {
            em.getTransaction().begin();
            em.persist(publicacion);
            em.getTransaction().commit();
            em.close();

            construirPeticionLog(pP);
            construirPeticionNotificarClientes(pP);
        } catch (Exception e) {
        }
    }

    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }

    public void construirPeticionLog(PeticionPublicacion peticion) {
        String mensaje = "[PUBLICACIÓN AGREGADA] [ID Usuario: " + peticion.getPublicacion().getUsuario() + "]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        agregarProblema(nuevaPeticion);
    }

    public void construirPeticionNotificarClientes(PeticionPublicacion peticion) {
        agregarProblema(new PeticionPublicacion(Peticiones.NOTIFICAR_TODOS, Peticiones.REGISTRAR_PUBLICACION, peticion.getHashcodeSC(), null, peticion.getPublicacion()));
    }

}
