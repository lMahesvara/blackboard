package blackboard;

import control.Control;
import entidades.BlackBoardObject;
import java.util.LinkedList;
import java.util.List;
import peticiones.AbstractPeticion;

public class Blackboard {
    private Control control;
    private static Blackboard instance;
    private List<AbstractPeticion> peticiones;
    
    private Blackboard() {
        this.peticiones = new LinkedList<>();
        this.control = new Control();
    }
    
    public static Blackboard getInstance(){
        if(instance == null)
            instance = new Blackboard();
        return instance;
    }
    
    public void addProblem(AbstractPeticion peticion){
        peticiones.add(peticion);
        notifyController(peticion);
        peticiones.remove(peticion);
    }
    
    private void notifyController(AbstractPeticion peticion){
        control.update(peticion);
    }
    
}
