package doctors.prescription.technology.code;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import doctors.prescription.technology.R;
import doctors.prescription.technology.code.navigation.drawer.Adapter;
import doctors.prescription.technology.code.navigation.drawer.DrawerToggle;
import doctors.prescription.technology.code.navigation.drawer.Item;
import doctors.prescription.technology.code.webview.WebViewInterface;
import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novac on 24-Jul-14.
 */
public abstract class PrescriptionTechnologyWithNavigationDrawer extends Activity implements CordovaInterface {
    private final String TAG = "PRESCRIPTION TECHNOLOGY";
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    //<editor-fold desc="Fields"
    public HashMap<String, View> NavigationDrawerViews = new HashMap<String, View>();
    public DrawerLayout mDrawerLayout;
    public CordovaWebView appView;
    boolean activityResultKeepRunning;
    boolean keepRunning;
    List<Item> items;
    private ListView mDrawerList;
    private CordovaPlugin activityResultCallback;
    private ActionBarDrawerToggle mDrawerToggle;
    private ConcurrentHashMap<String, BroadcastReceiver> broadcastReceiverHashMap;
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
        actionbar.loadUrl("file:///android_asset/www/messages.html");
        */

        setContentView(R.layout.activity_main);
        appView = (CordovaWebView) findViewById(R.id.cordova_main_webview);
        Config.init(this);
        appView.getSettings().setJavaScriptEnabled(true);
        //adauga interfata dintre code behind si cod client
        WebViewInterface webViewInterface = new WebViewInterface(this, appView);
        appView.addJavascriptInterface(webViewInterface, "doctors");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new DrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: //pending
                        break;
                    case 1://history
                        break;
                    case 2://declined
                        break;
                    case 3://query
                        break;
                    case 4: //invoices
                        break;
                    case 5: //messages
                        break;
                }
            }
        });
        PopulateLeftMenu();//populeaza listview din navigation drawer
        mDrawerList.setAdapter(new Adapter(this,
                R.layout.left_drawer_item, items));
        synchronized (this) {
            //populeaza harta de receiver-e din activity-ul curent
            broadcastReceiverHashMap = GetBroadcastsMap();
            for (String key : broadcastReceiverHashMap.keySet()) {
                //inregistreaza fiecare receiver
                registerReceiver(broadcastReceiverHashMap.get(key), new IntentFilter(key));
            }
        }
        //deschide meniu lateral(stanga)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
        synchronized (this) {
            for (String key : broadcastReceiverHashMap.keySet()) {
                //unregister fiecare receiver
                unregisterReceiver(broadcastReceiverHashMap.get(key));
                broadcastReceiverHashMap.remove(key);
            }
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
    protected abstract ConcurrentHashMap<String, BroadcastReceiver> GetBroadcastsMap();

    //</editor-fold>

    //<editor-fold desc="Private">
    private void PopulateLeftMenu() {
        items = new ArrayList<Item>();
        Item pendingItem = new Item();
        pendingItem.counter = 1;
        pendingItem.iconRes = R.drawable.ic_drawer;
        pendingItem.title = R.string.pending_menu_title;
        pendingItem.isHeader = false;
        items.add(pendingItem);
        Item historyItem = new Item();
        historyItem.counter = 6;
        historyItem.iconRes = R.drawable.ic_drawer;
        historyItem.title = R.string.history_menu_title;
        historyItem.isHeader = false;
        items.add(historyItem);
        Item declinedItem = new Item();
        declinedItem.counter = 18;
        declinedItem.iconRes = R.drawable.ic_drawer;
        declinedItem.title = R.string.declined_menu_title;
        declinedItem.isHeader = false;
        items.add(declinedItem);
        Item queryItem = new Item();
        queryItem.iconRes = R.drawable.ic_drawer;
        queryItem.title = R.string.query_menu_title;
        queryItem.isHeader = false;
        items.add(queryItem);
        Item invoicesItem = new Item();
        invoicesItem.iconRes = R.drawable.ic_drawer;
        invoicesItem.title = R.string.invoices_menu_title;
        invoicesItem.isHeader = false;
        items.add(invoicesItem);
        Item messagesItem = new Item();
        messagesItem.iconRes = R.drawable.ic_drawer;
        messagesItem.title = R.string.messages_menu_title;
        messagesItem.isHeader = false;
        items.add(messagesItem);
    }
    //</editor-fold>

    //<editor-fold desc="Public">
    public void AddNavigationDrawerView(String key, View v) {
        NavigationDrawerViews.put(key, v);
    }
    //</editor-fold>

}
