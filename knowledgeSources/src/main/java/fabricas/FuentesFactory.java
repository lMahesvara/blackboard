package fabricas;

import conexion.ConexionBD;
import fuentes.AgregarComentario;
import fuentes.AgregarPublicacion;
import fuentes.AgregarUsuario;
import fuentes.ConsultarNotificaciones;
import fuentes.ConsultarPublicaciones;
import fuentes.CrearNotificacion;
import fuentes.EditarUsuario;
import fuentes.IniciarSesion;
import fuentes.IniciarSesionFb;
import fuentes.LoguearTransaccion;
import fuentes.NotificarCliente;
import fuentes.NotificarClientes;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import interfaces.IFuentesFactory;
import peticiones.AbstractPeticion;
import static helpers.Peticiones.*;

/**
 *
 * @author robertorw
 */
public class FuentesFactory implements IFuentesFactory {

    private static final IConexionBD conexionBD = new ConexionBD();

    @Override
    public AbstractFuente crearFuente(AbstractPeticion peticion) {
        if (peticion.getPeticion().equals(REGISTRAR_USUARIO)) {
            return new AgregarUsuario(conexionBD);
        } else if (peticion.getPeticion().equals(LOGGEAR_INFO)) {
            return new LoguearTransaccion();
        } else if (peticion.getPeticion().equals(NOTIFICAR_TODOS)) {
            return new NotificarClientes();
        } else if (peticion.getPeticion().equals(NOTIFICAR_CLIENTE)) {
            return new NotificarCliente();
        } else if (peticion.getPeticion().equals(INICIAR_SESION)) {
            return new IniciarSesion(conexionBD);
        } else if (peticion.getPeticion().equals(REGISTRAR_PUBLICACION)) {
            return new AgregarPublicacion(conexionBD);
        } else if (peticion.getPeticion().equals(CONSULTAR_PUBLICACIONES)) {
            return new ConsultarPublicaciones(conexionBD);
        }else if (peticion.getPeticion().equals(AGREGAR_COMENTARIO)) {
            return new AgregarComentario(conexionBD);
        }else if (peticion.getPeticion().equals(ACTUALIZAR_USUARIO)) {
            return new EditarUsuario(conexionBD);
        }else if (peticion.getPeticion().equals(INICIAR_SESION_FB)){
            return new IniciarSesionFb(conexionBD);
        }else if (peticion.getPeticion().equals(NOTIFICACION_CORREO)  || peticion.getPeticion().equals(NOTIFICACION_SMS) || peticion.getPeticion().equals(NOTIFICACION_TODOS)){
            return new CrearNotificacion(conexionBD);
        }else if (peticion.getPeticion().equals(CONSULTAR_NOTIFICACIONES)){
            return new ConsultarNotificaciones(conexionBD);
        }
        return null;
    }

}
