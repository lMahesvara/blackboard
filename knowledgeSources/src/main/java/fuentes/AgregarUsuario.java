
package fuentes;

import entidades.BlackBoardObject;
import entidades.Usuario;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class AgregarUsuario extends AbstractFuente{

    private final IConexionBD conexionBD;
    private final EntityManager em;
    
    public AgregarUsuario(IConexionBD conexionBD){
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    } 
    
    @Override
    public void procesar(BlackBoardObject bbo){
        
        try{      
            Usuario usuario = bbo.getUsuario(); 
            if(existeUsuario(usuario.getUsuario())) return;
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            em.close();
        
        }catch(Exception ex){
            Logger.getLogger(AgregarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void agregarProblema(){
        
    }
    
    public boolean existeUsuario(String usuario){
        
        em.getTransaction().begin();
        
        Query query = em.createQuery(
                "SELECT e "
                + "FROM Usuario u "
                + "WHERE u.usuario = :usuario");
        
        query.setParameter("usuario", usuario);
        
        List<Usuario> usuarios = query.getResultList();
        
        em.getTransaction().commit();
        em.close();
        
        if(usuarios.isEmpty()) return false;
        return true;
    }
        
}
