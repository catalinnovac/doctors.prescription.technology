package doctors.prescription.technology.code.webview;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by novac on 07-Aug-14.
 */
public class WebViewInterface {
    public final String UPDATECARTACTION = "1";
    private Context _context;

    public WebViewInterface(Context context) {
        this._context = context;
    }

    @JavascriptInterface
    public void sendMessage(final String action, String extrajsonArray) {
        Intent intent = new Intent(action);
        if (extrajsonArray != null) {
            try {
                JSONArray array = new JSONArray(extrajsonArray);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    intent.putExtra(object.getString("key"), object.getString("value"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        _context.sendBroadcast(intent);
    }
}
