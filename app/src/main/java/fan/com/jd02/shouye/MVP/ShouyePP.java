package fan.com.jd02.shouye.MVP;

import okhttp3.ResponseBody;

/**
 * Created by fxf on 2018.05.17.
 */

public class ShouyePP implements ShouyeP {
    private ShouyeMM shouyeMM;
    private ShouyeV shouyeV;

    public ShouyePP(){
        shouyeMM = new ShouyeMM(this);
    }

    public void attachView(ShouyeV iDuanZiView){
        this.shouyeV = iDuanZiView;
    }

    public void dettachView(){
        if (shouyeV != null){
            shouyeV = null;
        }
    }

    public void getData(String url,String page){
        shouyeMM.getData(url,page);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        shouyeV.onSuccess(responseBody);
    }
}
