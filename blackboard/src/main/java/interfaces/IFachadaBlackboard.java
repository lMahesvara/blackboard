/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import peticiones.AbstractPeticion;

/**
 *
 * @author Vastem
 */
public interface IFachadaBlackboard {
    public void addProblem(AbstractPeticion peticion);
    public void notificarCliente(AbstractPeticion peticion);
    public void notificarTodo(AbstractPeticion peticion);
}
