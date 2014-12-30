package doctors.prescription.technology.code.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import org.apache.cordova.CordovaWebView;

/**
 * Created by novac on 25-Sep-14.
 */
public class CustomCordovaWebView extends CordovaWebView {
    public String CurrentPage = "file:///android_asset/www/index.html";
    GestureDetector gd;
    Context context;

    GestureDetector.SimpleOnGestureListener sogl = new GestureDetector.SimpleOnGestureListener() {
        public boolean onDown(MotionEvent event) {
            show_toast("swipe down");
            int temp_ScrollY = getScrollY();
            scrollTo(getScrollX(), getScrollY() + 1);
            scrollTo(getScrollX(), temp_ScrollY);
            return true;
        }

        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            if (event1.getRawX() > event2.getRawX()) {
                show_toast("swipe left");
            } else {
                show_toast("swipe right");
            }
            return true;
        }
    };

    public CustomCordovaWebView(Context context) {
        super(context);
        this.context = context;
        gd = new GestureDetector(context, sogl);
    }

    public CustomCordovaWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        gd = new GestureDetector(context, sogl);
    }

    public CustomCordovaWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        gd = new GestureDetector(context, sogl);
    }

    void show_toast(final String text) {
        Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return gd.onTouchEvent(event);
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
        return true;
        /*
        if (this.getUrl().equals("file:///android_asset/www/conectare.html")) {
            return false;
        } else
            return super.backHistory();
        */
    }

    public void loadCurrentPage() {
        this.loadUrl(CurrentPage + "#fromCache=true");
    }

}
