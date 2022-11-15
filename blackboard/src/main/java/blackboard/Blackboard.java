package blackboard;

import java.util.LinkedList;
import java.util.List;
import peticiones.AbstractPeticion;

public class Blackboard {
    private static Blackboard instance;
    private List<AbstractPeticion> peticiones;

    private Blackboard() {
        this.peticiones = new LinkedList<>();
    }

    public static Blackboard getInstance(){
        if(instance == null)
            instance = new Blackboard();
        return instance;
    }

    public void addProblem(AbstractPeticion peticion){
        System.out.println(peticiones);
        peticiones.add(peticion);
        System.out.println(peticiones);
    }

    public boolean existenPeticiones(){
        System.out.println(peticiones);
        return !peticiones.isEmpty();
    }

    public AbstractPeticion getPeticion(){
        return peticiones.get(0);
    }

    public void removePeticion(){
        peticiones.remove(0);
    }
    
}
