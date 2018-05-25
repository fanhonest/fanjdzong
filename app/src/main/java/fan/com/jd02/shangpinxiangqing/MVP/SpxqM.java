package fan.com.jd02.shangpinxiangqing.MVP;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import fan.com.jd02.appquanju.RetrofitHelper;
import fan.com.jd02.shangpinxiangqing.ApiSpxq;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by fxf on 2018.05.18.
 */

public class SpxqM {
    private SpxqinterP iDuanZiP;

    public SpxqM(SpxqinterP iDuanZiP){
        this.iDuanZiP = iDuanZiP;
    }

    public void getData(String url,String page){
        Map<String,String> parmars = new HashMap<>();
        parmars.put("pid", page);
        parmars.put("source", "android");
        Log.d("ShangpinxqActivity","page"+ page);

        RetrofitHelper.getApiService1(ApiSpxq.SPXQ).doGet1(url,parmars)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.d("ShangpinxqActivity", "运行");
                        iDuanZiP.onSuccess(responseBody);


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
