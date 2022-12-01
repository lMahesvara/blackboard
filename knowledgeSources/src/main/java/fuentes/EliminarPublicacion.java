package fuentes;

import blackboard.Blackboard;
import entidades.Hashtag;
import entidades.Publicacion;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.hibernate.Session;
import peticiones.AbstractPeticion;
import peticiones.PeticionLog;
import peticiones.PeticionPublicacion;

public class EliminarPublicacion extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    public EliminarPublicacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    public void procesar(AbstractPeticion peticion) {
        PeticionPublicacion pP = (PeticionPublicacion) peticion;
        Publicacion publicacion = pP.getPublicacion();
        try {
            em.getTransaction().begin();
            if (publicacion == null) {
                pP.setPublicacion(null);
                return;
            }
            Publicacion p =em.find(Publicacion.class, publicacion.getId());
            em.remove(p);
            em.getTransaction().commit();
            em.close();

            construirPeticionLog(pP);
            construirPeticionNotificarClientes(pP);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    public void construirPeticionLog(PeticionPublicacion peticion) {
        String mensaje = "[PUBLICACIÓN ELIMINADA] [ID Usuario: " + peticion.getPublicacion().getUsuario() + "]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        agregarProblema(nuevaPeticion);
    }

    public void construirPeticionNotificarClientes(PeticionPublicacion peticion) {
        agregarProblema(new PeticionPublicacion(Peticiones.NOTIFICAR_TODOS, Peticiones.ELIMINAR_PUBLICACION, peticion.getHashcodeSC(), null, peticion.getPublicacion()));
    }

}
