

package fabricas;

import conexion.ConexionBD;
import fuentes.AgregarUsuario;
import fuentes.LogTransacciones;
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
        }
        return null;
    }
    
}
