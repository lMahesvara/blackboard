
package blackboard;

import helpers.ConvertirPeticion;
import peticiones.AbstractPeticion;

public class HilosFactory {
    
    public static HiloSocket crearHiloSocket(String json, Integer hashcodeSC){
        AbstractPeticion peticion = ConvertirPeticion.PetitionConverter(json, hashcodeSC);
        return new HiloSocket(peticion);
    }
}
