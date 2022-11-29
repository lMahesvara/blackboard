/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.Blackboard;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import entidades.Comentario;
import entidades.Notificacion;
import entidades.Usuario;
import helpers.Peticiones;
import static helpers.Peticiones.LOGGEAR_INFO;
import static helpers.Peticiones.NOTIFICACION_CORREO;
import static helpers.Peticiones.NOTIFICACION_SMS;
import static helpers.Peticiones.NOTIFICACION_TODOS;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import peticiones.AbstractPeticion;
import peticiones.PeticionComentario;
import peticiones.PeticionLog;
import peticiones.PeticionNotificacion;

/**
 *
 * @author Vastem
 */
public class CrearNotificacion extends AbstractFuente{
    private final IConexionBD conexionBD;
    private final EntityManager em;
    public static final String ACCOUNT_SID = "ACa58a0e7d3fc7d1accba783a4622adaee";
    public static final String AUTH_TOKEN = "24ff0c4dcccd24f3eea0ea678596b263";

    public CrearNotificacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.em = this.conexionBD.crearConexion();
    }
    
    public void procesar(AbstractPeticion peticion) {
        System.out.println("entro al procesar");
        PeticionNotificacion pN = (PeticionNotificacion) peticion;
        Notificacion notificacion = pN.getNotificacion();
        try {
            String tipo = null;
            Usuario destinatario = existeUsuario(notificacion.getDestinatario().getUsuario());
            
            if(destinatario == null){
                System.out.println("no existe");
                return;
            }
            
            notificacion.setDestinatario(destinatario);
            
            em.getTransaction().begin();
            em.persist(notificacion);
            em.getTransaction().commit();
            
            System.out.println(pN.getPeticion());
            
            if(pN.getPeticion().equals(NOTIFICACION_CORREO)){
                System.out.println("entró a enviar correo");
                tipo = NOTIFICACION_CORREO;
                notificarCorreo(notificacion.getRemitente().getUsuario(),destinatario.getCorreo(),notificacion.getMensaje());
            }else if(pN.getPeticion().equals(NOTIFICACION_SMS)){
                tipo = NOTIFICACION_SMS;
                notificarSMS(destinatario.getNumeroCelular(),notificacion.getMensaje(), notificacion.getRemitente().getUsuario());
            }else if(pN.getPeticion().equals(NOTIFICACION_TODOS)){
                tipo = NOTIFICACION_TODOS;
                notificarCorreo(notificacion.getRemitente().getUsuario(),destinatario.getCorreo(),notificacion.getMensaje());
                notificarSMS(destinatario.getNumeroCelular(),notificacion.getMensaje(), notificacion.getRemitente().getUsuario());
            }
            
            construirPeticionLog(pN);
            construirPeticionNotificarCliente(pN, tipo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarProblema(AbstractPeticion peticion) {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(peticion);
    }

    public void construirPeticionLog(PeticionNotificacion peticion ) {
        String mensaje = "[NOTIFICACION AGREGADA] [ID Usuario: " + peticion.getNotificacion().getRemitente()+ "]";
        AbstractPeticion nuevaPeticion = new PeticionLog(LOGGEAR_INFO, peticion.getHashcodeSC(), mensaje);
        agregarProblema(nuevaPeticion);
    }

    public void construirPeticionNotificarCliente(PeticionNotificacion peticion, String tipo) {
        agregarProblema(new PeticionNotificacion(Peticiones.NOTIFICAR_CLIENTE, tipo, peticion.getHashcodeSC(), null, peticion.getNotificacion()));
    }
    
    public void notificarSMS(String destinatario, String mensaje, String remitente){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+52"+destinatario),
                new com.twilio.type.PhoneNumber("+19144772246"),
                "Nueva notificación de: "+ remitente+ ""
                        + " '" +mensaje+"'")
            .create();

        System.out.println(message.getSid());
    }
    
    public void notificarCorreo(String remitente ,String correoDestinatario, String mensaje){
        String from= "xlfaceboot"; 
        String pass = "abzdebuipdrbugnq";
        String subject = "Nueva notificacion de: " + remitente;
        
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(correoDestinatario);

            message.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);
            

            message.setSubject(subject);
            message.setText(mensaje);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    
    public Usuario existeUsuario(String usuario) {

        em.getTransaction().begin();

        Query query = em.createQuery(
                "SELECT u "
                + "FROM Usuario u "
                + "WHERE u.usuario = :usuario");

        query.setParameter("usuario", usuario);

        List<Usuario> usuarios = query.getResultList();

        em.getTransaction().commit();

        if (usuarios.isEmpty()) {
            return null;
        } 

        return usuarios.get(0);
    }
}
