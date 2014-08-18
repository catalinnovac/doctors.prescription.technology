package doctors.prescription.technology.code.webview;

import org.apache.cordova.api.CordovaPlugin;

/**
 * Created by novac on 18-Aug-14.
 */
public class DoctorsPlugin extends CordovaPlugin {
    @Override
    public boolean execute(java.lang.String action, org.json.JSONArray args, org.apache.cordova.api.CallbackContext callbackContext) throws org.json.JSONException {
        if (action.equals("LOGIN_COMPLETED")) {
            String message = args.getString(0);
            webView.sendJavascript("alert('cata is here')");
            callbackContext.success();
            return true;
        }
        return false;
    }
}
