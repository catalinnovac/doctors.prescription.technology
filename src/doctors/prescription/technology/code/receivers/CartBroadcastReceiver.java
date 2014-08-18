package doctors.prescription.technology.code.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import doctors.prescription.technology.Index;
import org.apache.cordova.CordovaWebView;

/**
 * Created by novac on 07-Aug-14.
 */
public class CartBroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = CartBroadcastReceiver.class.getSimpleName();
    public CordovaWebView cartView;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "CART") {
            Index activity = (Index) context;
            Log.v(TAG, "send javascript");
            cartView.sendJavascript("console.log('cata is here')");
            activity.mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }
}
