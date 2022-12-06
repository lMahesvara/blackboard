
package prueba;

import entidades.Comentario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;


public class Prueba {

    public static void main(String[] args) {
        //Obtiene acceso alemFactory a partir de la persistence unit utilizada
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("persistencia"); 
        // Creamos una em(bd) para realizar operaciones con la bd
        EntityManager em = emFactory.createEntityManager();
        
        
        
        em.getTransaction().begin();
        
//        Comentario c = em.find(Comentario.class, 14L);
//        em.remove(c);
//        System.out.println("ea");

        Query q =em.createNativeQuery("DELETE FROM comentarios "
                                            + "WHERE comentarios.id_comentario =" + 14L, Comentario.class);
            q.executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
    
}
