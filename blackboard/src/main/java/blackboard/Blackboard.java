package blackboard;

import java.util.LinkedList;
import java.util.List;
import peticiones.AbstractPeticion;

public class Blackboard {
    private static Blackboard instance;
    private List<AbstractPeticion> peticiones;

    /*
    * Constructor
    */
    private Blackboard() {
        this.peticiones = new LinkedList<>();
    }

    /*
    * Obtiene la instancio de la clase
    */
    public static Blackboard getInstance(){
        if(instance == null)
            instance = new Blackboard();
        return instance;
    }

    /*
    * Agrega un problema a la lista
    */
    public void addProblem(AbstractPeticion peticion){
        System.out.println(peticiones);
        peticiones.add(peticion);
        System.out.println(peticiones);
    }

    /*
    * PRegunta si existen peticiones en el blackboard
    */
    public boolean existenPeticiones(){
        System.out.println(peticiones);
        return !peticiones.isEmpty();
    }

    /*
    * Obtiene la peticion
    */
    public AbstractPeticion getPeticion(){
        return peticiones.get(0);
    }

    /*
    * Remueve la peticion
    */
    public void removePeticion(){
        peticiones.remove(0);
    }
    
}
