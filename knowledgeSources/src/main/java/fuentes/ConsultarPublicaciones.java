package fuentes;

import blackboard.Blackboard;
import static helpers.Peticiones.CONSULTAR_PUBLICACIONES;
import static helpers.Peticiones.NOTIFICAR_CLIENTE;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
            em.getTransaction().begin();
            //TODO: Checar si se regresa la mas reciente al inicio
            Query query = em.createQuery(
                    "SELECT p "
                    + "FROM Publicacion p ");

            pP.setPublicaciones(query.getResultList());

            em.getTransaction().commit();
            em.close();

            //No se si deba loguearse esto
            //construirPeticionLog(pP);
            construirPeticionNotificarCliente(pP);
        } catch (Exception e) {
        }
    }

    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }

    public void construirPeticionNotificarCliente(PeticionPublicaciones peticion) {
        agregarProblema(new PeticionPublicaciones(NOTIFICAR_CLIENTE, CONSULTAR_PUBLICACIONES, peticion.getHashcodeSC(), null, peticion.getPublicaciones()));
    }
}
