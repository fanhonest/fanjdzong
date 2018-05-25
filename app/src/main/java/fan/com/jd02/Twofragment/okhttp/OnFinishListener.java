package fan.com.jd02.Twofragment.okhttp;

/**
 * Created by fxf on 2018.04.20.
 */

public interface OnFinishListener {
    void onFailed(String msg);
    void onSuccess(Object obj);
}
