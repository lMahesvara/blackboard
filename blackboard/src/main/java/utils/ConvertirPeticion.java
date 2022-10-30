
package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.BlackBoardObject;

public class ConvertirPeticion {
    
    public static BlackBoardObject BBOConverter(String json){
        System.out.println("--------------------------------------");
        System.out.println(json);
        System.out.println("--------------------------------------");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BlackBoardObject bbo = objectMapper.readValue(json, BlackBoardObject.class);
            System.out.println(bbo);
            return bbo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
