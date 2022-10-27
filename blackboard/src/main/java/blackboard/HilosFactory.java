
package blackboard;

import entidades.BlackBoardObject;
import utils.ConvertirPeticion;

public class HilosFactory {
    
    public static HiloSocket crearHiloSocket(String json){
        BlackBoardObject bbo = ConvertirPeticion.BBOConverter(json);
        return new HiloSocket(bbo);
    }
}
