package controller.system.security.securitycontroller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import Interfaces.ISMSListeners;
import utils.SMSParser;

public class pairSS extends AppCompatActivity implements ISMSListeners {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private SMSParser _parser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _parser = SMSParser.getStaticSMSParser();

        setContentView(R.layout.activity_pair_ss);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        _parser.registerSMSListener(this);
    }

    private String buildMessage(View view){
        EditText siteName = (EditText)findViewById(R.id.Name);
        EditText SSNumber = (EditText)findViewById(R.id.SSNumber);
        EditText pass = (EditText)findViewById(R.id.Password);
        EditText serial = (EditText)findViewById(R.id.serial);
        EditText owner2 = (EditText)findViewById(R.id.owner2);
        EditText owner3 = (EditText)findViewById(R.id.owner3);
        EditText emp1 = (EditText)findViewById(R.id.emp1);
        EditText emp2 = (EditText)findViewById(R.id.emp2);
        EditText emp3 = (EditText)findViewById(R.id.emp3);
        EditText emp4 = (EditText)findViewById(R.id.emp4);
        EditText emp5 = (EditText)findViewById(R.id.emp5);

        String pairMessage = "R";
        if ( ( SSNumber.getText().toString().trim().equals("")) )
        {
            Toast.makeText(getApplicationContext(), "SSNumber name is empty", Toast.LENGTH_LONG).show();
            return null;
        }

        if ( ( pass.getText().toString().length()) < 4)
        {
            Toast.makeText(getApplicationContext(), "Password has to be 4 digit", Toast.LENGTH_LONG).show();
            return null;
        }

        if ( ( serial.getText().toString().trim().equals("")) )
        {
            Toast.makeText(getApplicationContext(), "You need to have a serial number for this site ", Toast.LENGTH_LONG).show();
            return null;
        }
        if ( ( siteName.getText().toString().trim().equals("")) )
        {
            Toast.makeText(getApplicationContext(), "You need to have a Name for the site ", Toast.LENGTH_LONG).show();
            return null;
        }

        TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        String finalMessage = pairMessage + serial.getText().toString() + this._parser.sanitizeName(siteName.getText().toString()) + this._parser.sanitizeNumber(mPhoneNumber)
                            + this._parser.sanitizeNumber(owner2.getText().toString()) + this._parser.sanitizeNumber(owner3.getText().toString())
                            + this._parser.sanitizeNumber(emp1.getText().toString()) + this._parser.sanitizeNumber(emp2.getText().toString())
                            + this._parser.sanitizeNumber(emp3.getText().toString()) + this._parser.sanitizeNumber(emp4.getText().toString())
                            + this._parser.sanitizeNumber(emp5.getText().toString()) + pass.getText().toString();

        return finalMessage;

    }
    public void executePair(View view) {

        String payload = this.buildMessage(view);
        if (payload!=null){
            EditText SSNumber = (EditText)findViewById(R.id.SSNumber);
            this._parser.sendMessage(SSNumber.getText().toString() , payload);
            //this._parser.sendMessage("07506225616" , payload);
        }
        Toast.makeText(pairSS.this, "Pairing message sent ",
                Toast.LENGTH_LONG).show();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("pairSS Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onSMSReceive(String message) {
        String decoded = _parser.deSerialize(message);
        Log.println(Log.DEBUG,"GOT MESSAGE",decoded);
    }
}
