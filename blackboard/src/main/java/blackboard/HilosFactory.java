
package blackboard;

import helpers.ConvertirPeticion;
import peticiones.AbstractPeticion;

public class HilosFactory {
    
    public static HiloSocket crearHiloSocket(String json){
        AbstractPeticion peticion = ConvertirPeticion.PetitionConverter(json);
        return new HiloSocket(peticion);
    }
}
