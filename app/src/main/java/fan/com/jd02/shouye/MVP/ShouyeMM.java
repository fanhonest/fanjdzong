package fan.com.jd02.shouye.MVP;

import java.util.HashMap;
import java.util.Map;

import fan.com.jd02.appquanju.RetrofitHelper;
import fan.com.jd02.shouye.ashouyemian.Api;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by fxf on 2018.05.17.
 */

public class ShouyeMM {
    private ShouyeP shouyeP;

    public ShouyeMM(ShouyeP iDuanZiP){
        this.shouyeP = iDuanZiP;
    }

    public void getData(String url,String page){
    Map<String,String> parmars = new HashMap<>();
        parmars.put("page", page);
        RetrofitHelper.getApiService(Api.SHOUYE).doGet(url, parmars)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        shouyeP.onSuccess(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
