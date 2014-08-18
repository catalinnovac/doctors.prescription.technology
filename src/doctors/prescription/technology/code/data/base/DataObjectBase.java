package doctors.prescription.technology.code.data.base;

import android.content.Context;

/**
 * Created by novac on 18-Aug-14.
 */
public abstract class DataObjectBase {
    protected static String TAG;
    protected Context _context;

    public DataObjectBase(Context context) {
        _context = context;
        TAG = this.getClass().getSimpleName();
    }

}
