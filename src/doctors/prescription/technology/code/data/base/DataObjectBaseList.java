package doctors.prescription.technology.code.data.base;

import android.content.Context;

/**
 * Created by novac on 18-Aug-14.
 */
public abstract class DataObjectBaseList extends DataObjectBase {
    public DataObjectBaseList(Context context) {
        super(context);
    }

    public abstract String Load();
}
