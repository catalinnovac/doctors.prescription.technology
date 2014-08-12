package prescription.technology.code.http;

import java.util.Hashtable;

/**
 * Created by novac on 07-Aug-14.
 */
public class RequestRunnable implements Runnable {
    private Hashtable args;

    public Hashtable getArgs() {
        return args;
    }

    public void setArgs(Hashtable args1) {
        args = args1;
    }

    @Override
    public void run() {
    }
}
