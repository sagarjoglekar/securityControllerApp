package utils;

import android.telephony.SmsManager;

/**
 * Created by sagarjoglekar on 10/11/2016.
 */

public class SMSParser {
    private SmsManager smsManager;
    public SMSParser(){
        smsManager = SmsManager.getDefault();
    }
    private String serialize(String payload){
        String serializedPayload = "";
        for (int i = 0; i < payload.length(); i++){
            char c = payload.charAt(i);
            char encodedChar  = (char) (c + 16);
            serializedPayload = serializedPayload + encodedChar;
        }
        return serializedPayload;
    }

    private String deSerialize(String message){
        String payload = "";
        for (int i = 0; i < message.length(); i++){
            char c = message.charAt(i);
            char decodedChar  = (char) (c + 16);
            payload = payload + decodedChar;
        }
        return payload;
    }

    public void sendMessage(String destination , String message){
        String encoded = this.serialize(message);
        this.smsManager.sendTextMessage(destination, null, encoded , null, null);
    }

    public String sanitizeNumber(String number){
        String padded = new String("");
        if (number.length() < 14){
            padded = String.format("%-14s", number);
        }
        return padded;
    }

    public String sanitizeName( String name ){
        String padded = new String("");
        if (name.length() < 20){
            padded = String.format("%-20s", name);
        }
        return padded;
    }
}
