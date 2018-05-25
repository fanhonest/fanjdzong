package fan.com.jd02.fourfragment.mvp;

import java.util.List;

import fan.com.jd02.fourfragment.ShoppCarBean;

/**
 * Created by fxf on 2018.05.24.
 */

public class ShooppPrenster implements IShooppPrenster {
    private IMainView iMainView;
    private ShoppCarModel shoppCarModel;

    public ShooppPrenster(IMainView iMainView) {
        this.iMainView = iMainView;
        shoppCarModel = new ShoppCarModel();
    }

    @Override
    public void onSuccess(List<ShoppCarBean.DataBean> data) {
        if(iMainView!=null){
            iMainView.onSuccess(data);
        }
    }

    @Override
    public void onFailed(String msg) {
        if(iMainView!=null){

            iMainView.onFailed(msg);
        }

    }

    @Override
    public void onDestory() {
        if(iMainView!=null){
            iMainView=null;
        }
    }

    @Override
    public void getCarInfo(String uid,String token) {
        shoppCarModel.getCarInfo(uid,token,this);
    }
}
