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

public class AgregarPublicacion extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    public AgregarPublicacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    public void procesar(AbstractPeticion peticion) {
        Session session = em.unwrap(Session.class);
        PeticionPublicacion pP = (PeticionPublicacion) peticion;
        Publicacion publicacion = pP.getPublicacion();

        List<Hashtag> hashtags = publicacion.getHashtag();
        publicacion.setHashtag(null);
        try {
            session.getTransaction().begin();
            Long id = (Long)session.save(publicacion);
            session.getTransaction().commit();
            
            session.getTransaction().begin();
            Publicacion p = new Publicacion(id);
            
            for(int i = 0; i < hashtags.size(); i++){
                hashtags.get(i).setPublicacion(p);
                session.save(hashtags.get(i));
            }
            session.getTransaction().commit();
            
            session.close();
            
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
