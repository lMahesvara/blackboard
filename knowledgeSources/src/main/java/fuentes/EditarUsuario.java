/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.Blackboard;
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
import peticiones.PeticionUsuario;

/**
 *
 * @author Vastem
 */
public class EditarUsuario extends AbstractFuente {
    private final IConexionBD conexionBD;
    private final EntityManager em;

    public EditarUsuario(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }
    
    public void procesar(AbstractPeticion peticion){
        PeticionUsuario pU = (PeticionUsuario)peticion;
        try {
            Usuario usuario = pU.getUsuario();
            
            if(existeUsuario(usuario.getUsuario())){
                pU.setUsuario(null);
                construirPeticionNotificarClientes(pU);
                return;
            }
            
            em.getTransaction().begin();
            
            Usuario user = em.find(Usuario.class, usuario.getId());
            if(user == null){
                return;
            }
            
            
            user.setUsuario(usuario.getUsuario());
            user.setPassword(usuario.getPassword());
            user.setCorreo(usuario.getCorreo());
            user.setFechaNacimiento(usuario.getFechaNacimiento());
            user.setNumeroCelular(usuario.getNumeroCelular());
            user.setSexo(usuario.getSexo());
            
            em.persist(user);
            em.getTransaction().commit();
            em.close();
            
            construirPeticionLog(pU);
            construirPeticionNotificarClientes(pU);
            
        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void construirPeticionLog(PeticionUsuario peticion){
        String mensaje = "[USUARIO ACTUALIZADO] [username: "+ peticion.getUsuario().getUsuario() +"]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        this.agregarProblema(nuevaPeticion);
    }
    
    public void construirPeticionNotificarClientes(PeticionUsuario peticion){
        agregarProblema(new PeticionUsuario(Peticiones.NOTIFICAR_TODOS, Peticiones.ACTUALIZAR_USUARIO, peticion.getHashcodeSC(), null,peticion.getUsuario()));
    }
    
    public boolean existeUsuario(String usuario) {

        em.getTransaction().begin();

        Query query = em.createQuery(
                "SELECT u "
                + "FROM Usuario u "
                + "WHERE u.usuario = :usuario");

        query.setParameter("usuario", usuario);

        List<Usuario> usuarios = query.getResultList();

        em.getTransaction().commit();

        if (usuarios.isEmpty()) {
            return false;
        }
        em.close();
        System.out.println("-------------------------");
        System.out.println("El usuario ya existe");
        System.out.println("-------------------------");
        return true;
    }
}
