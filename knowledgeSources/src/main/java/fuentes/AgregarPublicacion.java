package fuentes;

import entidades.Etiqueta;
import entidades.Hashtag;
import entidades.Publicacion;
import entidades.Usuario;
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
import jakarta.persistence.Query;

public class AgregarPublicacion extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    /**
     * Constructor por defecto  
     * @param conexionBD Conexion a la base de datos
     */
    public AgregarPublicacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    /**
     * Se encarga de procesar la petición
     * @param peticion Petición a procesar
     */
    public void procesar(AbstractPeticion peticion) {
        Session session = em.unwrap(Session.class);
        PeticionPublicacion pP = (PeticionPublicacion) peticion;
        Publicacion publicacion = pP.getPublicacion();

        List<Hashtag> hashtags = publicacion.getHashtag();
        List<Etiqueta> etiquetas = publicacion.getEtiquetas();
        publicacion.setHashtag(null);
        publicacion.setEtiquetas(null);
        try {
            //Introducir nueva publicación a la BD
            session.getTransaction().begin();
            Long id = (Long)session.save(publicacion);
            session.getTransaction().commit();
            
            Publicacion p = new Publicacion(id);
            
            //Introducir los hashtags a la base de datos
            session.getTransaction().begin();
            for(int i = 0; i < hashtags.size(); i++){
                hashtags.get(i).setPublicacion(p);
                session.save(hashtags.get(i));
            }
            session.getTransaction().commit();
            
            //Introducir las etiquetas a la base de datos
            session.getTransaction().begin();
            for(int i = 0; i < etiquetas.size(); i++){
                Query query = session.createQuery(
                                                  "SELECT u "
                                                  + "FROM Usuario u "
                                                  + "WHERE u.usuario = :usuario");
                query.setParameter("usuario", etiquetas.get(i).getEtiquetado().getUsuario());
                Usuario user = (Usuario) query.getSingleResult();
                
                if(user != null){
                    etiquetas.get(i).setPublicacion(p);
                    etiquetas.get(i).setEtiquetado(user);
                    session.save(etiquetas.get(i));  
                }
            }
            session.getTransaction().commit();
            session.close();
            
            construirPeticionLog(pP);
            construirPeticionNotificarClientes(pP);
        } catch (Exception e) {
        }
    }

    /**
     * Construye una petición para logear lo sucedido
     * @param peticion Petición a agregar al blackboard
     */
    public void construirPeticionLog(PeticionPublicacion peticion) {
        String mensaje = "[PUBLICACIÓN AGREGADA] [ID Usuario: " + peticion.getPublicacion().getUsuario() + "]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        agregarProblema(nuevaPeticion);
    }

    /**
     * Construye una petición para notificar a los clientes conectados
     * @param peticion Petición a agregar al blackboard
     */ 
    public void construirPeticionNotificarClientes(PeticionPublicacion peticion) {
        agregarProblema(new PeticionPublicacion(Peticiones.NOTIFICAR_TODOS, Peticiones.REGISTRAR_PUBLICACION, peticion.getHashcodeSC(), null, peticion.getPublicacion()));
    }

}
