package prescription.technology.code.navigation.drawer;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by novac on 07-Aug-14.
 */
public class DrawerToggle extends ActionBarDrawerToggle {
    public DrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        //getActionBar().setTitle(mTitle);
    }

    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        //getActionBar().setTitle(mDrawerTitle);
    }
}
