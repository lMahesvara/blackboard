/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

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
 * @author Vastem
 */
public class IniciarSesion extends AbstractFuente {
    private final IConexionBD conexionBD;
    private final EntityManager em;

    /**
     * Constructor por defecto  
     * @param conexionBD Conexion a la base de datos
     */
    public IniciarSesion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }
    
    /**
     * Se encarga de procesar la petición
     * @param peticion Petición a procesar
     */
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

    /**
     * Construye una petición para logear lo sucedido
     */
    public void construirPeticionLog(Usuario u) {
        String mensaje = "[INICIO DE SESION] [username: " + u.getUsuario() + "]";
        AbstractPeticion peticion = new PeticionLog(LOGGEAR_INFO, mensaje);
        agregarProblema(peticion);
    }

    /**
     * Construye una petición para notificar al cliente
     */ 
    public void construirPeticionNotificarCliente(Usuario usuario, Integer hashcodeSC) {
        agregarProblema(new PeticionUsuario(NOTIFICAR_CLIENTE, Peticiones.INICIAR_SESION, hashcodeSC, null, usuario));
    }
    
    /*
    * Valida si el usuario existe
    */
    public Usuario validarUsuario(Usuario usuario) {
        em.getTransaction().begin();

        Query query = em.createQuery(
                "SELECT u "
                + "FROM Usuario u "
                + "WHERE u.usuario = :usuario");

        query.setParameter("usuario", usuario.getUsuario());

        List<Usuario> usuarios = query.getResultList();

        em.getTransaction().commit();
        if (usuarios.isEmpty()) {
            System.out.println("-------------------------");
            System.out.println("No existe el usuario");
            System.out.println("-------------------------");
            return null;
        }else if (usuarios.get(0).getPassword().equals(usuario.getPassword())) {
            return usuarios.get(0);
        } 

        em.close();
        System.out.println("-------------------------");
        System.out.println("No se inicio sesión");
        System.out.println("-------------------------");
        return null;
    }

}
