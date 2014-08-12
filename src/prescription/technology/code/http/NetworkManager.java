package prescription.technology.code.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by novac on 07-Aug-14.
 */
public class NetworkManager {
    private static boolean haveConnectedWifi = false;
    private static boolean haveConnectedMobile = false;

    public static NetworkSource getSource(Context context) {
        isNetworkConnection(context);
        if (haveConnectedWifi)
            return NetworkSource.Wifi;
        else if (haveConnectedMobile)
            return NetworkSource.Mobile;

        return null;
    }

    public static boolean isNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                } else
                    haveConnectedWifi = false;
            }

            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                } else
                    haveConnectedMobile = false;
            }
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    public enum NetworkSource {
        Wifi, Mobile
    }
}

