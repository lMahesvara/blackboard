/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.Blackboard;
import entidades.Usuario;
import helpers.Peticiones;
import static helpers.Peticiones.NOTIFICAR_CLIENTE;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import peticiones.AbstractPeticion;
import peticiones.PeticionUsuario;

/**
 *
 * @author Vastem
 */
public class CerrarSesion extends AbstractFuente{
    private final IConexionBD conexionBD;
    private final EntityManager em;

    public CerrarSesion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }
    
    @Override
    public void procesar(AbstractPeticion peticion) {
        PeticionUsuario pU = (PeticionUsuario) peticion;
        try {
            Usuario usuario = pU.getUsuario();
            construirPeticionNotificarCliente(usuario, peticion.getHashcodeSC());
        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void construirPeticionNotificarCliente(Usuario usuario, Integer hashcodeSC) {
        agregarProblema(new PeticionUsuario(NOTIFICAR_CLIENTE, Peticiones.CERRAR_SESION, hashcodeSC, null, usuario));
    }
    
}
