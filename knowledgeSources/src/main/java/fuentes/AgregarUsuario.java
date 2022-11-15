package fuentes;

import blackboard.Blackboard;
import entidades.Usuario;
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
            
            construirPeticionLog(usuario);
            construirPeticionNotificarClientes(usuario);
            
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
        String mensaje = "[USUARIO AGREGADO] [username: "+ u.getUsuario() +"]";
        AbstractPeticion peticion = new PeticionLog(LOGGEAR_INFO, mensaje);
        this.agregarProblema(peticion);
    }
    
    public void construirPeticionNotificarClientes(Usuario usuario){
        agregarProblema(new PeticionUsuario(NOTIFICAR_TODOS, usuario));
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
