package prescription.technology.code;

import android.webkit.JavascriptInterface;

public class Constants {

    public final String CUSTOMER_ADDRESS_ACTIVITY = "prescription.technology.CUSTOMER_ADDRESS";
    public final String CUSTOMER_PERSONAL_INFO_ACTIVITY = "prescription.technology.CUSTOMER_PERSONAL_INFO";
    public final String ORDER_CONFIRMARTION_ACTIVITY = "prescription.technology.ORDER_CONFIRMATION";
    public final String QUESTIONS_ACTIVITY = "prescription.technology.QUESTIONS";

    @JavascriptInterface
    public final String getCUSTOMER_ADDRESS_ACTIVITY() {
        return CUSTOMER_ADDRESS_ACTIVITY;
    }

    @JavascriptInterface
    public final String getCUSTOMER_PERSONAL_INFO_ACTIVITY() {
        return CUSTOMER_PERSONAL_INFO_ACTIVITY;
    }

    @JavascriptInterface
    public final String getORDER_CONFIRMARTION_ACTIVITY() {
        return ORDER_CONFIRMARTION_ACTIVITY;
    }

    @JavascriptInterface
    public final String getQUESTIONS_ACTIVITY() {
        return QUESTIONS_ACTIVITY;
    }
}
