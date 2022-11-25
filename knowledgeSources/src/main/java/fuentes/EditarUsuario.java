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
            construirPeticionNotificarCliente(pU);
            
        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void agregarProblema(AbstractPeticion peticion){
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }
    
    public void construirPeticionLog(PeticionUsuario peticion){
        String mensaje = "[USUARIO ACTUALIZADO] [username: "+ peticion.getUsuario().getUsuario() +"]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        this.agregarProblema(nuevaPeticion);
    }
    
    public void construirPeticionNotificarCliente(PeticionUsuario peticion){
        agregarProblema(new PeticionUsuario(Peticiones.NOTIFICAR_CLIENTE, Peticiones.ACTUALIZAR_USUARIO, peticion.getHashcodeSC(), null,peticion.getUsuario()));
    }
    
}
