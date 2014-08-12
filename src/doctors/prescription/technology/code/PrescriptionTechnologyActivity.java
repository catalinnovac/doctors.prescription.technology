package doctors.prescription.technology.code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import doctors.prescription.technology.R;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;
import org.apache.cordova.api.CordovaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrescriptionTechnologyActivity extends DroidGap {

    public final String TAG = "PRESCRIPTION TECHNOLOGY";
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appView = (CordovaWebView) findViewById(R.id.cordova_main_webview);
        super.init();
        mPlanetTitles = new String[]{"TEST 1", "TEST 2"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mPlanetTitles));
        appView.addJavascriptInterface(this, "prescription");
        appView.addJavascriptInterface(new Constants(), "constants");
    }

    @JavascriptInterface
    public void NavigateTo(String action) {
        Log.v(TAG, action);
        boolean valid = false;
        Constants constant = new Constants();
        if (action.equals(constant.CUSTOMER_ADDRESS_ACTIVITY))
            valid = true;
        if (action.equals(constant.CUSTOMER_PERSONAL_INFO_ACTIVITY))
            valid = true;
        if (action.equals(constant.ORDER_CONFIRMARTION_ACTIVITY))
            valid = true;
        if (action.equals(constant.QUESTIONS_ACTIVITY))
            valid = true;
        if (valid) {
            Intent i = new Intent(action);
            startActivity(i);
        } else
            try {
                throw new Exception("Invalid action!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {
        this.activityResultCallback = plugin;
    }

    /**
     * Launch an activity for which you would like a result when it finished. When this activity exits,
     * your onActivityResult() method is called.
     *
     * @param command     The command object
     * @param intent      The intent to start
     * @param requestCode The request code that is passed to callback to identify the activity
     */
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        this.activityResultCallback = command;
        this.activityResultKeepRunning = this.keepRunning;


// If multitasking turned on, then disable it for activities that return results
        if (command != null) {
            this.keepRunning = false;
        }


// Start activity
        super.startActivityForResult(intent, requestCode);


    }


    @Override
/**
 * Called when an activity you launched exits, giving you the requestCode you started it with,
 * the resultCode it returned, and any additional data from it.
 *
 * @param requestCode       The request code originally supplied to startActivityForResult(),
 *                          allowing you to identify who this result came from.
 * @param resultCode        The integer result code returned by the child activity through its setResult().
 * @param data              An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
 */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        CordovaPlugin callback = this.activityResultCallback;
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public ExecutorService getThreadPool() {
        return Executors.newSingleThreadExecutor();
    }
}
