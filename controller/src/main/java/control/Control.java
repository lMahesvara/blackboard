
package control;

import blackboard.Server;
import interfaces.IFachadaBlackboard;
import interfaces.IManejadorKS;
import peticiones.AbstractPeticion;

public class Control {

    private IManejadorKS manejadorKS;
    private static IFachadaBlackboard fachadaBb;
    public Control() {
        this.manejadorKS = new ManejadorKS();
        this.fachadaBb = new FachadaBlackboard();
    }

    public static void main(String[] args){
        Server.getInstance().start();
        Control c = new Control();
        c.preguntarPeticion();
    }

    public void update(AbstractPeticion peticion){
        manejadorKS.ejecutar(peticion);
    }
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
