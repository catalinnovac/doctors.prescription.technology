package prescription.technology.code.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import prescription.technology.Index;
import prescription.technology.code.navigation.drawer.CustomCordovaWebView;

/**
 * Created by novac on 07-Aug-14.
 */
public class CartBroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = CartBroadcastReceiver.class.getSimpleName();
    public CustomCordovaWebView cartView;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "CART") {
            Index activity = (Index) context;
            cartView = (CustomCordovaWebView) activity.NavigationDrawerViews.get("CART");
            if (cartView != null) {
                Log.v(TAG, "send javascript");
                //cartView.sendJavascript("showmessage(\"cata is here\")");
                activity.mDrawerLayout.openDrawer(Gravity.LEFT);
            } else
                Log.v(TAG, "cartView is not set");
        }
    }
}
