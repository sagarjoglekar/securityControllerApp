package utils;

/**
 * Created by sagarjoglekar on 10/11/2016.
 */

public class SMSParser {
    public SMSParser(){

    }
    public String serialize(String payload){
        String serializedPayload = "";
        for (int i = 0; i < payload.length(); i++){
            char c = payload.charAt(i);
            char encodedChar  = (char) (c + 16);
            serializedPayload = serializedPayload + encodedChar;
        }
        return serializedPayload;
    }

    public String deSerialize(String message){
        String payload = "";
        for (int i = 0; i < message.length(); i++){
            char c = message.charAt(i);
            char decodedChar  = (char) (c + 16);
            payload = payload + decodedChar;
        }
        return payload;
    }
}
