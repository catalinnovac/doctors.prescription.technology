package doctors.prescription.technology.code.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import org.apache.cordova.CordovaWebView;

/**
 * Created by novac on 25-Sep-14.
 */
public class CustomCordovaWebView extends CordovaWebView {
    public CustomCordovaWebView(Context context) {
        super(context);
    }

    public CustomCordovaWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomCordovaWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int temp_ScrollY = getScrollY();
            scrollTo(getScrollX(), getScrollY() + 1);
            scrollTo(getScrollX(), temp_ScrollY);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void handleDestroy() {
        super.handleDestroy();
    }

    @Override
    public boolean backHistory() {
        Log.v(TAG, this.getUrl());
        if (this.getUrl().equals("file:///android_asset/www/conectare.html")) {
            return false;
        } else
            return super.backHistory();
    }

}
