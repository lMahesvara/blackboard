
package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.BlackBoardObject;

public class ConvertirPeticion {
    
    public static BlackBoardObject BBOConverter(String json){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, BlackBoardObject.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
