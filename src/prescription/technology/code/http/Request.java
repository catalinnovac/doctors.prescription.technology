package prescription.technology.code.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by novac on 07-Aug-14.
 */
public class Request extends AsyncTask<String, List<NameValuePair>, String> {

    public final String TAG = Request.class.getSimpleName();
    public RequestRunnable onPostExecute__callback;
    public Runnable onPreExecute__callback;
    public RequestRunnable onBeforeDoInBackground__callback;
    public RequestRunnable onAfterDoInBackground__callback;
    public Runnable onPostExecuteException__callback;
    private Context mContext;
    private List<NameValuePair> data;

    public Request(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        if (onPreExecute__callback != null)
            onPreExecute__callback.run();
        super.onPreExecute();
    }

    protected List<NameValuePair> getData() {
        return data;
    }

    public void setData(List<NameValuePair> _data) {
        data = _data;
    }

    @Override
    protected String doInBackground(String... uri) {
        String responseString = null;
        //in case we want to execute an action in async thread and before executing the rest of code
        //we call a callback
        if (onBeforeDoInBackground__callback != null) {
            //Log.v(TAG, "onBeforeDoInBackground call");
            onBeforeDoInBackground__callback.run();
        }

        try {

            if (NetworkManager.isNetworkConnection(mContext)) {
                HttpPost request = new HttpPost(uri[0]);
                request.setHeader("Content-Type",
                        "application/x-www-form-urlencoded");
                request.setEntity(new UrlEncodedFormEntity(getData()));
                HttpResponse response = prescription.technology.code.http.HttpClient.INSTANCE.getHttp().execute(request);
                StatusLine statusLine = response.getStatusLine();
                //Log.v(TAG,url + ">>STATUS>>"+ String.valueOf(statusLine.getStatusCode()));
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity resEntity = response.getEntity();
                    String result = EntityUtils.toString(resEntity);
                    return result;
                } else {
                    Log.e(TAG, uri[0] + ">>STATUS>>"
                            + String.valueOf(statusLine.getStatusCode()));
                    return null;
                }
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //execute (if is defined) this callback in the same thread as action before
        if (onAfterDoInBackground__callback != null) {
            //Log.v(TAG, "onAfterDoInBackground call");
            Hashtable h = new Hashtable();
            h.put("response", responseString);
            onAfterDoInBackground__callback.setArgs(h);
            onAfterDoInBackground__callback.run();
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (NetworkManager.isNetworkConnection(mContext)) {
            if (result != null) {
                Hashtable args = new Hashtable();
                try {

                } catch (Exception e) {
                    args.put("status", "error");
                    args.put("message", e.getMessage());
                }
                if (onPostExecute__callback != null) {
                    onPostExecute__callback.setArgs(args);
                    onPostExecute__callback.run();
                }
            } else {
                Toast.makeText(mContext, "Malformed response from sever!", Toast.LENGTH_LONG).show();
                if (onPostExecuteException__callback != null)
                    onPostExecuteException__callback.run();
            }
        } else {
            Toast.makeText(mContext, "Check your Internet connection!", Toast.LENGTH_LONG).show();
            if (onPostExecuteException__callback != null)
                onPostExecuteException__callback.run();
        }
    }


}
