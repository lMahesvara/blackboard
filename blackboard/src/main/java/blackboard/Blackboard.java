package blackboard;

import entidades.BlackBoardObject;
import java.util.LinkedList;
import java.util.List;

public class Blackboard {
    //private Controller control;
    private static Blackboard instance;
    private List<BlackBoardObject> peticiones;
    
    private Blackboard() {
        peticiones = new LinkedList<>();
    }
    
    public static Blackboard getInstance(){
        if(instance == null)
            instance = new Blackboard();
        return instance;
    }
    
    public void addProblem(BlackBoardObject bbo){
        peticiones.add(bbo);
        notifyController();
    }
    
    private void notifyController(){
        //control.update();
    }
    
}
