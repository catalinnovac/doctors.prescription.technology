package doctors.prescription.technology.code.data;

import android.content.Context;
import android.webkit.JavascriptInterface;
import doctors.prescription.technology.code.data.objects.*;

/**
 * Created by novac on 18-Aug-14.
 */
public class DataContext {
    public Context _Context;
    private Declined declined;
    private History history;
    private Invoices invoices;
    private Login login;
    private Messages messages;
    private Pending pending;
    private Query query;

    public DataContext(Context context) {
        _Context = context;
    }

    @JavascriptInterface
    public Login getLogin() {
        login = new Login(_Context);
        return login;
    }

    @JavascriptInterface
    public Declined getDeclined() {
        declined = new Declined(_Context);
        return declined;
    }

    @JavascriptInterface
    public History getHistory() {
        history = new History(_Context);
        return history;
    }

    @JavascriptInterface
    public Invoices getInvoices() {
        invoices = new Invoices(_Context);
        return invoices;
    }

    @JavascriptInterface
    public Messages getMessages() {
        messages = new Messages(_Context);
        return messages;
    }

    @JavascriptInterface
    public Pending getPending() {
        pending = new Pending(_Context);
        return pending;
    }

    @JavascriptInterface
    public Query getQuery() {
        query = new Query(_Context);
        return query;
    }


}
