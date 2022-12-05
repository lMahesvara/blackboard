 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuentes;

import blackboard.FachadaBlackboard;
import interfaces.AbstractFuente;
import interfaces.IFachadaBlackboard;
import peticiones.AbstractPeticion;

/**
 * 
 * @author erick
 */
public class NotificarCliente extends AbstractFuente{
    /**
     * Se encarga de procesar la petición
     * @param peticion Petición a procesar
     */
    @Override
    public void procesar(AbstractPeticion peticion) {
        IFachadaBlackboard fachadaBlack = new FachadaBlackboard();
        fachadaBlack.notificarCliente(peticion);
    }  
}