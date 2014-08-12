package prescription.technology;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import prescription.technology.code.PrescriptionTechnologyWithNavigationDrawer;
import prescription.technology.code.receivers.CartBroadcastReceiver;

import java.util.HashMap;

public class Index extends PrescriptionTechnologyWithNavigationDrawer {

    private final String TAG = Index.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appView.loadUrl("file:///android_asset/www/index.html");
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.v(TAG, query);
        }
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
    protected HashMap<String, BroadcastReceiver> GetBroadcastsMap() {
        HashMap<String, BroadcastReceiver> map = new HashMap<String, BroadcastReceiver>();
        CartBroadcastReceiver br = new CartBroadcastReceiver();
        map.put("CART", br);
        return map;
    }

}
