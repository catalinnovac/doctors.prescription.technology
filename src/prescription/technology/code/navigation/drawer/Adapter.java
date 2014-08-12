package prescription.technology.code.navigation.drawer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import prescription.technology.R;
import prescription.technology.code.PrescriptionTechnologyWithNavigationDrawer;

import java.util.List;

/**
 * Created by novac on 26-Jul-14.
 */
public class Adapter extends ArrayAdapter<Item> {

    private static final String TAG = "ADAPTER";
    private List<Item> internal_items;
    private PrescriptionTechnologyWithNavigationDrawer internal_context;

    public Adapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public Adapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        internal_items = items;
        internal_context = (PrescriptionTechnologyWithNavigationDrawer) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Item p = getItem(position);
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.left_drawer_item, null);
        }
        if (p != null) {
            CustomCordovaWebView cordovaWebView = (CustomCordovaWebView) v.findViewById(R.id.cordova_left_item_webview);
            if (cordovaWebView != null) {
                Log.v(TAG, "LOAD URL:" + p.Id);
                cordovaWebView.getSettings().setJavaScriptEnabled(true);
                cordovaWebView.loadUrl(p.CONTENT);
                internal_context.AddNavigationDrawerView("CART", cordovaWebView);
            }
        }
        return v;

    }

    public View getViewById(String Id) {
        int position = 0;
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).Id == Id)
                position = i;
            break;
        }
        return getView(position, null, null);
    }
}
