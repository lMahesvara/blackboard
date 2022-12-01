package fuentes;

import blackboard.Blackboard;
import entidades.Hashtag;
import entidades.Publicacion;
import static helpers.Peticiones.CONSULTAR_PUBLICACIONES;
import static helpers.Peticiones.CONSULTAR_PUBLICACIONES_HASHTAG;
import static helpers.Peticiones.NOTIFICAR_CLIENTE;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import peticiones.AbstractPeticion;
import peticiones.PeticionPublicaciones;

public class ConsultarPublicaciones extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    public ConsultarPublicaciones(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();

    }

    public void procesar(AbstractPeticion peticion) {
        PeticionPublicaciones pP = (PeticionPublicaciones) peticion;

        try {
            List<Publicacion> publicaciones = new ArrayList();
            em.getTransaction().begin();
            //TODO: Checar si se regresa la mas reciente al inicio
            
            Query query;
            if(pP.getPeticion().equals(CONSULTAR_PUBLICACIONES)){
                query = em.createQuery(
                    "SELECT p "
                    + "FROM Publicacion p ");
                
                publicaciones = query.getResultList();
                
            }else if (pP.getPeticion().equals(CONSULTAR_PUBLICACIONES_HASHTAG)){
                query = em.createQuery(
                    "SELECT h "
                    + "FROM Hashtag h "
                    + "WHERE h.nombre = :nombre");
                query.setParameter("nombre", pP.getHashtag().getNombre());
                List<Hashtag> hashtags = query.getResultList();
                for(int i=0; i < hashtags.size();i++){
                    query = em.createQuery(
                        "SELECT p "
                        + "FROM Publicacion p "
                        + "WHERE p.id = :id"); 
                    query.setParameter("id", hashtags.get(i).getPublicacion().getId());
                    publicaciones.add((Publicacion) query.getSingleResult());
                }
            }
            
            pP.setPublicaciones(publicaciones);

            em.getTransaction().commit();
            em.close();

            //No se si deba loguearse esto
            //construirPeticionLog(pP);
            construirPeticionNotificarCliente(pP);
        } catch (Exception e) {
        }
    }

    public void construirPeticionNotificarCliente(PeticionPublicaciones peticion) {
        if(peticion.getPeticion().equals(CONSULTAR_PUBLICACIONES)){
            agregarProblema(new PeticionPublicaciones(NOTIFICAR_CLIENTE, CONSULTAR_PUBLICACIONES, peticion.getHashcodeSC(), null, peticion.getPublicaciones()));
        }else if (peticion.getPeticion().equals(CONSULTAR_PUBLICACIONES_HASHTAG)){
            agregarProblema(new PeticionPublicaciones(NOTIFICAR_CLIENTE, CONSULTAR_PUBLICACIONES_HASHTAG, peticion.getHashcodeSC(), null, peticion.getPublicaciones()));
        }
    }
}
