
package control;

import blackboard.Server;
import interfaces.IManejadorKS;
import peticiones.AbstractPeticion;
import interfaces.IFachadaBlackboardControl;

public class Control {

    private IManejadorKS manejadorKS;
    private static IFachadaBlackboardControl fachadaBb;
    public Control() {
        this.manejadorKS = new ManejadorKS();
        this.fachadaBb = new FachadaBlackboardControl();
    }

    /**
     * 
     * @param args 
     */
    public static void main(String[] args){
        Server.getInstance().start();
        Control c = new Control();
        c.preguntarPeticion();
    }

    /**
     * Ejecuta la peticion del blackboard
     */
    public void update(AbstractPeticion peticion){
        manejadorKS.ejecutar(peticion);
    }
    
    /**
     * Pregunta si existe alguna peticion
     */
    public void preguntarPeticion(){
        while(true){
            try {
                Thread.sleep(1000);
                if(fachadaBb.existenPeticiones()){
                    System.out.println("Entro");
                    AbstractPeticion peticion = fachadaBb.getPeticion();
                    fachadaBb.removePeticion();
                    update(peticion);
                }else System.out.println("No entro");
            } catch (InterruptedException e) {
                System.out.println("GG el hilo");
            }
        }
    }
    
}
