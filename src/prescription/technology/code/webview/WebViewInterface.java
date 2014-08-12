package prescription.technology.code.webview;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

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
    public void sendMessage(final String action) {
        Intent i = new Intent(action);
        _context.sendBroadcast(i);
    }
}
