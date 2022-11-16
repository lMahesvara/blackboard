package fuentes;

import blackboard.Blackboard;
import entidades.Usuario;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import static helpers.Peticiones.NOTIFICAR_TODOS;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import peticiones.AbstractPeticion;
import peticiones.PeticionLog;
import peticiones.PeticionUsuario;

public class AgregarUsuario extends AbstractFuente {

    private final IConexionBD conexionBD;
    private final EntityManager em;

    public AgregarUsuario(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }

    @Override
    public void procesar(AbstractPeticion peticion) {
        PeticionUsuario pU = (PeticionUsuario)peticion;
        try {
            Usuario usuario = pU.getUsuario();
            if (existeUsuario(usuario.getUsuario())) {
                return;
            }
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            em.close();
            
            construirPeticionLog(pU);
            construirPeticionNotificarClientes(pU);
            
        } catch (Exception ex) {
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }

    public void construirPeticionLog(PeticionUsuario peticion){
        String mensaje = "[USUARIO AGREGADO] [username: "+ peticion.getUsuario().getUsuario() +"]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        this.agregarProblema(nuevaPeticion);
    }
    
    public void construirPeticionNotificarClientes(PeticionUsuario peticion){
        agregarProblema(new PeticionUsuario(Peticiones.NOTIFICAR_CLIENTE, peticion.getHashcodeSC(), peticion.getUsuario()));
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
