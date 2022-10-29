

package fabricas;

import conexion.ConexionBD;
import fuentes.AgregarUsuario;
import fuentes.LogTransacciones;
import interfaces.AbstractFuente;
import interfaces.IConexionBD;
import interfaces.IFuentesFactory;

/**
 *
 * @author robertorw
 */
public class FuentesFactory implements IFuentesFactory{
    private static final IConexionBD conexionBD = new ConexionBD();
    
    @Override
    public AbstractFuente crearAgregarUsuario() {
        return new AgregarUsuario(conexionBD);
    }

    @Override
    public AbstractFuente crearLogTransacciones() {
        return new LogTransacciones(conexionBD);
    }
    
}
