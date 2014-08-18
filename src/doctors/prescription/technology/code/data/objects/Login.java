package doctors.prescription.technology.code.data.objects;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import doctors.prescription.technology.code.Constants;
import doctors.prescription.technology.code.data.base.DataObjectBase;
import doctors.prescription.technology.code.http.Request;
import doctors.prescription.technology.code.http.RequestRunnable;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by novac on 14-Aug-14.
 */
public class Login extends DataObjectBase {

    public Login(Context context) {
        super(context);
    }

    public void auth(String loginID, String password, String user) {
        //loginID = "marius";
        //password = "1234";
        //user = "1";
        Request request = new Request(_context);
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("loginID", loginID));
        data.add(new BasicNameValuePair("password", password));
        data.add(new BasicNameValuePair("user", user));
        request.setData(data);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        request.PostExecuteHandler = new RequestRunnable() {
            @Override
            public void run() {
                String response = getArgs().get("response").toString();
                boolean _success = false;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String token = jsonObject.getString("token");
                    if (jsonObject.getInt("status") == 1) {
                        Log.v(TAG, token);
                        //salveaza token-ul pentru request-urile viitoare
                        editor.putString("TOKEN", token);
                        editor.apply();
                        _success = true;
                    } else {
                        _success = false;
                        editor.putString("ERROR", token);
                        editor.apply();
                    }
                } catch (JSONException e) {
                    _success = false;
                    e.printStackTrace();
                }
                Log.v(TAG, response);
                Intent loginCompletedItent = new Intent("LOGIN_COMPLETED");
                if (_success) {
                    loginCompletedItent.putExtra("TOKEN", sharedPreferences.getString("TOKEN", null));
                } else {
                    loginCompletedItent.putExtra("ERROR", sharedPreferences.getString("ERROR", null));
                }
                _context.sendBroadcast(loginCompletedItent);
            }
        };
        request.execute(Constants.LOGIN_URI);
    }
}

