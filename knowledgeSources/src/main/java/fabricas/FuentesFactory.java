package fabricas;

import conexion.ConexionBD;
import fuentes.AgregarPublicacion;
import fuentes.AgregarUsuario;
import fuentes.ConsultarPublicaciones;
import fuentes.IniciarSesion;
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
        }
        return null;
    }

}
