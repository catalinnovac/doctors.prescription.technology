package doctors.prescription.technology.code;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import doctors.prescription.technology.R;
import doctors.prescription.technology.code.http.HttpMethod;
import doctors.prescription.technology.code.http.Request;
import doctors.prescription.technology.code.http.RequestRunnable;
import doctors.prescription.technology.code.navigation.drawer.Adapter;
import doctors.prescription.technology.code.navigation.drawer.DrawerToggle;
import doctors.prescription.technology.code.navigation.drawer.Item;
import doctors.prescription.technology.code.webview.CustomCordovaWebView;
import doctors.prescription.technology.code.webview.WebViewInterface;
import org.apache.cordova.Config;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by novac on 24-Jul-14.
 */
public abstract class PrescriptionTechnologyWithNavigationDrawer extends Activity implements CordovaInterface {

    //<editor-fold desc="Final">
    private static final String TAG = "PRESCRIPTION TECHNOLOGY";
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    //</editor-fold>

    //<editor-fold desc="Fields">
    public HashMap<String, View> NavigationDrawerViews = new HashMap<String, View>();
    public DrawerLayout mDrawerLayout;
    public CustomCordovaWebView appView;
    boolean activityResultKeepRunning;
    boolean keepRunning;
    List<Item> items;
    private ListView mDrawerList;
    private CordovaPlugin activityResultCallback;
    private ActionBarDrawerToggle mDrawerToggle;
    private ConcurrentHashMap<String, BroadcastReceiver> broadcastReceiverHashMap;
    private Request request;
    private Context context;
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
        appView = (CustomCordovaWebView) findViewById(R.id.cordova_main_webview);
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
                        appView.CurrentPage = "file:///android_asset/www/pending.html";
                        appView.loadUrl("file:///android_asset/www/pending.html");
                        break;
                    case 1://history
                        appView.CurrentPage = "file:///android_asset/www/history.html";
                        appView.loadUrl("file:///android_asset/www/history.html");
                        break;
                    case 2://declined
                        appView.CurrentPage = "file:///android_asset/www/declined.html";
                        appView.loadUrl("file:///android_asset/www/declined.html");
                        break;
                    case 3://query
                        appView.CurrentPage = "file:///android_asset/www/query.html";
                        appView.loadUrl("file:///android_asset/www/query.html");
                        break;
                    case 4: //invoices
                        appView.CurrentPage = "file:///android_asset/www/invoices.html";
                        appView.loadUrl("file:///android_asset/www/invoices.html");
                        break;
                    case 5: //messages
                        appView.CurrentPage = "file:///android_asset/www/messages.html";
                        appView.loadUrl("file:///android_asset/www/messages.html");
                        break;
                }
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        //deschide meniu lateral(stanga)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        appView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) view;
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        new AlertDialog.Builder(webView.getContext())
                                .setMessage("Are you sure you want to exit?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //populeaza harta de receiver-e din activity-ul curent
        broadcastReceiverHashMap = GetBroadcastsMap();
        for (String key : broadcastReceiverHashMap.keySet()) {
            //inregistreaza fiecare receiver
            registerReceiver(broadcastReceiverHashMap.get(key), new IntentFilter(key));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        for (String key : broadcastReceiverHashMap.keySet()) {
            //unregister fiecare receiver
            unregisterReceiver(broadcastReceiverHashMap.get(key));
            broadcastReceiverHashMap.remove(key);
        }
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
    protected void PopulateLeftMenu() {
        items = new ArrayList<Item>();
        context = this;
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        request = new Request(this);
        request.setRequestMethod(HttpMethod.GET);
        request.BeforeDoRequestInBackgroundHandler = new RequestRunnable() {
            @Override
            public void run() {
                HttpUriRequest httpUriRequest = (HttpUriRequest) getArgs().get("request");
                httpUriRequest.addHeader("Authorization", sharedPreferences.getString("TOKEN", null));
            }
        };
        request.PostExecuteHandler = new RequestRunnable() {
            @Override
            public void run() {
                String response = getArgs().get("response").toString();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray menus = jsonObject.getJSONArray("Menu");
                    for (int i = 0; i < menus.length(); i++) {
                        JSONObject menu = menus.getJSONObject(i);
                        String menuName = menu.getString("name");
                        String counter = menu.getString("counter");
                        int iconResource = 0;
                        Log.v(TAG, "MENU NAME:" + menuName);
                        Item item = new Item();

                        if (menuName.equals("Pending"))
                            item.iconRes = R.drawable.adjust4;
                        else if (menuName.equals("History"))
                            item.iconRes = R.drawable.big37;
                        else if (menuName.equals("Declined"))
                            item.iconRes = R.drawable.thumbs27;
                        else if (menuName.equals("Query"))
                            item.iconRes = R.drawable.white24;
                        else if (menuName.equals("Invoices"))
                            item.iconRes = R.drawable.list30;
                        else if (menuName.equals("Messages"))
                            item.iconRes = R.drawable.speech59;

                        if (counter != null && counter.length() > 0)
                            item.counter = Integer.decode(counter);
                        else
                            item.counter = 0;
                        item.title = menuName;
                        item.isHeader = false;
                        items.add(item);
                    }
                    mDrawerList.setAdapter(new Adapter(context,
                            R.layout.left_drawer_item, items));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        request.execute(Constants.MENU_DATABINDING_URL);
        try {
            request.get(30, TimeUnit.SECONDS);//wait for menu items to be populated
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        /*
        mDrawerList.setAdapter(new Adapter(this,
                R.layout.left_drawer_item, items));
        */
        /*
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
        */
    }
//</editor-fold>

    //<editor-fold desc="Public">
    public void AddNavigationDrawerView(String key, View v) {
        NavigationDrawerViews.put(key, v);
    }
    //</editor-fold>

}
