package utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Iterator;

import Interfaces.ISMSListeners;

/**
 * Created by sagarjoglekar on 10/11/2016.
 */

public class SMSParser extends BroadcastReceiver{

    private SmsManager smsManager;
    private SharedPreferences preferences;

    private static java.util.Vector<ISMSListeners> listeners = new java.util.Vector();

    public static SMSParser _singleTonParser = null;


    public SMSParser(){
        smsManager = SmsManager.getDefault();
        _singleTonParser = this;
    }

    public static SMSParser getStaticSMSParser(){
        if (_singleTonParser == null){
            _singleTonParser = new SMSParser();
            return _singleTonParser;
        } else {
            return _singleTonParser;
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.println(Log.DEBUG,"onReceive","Notifying Listeners");
        for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                Log.println(Log.DEBUG,"Not-REG",listeners.toString());
                Iterator it = listeners.iterator();
                while(it.hasNext()){
                    Log.println(Log.DEBUG,"NOTIFY","Notifying Listeners");
                    ISMSListeners a = (ISMSListeners)it.next();
                    a.onSMSReceive(messageBody);
                }
        }
    }

    public static String serialize(String payload){
        String serializedPayload = "";
        for (int i = 0; i < payload.length(); i++){
            char c = payload.charAt(i);
            char encodedChar  = (char) (c + 16);
            serializedPayload = serializedPayload + encodedChar;
        }
        return serializedPayload;
    }

    public static String deSerialize(String message){
        String payload = "";
        for (int i = 0; i < message.length(); i++){
            char c = message.charAt(i);
            char decodedChar  = (char) (c - 16);
            payload = payload + decodedChar;
        }
        return payload;
    }

    public void sendMessage(String destination , String message){
        String encoded = this.serialize(message);
        this.smsManager.sendTextMessage(destination, null, encoded , null, null);
    }

    public String sanitizeNumber(String number){
        String padded = new String();
        if (number.length() < 14){
            padded = String.format("%-14s", number);
        }
        return padded;
    }

    public String sanitizeName( String name ){
        String padded = new String();
        if (name.length() < 20){
            padded = String.format("%-20s", name);
        }
        return padded;
    }

    public void registerSMSListener(ISMSListeners listener){
        Log.println(Log.DEBUG, "Registry" , "Registered Listener");
        this.listeners.addElement( listener );
    }
}
