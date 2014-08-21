package doctors.prescription.technology.code.data.objects;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import doctors.prescription.technology.code.Constants;
import doctors.prescription.technology.code.data.base.DataObjectBaseList;
import doctors.prescription.technology.code.http.Request;
import doctors.prescription.technology.code.http.RequestRunnable;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by novac on 18-Aug-14.
 */
public class Pending extends DataObjectBaseList {

    public Pending(Context context) {
        super(context);
    }

    @Override
    public void Load() {
        Request request = new Request(_context);
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        request.setData(data);
        request.PostExecuteHandler = new RequestRunnable() {
            @Override
            public void run() {
                String response = getArgs().get("response").toString();
                Log.v(TAG, response);
                Intent loginCompletedItent = new Intent("PRESCRIPTION_LOADED");
                _context.sendBroadcast(loginCompletedItent);
            }
        };
        request.execute(Constants.PENDDING_LOAD_URL);
    }
}
