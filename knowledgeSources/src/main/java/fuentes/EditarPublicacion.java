package fuentes;

import blackboard.Blackboard;
import entidades.Hashtag;
import entidades.Publicacion;
import entidades.Usuario;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import peticiones.AbstractPeticion;
import peticiones.PeticionLog;
import peticiones.PeticionPublicacion;
import peticiones.PeticionUsuario;

public class EditarPublicacion extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    public EditarPublicacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    public void procesar(AbstractPeticion peticion) {
        PeticionPublicacion pP = (PeticionPublicacion) peticion;
        try {
            Publicacion publicacion = pP.getPublicacion();

            em.getTransaction().begin();

            Publicacion publi = em.find(Publicacion.class, publicacion.getId());
            if (publicacion == null) {
                return;
            }
            
            //Eliminar hashtags antiguos
            Query query = em.createNativeQuery("delete from hashtags where id_publicacion = ?");
            query.setParameter(1, publi.getId());
            query.executeUpdate();
            em.getTransaction().commit();
            
            //Editar Publicacion
            em.getTransaction().begin();
            List<Hashtag> hashtags = publicacion.getHashtag();
            publi.setHashtag(null);
            publi.setTexto(publicacion.getTexto());
            publi.setImagen(publicacion.getImagen());

            em.persist(publi);
            em.getTransaction().commit();
            
            
            //Crear hashtags
            em.getTransaction().begin();
            hashtags.forEach(hashtag -> {
                hashtag.setPublicacion(publi);
                em.persist(hashtag);
            });
            em.getTransaction().commit();
            em.close();

            construirPeticionLog(pP);
            construirPeticionNotificarCliente(pP);

        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }

    public void construirPeticionLog(PeticionPublicacion peticion) {
        String mensaje = "[PUBLICACIÓN ACTUALIZADA] [username: " + peticion.getPublicacion().getUsuario().getUsuario() + "]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        this.agregarProblema(nuevaPeticion);
    }

    public void construirPeticionNotificarCliente(PeticionPublicacion peticion) {
        agregarProblema(new PeticionPublicacion(Peticiones.NOTIFICAR_CLIENTE, Peticiones.ACTUALIZAR_PUBLICACION, peticion.getHashcodeSC(), null, peticion.getPublicacion()));
    }
}
