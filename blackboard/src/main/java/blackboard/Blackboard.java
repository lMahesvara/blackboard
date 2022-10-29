package blackboard;

import control.Control;
import entidades.BlackBoardObject;
import java.util.LinkedList;
import java.util.List;

public class Blackboard {
    private Control control;
    private static Blackboard instance;
    private List<BlackBoardObject> peticiones;
    
    private Blackboard() {
        this.peticiones = new LinkedList<>();
        this.control = new Control();
    }
    
    public static Blackboard getInstance(){
        if(instance == null)
            instance = new Blackboard();
        return instance;
    }
    
    public void addProblem(BlackBoardObject bbo){
        peticiones.add(bbo);
        notifyController(bbo);
        peticiones.remove(bbo);
    }
    
    private void notifyController(BlackBoardObject bbo){
        control.update(bbo);
    }
    
}
