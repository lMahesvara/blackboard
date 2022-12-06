/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.Blackboard;
import conexion.ConexionBD;
import entidades.Usuario;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import static helpers.Peticiones.NOTIFICAR_CLIENTE;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import peticiones.AbstractPeticion;
import peticiones.PeticionLog;
import peticiones.PeticionUsuario;

/**
 *
 * @author germa
 */
public class IniciarSesionFb extends AbstractFuente {
    
    private final IConexionBD conexionBD;
    private final EntityManager em;

    public IniciarSesionFb (IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    @Override
    public void procesar(AbstractPeticion peticion) {
        PeticionUsuario pU = (PeticionUsuario) peticion;
        try {
            Usuario usuario = validarUsuario(pU.getUsuario());
            if (usuario != null) {
                construirPeticionLog(usuario);
            }

            construirPeticionNotificarCliente(usuario, peticion.getHashcodeSC());

        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void construirPeticionLog(Usuario u) {
        String mensaje = "[INICIO DE SESION] [username: " + u.getUsuario() + "]";
        AbstractPeticion peticion = new PeticionLog(LOGGEAR_INFO, mensaje);
        this.agregarProblema(peticion);
    }

    public void construirPeticionNotificarCliente(Usuario usuario, Integer hashcodeSC) {
        agregarProblema(new PeticionUsuario(NOTIFICAR_CLIENTE, Peticiones.INICIAR_SESION_FB, hashcodeSC, null, usuario));
    }

    public Usuario validarUsuario(Usuario usuario) {
        em.getTransaction().begin();

        Query query = em.createQuery(
                "SELECT u "
                + "FROM Usuario u "
                + "WHERE u.token = :token");
        
        System.out.println(usuario.getToken());
        query.setParameter("token", usuario.getToken());

        List<Usuario> usuarios = query.getResultList();

        em.getTransaction().commit();
        if (usuarios.isEmpty()) {
            System.out.println("-------------------------");
            System.out.println("No existe el usuario");
            System.out.println("-------------------------");
            return agregarUsuarioFb(usuario);
        }
         em.close();
         return usuarios.get(0);
    }
    
    public Usuario agregarUsuarioFb(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        return validarUsuario(usuario);
    }

}
