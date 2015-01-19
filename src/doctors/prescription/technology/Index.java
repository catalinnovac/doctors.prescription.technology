package doctors.prescription.technology;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import doctors.prescription.technology.code.PrescriptionTechnologyWithNavigationDrawer;
import doctors.prescription.technology.code.data.DataContext;

import java.util.concurrent.ConcurrentHashMap;

public class Index extends PrescriptionTechnologyWithNavigationDrawer {

    private final String TAG = Index.class.getSimpleName();
    ConcurrentHashMap<String, BroadcastReceiver> map = new ConcurrentHashMap<String, BroadcastReceiver>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataContext dataContext = new DataContext(this);
        appView.addJavascriptInterface(dataContext, "data");
        appView.getSettings().setDomStorageEnabled(true);
        appView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.v(TAG, query);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String TOKEN = sharedPreferences.getString("TOKEN", null);
        if (TOKEN != null) {
            Intent loginCompletedItent = new Intent("LOGIN_COMPLETED");
            loginCompletedItent.putExtra("TOKEN", TOKEN);
            sendBroadcast(loginCompletedItent);
        } else
            appView.loadUrl("file:///android_asset/www/conectare.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    protected ConcurrentHashMap<String, BroadcastReceiver> GetBroadcastsMap() {
        BroadcastReceiver loginCompleted = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v(TAG, String.valueOf(map.size()));
                Log.v(TAG, String.valueOf(intent.hasExtra("TOKEN")));
                if (intent.hasExtra("TOKEN")) {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    PopulateLeftMenu();
                    appView.loadCurrentPage();
                    appView.clearHistory();
                } else {
                    appView.sendJavascript("OnloginErrorEventHandler();");
                }
            }
        };
        BroadcastReceiver drawer = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("open")) {
                    if (intent.getStringExtra("open").equals("1")) {
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                } else
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        };
        map.put("LOGIN_COMPLETED", loginCompleted);
        map.put("DRAWER", drawer);
        return map;
    }
}
