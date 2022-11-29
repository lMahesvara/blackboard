/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.Blackboard;
import static helpers.Peticiones.CONSULTAR_NOTIFICACIONES;
import static helpers.Peticiones.NOTIFICAR_CLIENTE;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import peticiones.AbstractPeticion;
import peticiones.PeticionNotificaciones;

/**
 *
 * @author Vastem
 */
public class ConsultarNotificaciones extends AbstractFuente{
    private final IConexionBD conexionBD;
    private final EntityManager em;

    public ConsultarNotificaciones(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();

    }

    public void procesar(AbstractPeticion peticion) {
        PeticionNotificaciones pNotis = (PeticionNotificaciones) peticion;

        try {
            em.getTransaction().begin();
            //TODO: Checar si se regresa la mas reciente al inicio
            
//            Query query = em.createQuery(
//                "SELECT u "
//                + "FROM Usuario u "
//                + "WHERE u.usuario = :usuario");
//
//        query.setParameter("usuario", usuario);

            Query query = em.createQuery(
                    "SELECT n "
                    + "FROM Notificacion n "
                    + "WHERE n.remitente = :usuario");
            
            query.setParameter("usuario", pNotis.getUsuario());

            pNotis.setNotificaciones(query.getResultList());

            em.getTransaction().commit();
            em.close();

            //No se si deba loguearse esto
            //construirPeticionLog(pP);
            construirPeticionNotificarCliente(pNotis);
        } catch (Exception e) {
        }
    }

    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }

    public void construirPeticionNotificarCliente(PeticionNotificaciones peticion) {
        agregarProblema(new PeticionNotificaciones(NOTIFICAR_CLIENTE, CONSULTAR_NOTIFICACIONES, peticion.getHashcodeSC(), null, peticion.getNotificaciones()));
    }
}
