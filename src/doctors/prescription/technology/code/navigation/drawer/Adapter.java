package doctors.prescription.technology.code.navigation.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import doctors.prescription.technology.R;
import doctors.prescription.technology.code.PrescriptionTechnologyWithNavigationDrawer;

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
        Item item = getItem(position);
        ViewHolder holder = null;
        View view = convertView;

        if (view == null) {
            int layout = R.layout.menu_row_counter;
            if (item.isHeader)
                layout = R.layout.menu_row_header;

            view = LayoutInflater.from(getContext()).inflate(layout, null);

            TextView text1 = (TextView) view.findViewById(R.id.menurow_title);
            ImageView image1 = (ImageView) view.findViewById(R.id.menurow_icon);
            TextView textcounter1 = (TextView) view.findViewById(R.id.menurow_counter);
            view.setTag(new ViewHolder(text1, image1, textcounter1));
        }

        if (holder == null && view != null) {
            Object tag = view.getTag();
            if (tag instanceof ViewHolder) {
                holder = (ViewHolder) tag;
            }
        }


        if (item != null && holder != null) {

            if (holder.textHolder != null)
                holder.textHolder.setText(item.title);

            //Counter
            if (holder.textCounterHolder != null) {
                if (item.counter > 0) {
                    holder.textCounterHolder.setVisibility(View.VISIBLE);
                    holder.textCounterHolder.setText("" + item.counter);
                } else {
                    //Hide counter if == 0
                    holder.textCounterHolder.setVisibility(View.GONE);
                }
            }

            if (holder.imageHolder != null) {
                if (item.iconRes > 0) {
                    holder.imageHolder.setVisibility(View.VISIBLE);
                    holder.imageHolder.setImageResource(item.iconRes);
                } else {
                    holder.imageHolder.setVisibility(View.GONE);
                }
            }
        }

        return view;

    }

    public static class ViewHolder {
        public final TextView textHolder;
        public final ImageView imageHolder;
        public final TextView textCounterHolder;

        public ViewHolder(TextView text1, ImageView image1, TextView textcounter1) {
            this.textHolder = text1;
            this.imageHolder = image1;
            this.textCounterHolder = textcounter1;
        }
    }
}
