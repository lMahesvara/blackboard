/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.Blackboard;
import entidades.Usuario;
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
public class IniciarSesion extends AbstractFuente{
    private final IConexionBD conexionBD;
    private final EntityManager em;
    
    public IniciarSesion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }
    
    @Override
    public void procesar(AbstractPeticion peticion) {
        PeticionUsuario pU = (PeticionUsuario)peticion;
        try {
            Usuario usuario = pU.getUsuario();
            if (!validarUsuario(usuario)) {
                return;
            }
            
            construirPeticionLog(usuario);
            construirPeticionNotificarCliente(usuario);
            
        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }
    
    public void construirPeticionLog(Usuario u){
        String mensaje = "[INICIO DE SESION] [username: "+ u.getUsuario() +"]";
        AbstractPeticion peticion = new PeticionLog(LOGGEAR_INFO, mensaje);
        this.agregarProblema(peticion);
    }
    
    public void construirPeticionNotificarCliente(Usuario usuario){
        // 
        agregarProblema(new PeticionUsuario(NOTIFICAR_CLIENTE,usuario));
    }
    
    public boolean validarUsuario(Usuario usuario) {
        em.getTransaction().begin();

        Query query = em.createQuery(
                "SELECT u "
                + "FROM Usuario u "
                + "WHERE u.usuario = :usuario");

        query.setParameter("usuario", usuario.getUsuario());

        List<Usuario> usuarios = query.getResultList();

        em.getTransaction().commit();

        if (usuarios.get(0).getPassword().equals(usuario.getPassword())) {
            return true;
        }
        else if(usuarios.isEmpty()){
            System.out.println("-------------------------");
            System.out.println("No existe el usuario");
            System.out.println("-------------------------");
            return false;
        }
        
        em.close();
        System.out.println("-------------------------");
        System.out.println("No se inicio sesión");
        System.out.println("-------------------------");
        return false;
    }
    
}
