package fan.com.jd02.shangpinxiangqing.MVP;

import okhttp3.ResponseBody;

/**
 * Created by fxf on 2018.05.18.
 */

public class SpxqP implements SpxqinterP {
    private SpxqM duanZiModel;
    private SpxqinterV iDuanZiView;

    public SpxqP(){
        duanZiModel = new SpxqM(this);
    }

    public void attachView(SpxqinterV iDuanZiView){
        this.iDuanZiView = iDuanZiView;
    }

    public void dettachView(){
        if (iDuanZiView != null){
            iDuanZiView = null;
        }
    }

    public void getData(String url, String page){
        duanZiModel.getData(url,page);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        iDuanZiView.onSuccess(responseBody);
    }
}
