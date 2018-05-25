package fan.com.jd02.Twofragment.MVP.V;

import java.util.List;

import fan.com.jd02.Twofragment.bean.Userbean;

/**
 * Created by fxf on 2018.04.20.
 */

public interface IMainView {
    void onSuccess(List<Userbean.DataBean> data);
    void onFailed(String msg);

}
