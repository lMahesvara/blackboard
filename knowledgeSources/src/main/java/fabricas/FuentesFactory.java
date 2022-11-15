

package fabricas;

import conexion.ConexionBD;
import fuentes.AgregarUsuario;
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
public class FuentesFactory implements IFuentesFactory{
    private static final IConexionBD conexionBD = new ConexionBD();
    
    @Override
    public AbstractFuente crearFuente(AbstractPeticion peticion) {
        if(peticion.getPeticion().equals(REGISTRAR_USUARIO)){
            return new AgregarUsuario(conexionBD);
        }else if(peticion.getPeticion().equals(LOGGEAR_INFO)){
            return new LoguearTransaccion();
        }else if(peticion.getPeticion().equals(NOTIFICAR_TODOS)){
            return new NotificarClientes();
        }else if(peticion.getPeticion().equals(NOTIFICAR_CLIENTE)){
            return new NotificarCliente();
        }
        return null;
    }
    
}
