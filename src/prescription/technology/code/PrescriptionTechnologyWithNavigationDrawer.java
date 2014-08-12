package prescription.technology.code;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import prescription.technology.R;
import prescription.technology.code.navigation.drawer.Adapter;
import prescription.technology.code.navigation.drawer.CustomCordovaWebView;
import prescription.technology.code.navigation.drawer.DrawerToggle;
import prescription.technology.code.navigation.drawer.Item;
import prescription.technology.code.webview.WebViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novac on 24-Jul-14.
 */
public abstract class PrescriptionTechnologyWithNavigationDrawer extends Activity implements CordovaInterface {

    public static CustomCordovaWebView __cart;
    public final String TAG = "PRESCRIPTION TECHNOLOGY";
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    //<editor-fold desc="Fields"
    public HashMap<String, View> NavigationDrawerViews = new HashMap<String, View>();
    public DrawerLayout mDrawerLayout;
    protected CordovaWebView appView;
    boolean activityResultKeepRunning;
    boolean keepRunning;
    private ListView mDrawerList;
    private CordovaPlugin activityResultCallback;
    private ActionBarDrawerToggle mDrawerToggle;
    private HashMap<String, BroadcastReceiver> broadcastReceiverHashMap;
    //</editor-fold>

    //<editor-fold desc="Override">
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getActionBar().hide();
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e4f2f2")));
        /*
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setCustomView(R.layout.action_bar);
        LayoutInflater vi = LayoutInflater.from(this);
        CordovaWebView actionbar = (CordovaWebView) vi.inflate(R.layout.action_bar, null);
        actionbar.loadUrl("file:///android_asset/www/accountinfo.html");
        */

        setContentView(R.layout.activity_main);
        appView = (CordovaWebView) findViewById(R.id.cordova_main_webview);
        Config.init(this);
        appView.getSettings().setJavaScriptEnabled(true);
        WebViewInterface webViewInterface = new WebViewInterface(this);
        appView.addJavascriptInterface(webViewInterface, "prescription");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new DrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.Id = "shopping-cart";
        item.CONTENT = "file:///android_asset/www/shoppingcart.html";
        items.add(item);
        mDrawerList.setAdapter(new Adapter(this,
                R.layout.left_drawer_item, items));

        broadcastReceiverHashMap = GetBroadcastsMap();
        for (String key : broadcastReceiverHashMap.keySet()) {
            registerReceiver(broadcastReceiverHashMap.get(key), new IntentFilter(key));
        }
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (String key : broadcastReceiverHashMap.keySet()) {
            unregisterReceiver(broadcastReceiverHashMap.get(key));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startActivityForResult(CordovaPlugin cordovaPlugin, Intent intent, int requestCode) {
        this.activityResultCallback = cordovaPlugin;
        this.activityResultKeepRunning = this.keepRunning;
        if (cordovaPlugin != null) {
            this.keepRunning = false;
        }
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin cordovaPlugin) {
        this.activityResultCallback = cordovaPlugin;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        CordovaPlugin callback = this.activityResultCallback;
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Object onMessage(String s, Object o) {
        if (s.equalsIgnoreCase("exit")) {
            super.finish();
        }
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void onDestroy() {
        if (this.appView != null) {
            this.appView.handleDestroy();
        }
        super.onDestroy();
    }
    //</editor-fold>

    //<editor-fold desc="Abstract">
    protected abstract HashMap<String, BroadcastReceiver> GetBroadcastsMap();

    //</editor-fold>

    //<editor-fold desc="Public">
    public void AddNavigationDrawerView(String key, View v) {
        NavigationDrawerViews.put(key, v);
    }
    //</editor-fold>

}
