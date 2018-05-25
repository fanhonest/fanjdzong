package fan.com.jd02.appquanju;

import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import fan.com.jd02.shangpinxiangqing.ApiService1;
import fan.com.jd02.shouye.ashouyemian.ApiService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fxf on 2018.05.17.
 */

public class RetrofitHelper {
    public static OkHttpClient okHttpClient;
    public static ApiService apiService;
    public static ApiService1 apiService1;

    static {
        getOkHttpClient();
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null){
            synchronized (OkHttpClient.class){
                if (okHttpClient == null){
                    File file = new File(Environment.getExternalStorageDirectory(),"cahce");
                    long fileSize = 10*1024*1024;
                    okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15,TimeUnit.SECONDS)
                            .connectTimeout(15,TimeUnit.SECONDS)
                            .cache(new Cache(file,fileSize))
                            .build();
                }
            }
        }
        return okHttpClient;
    }
    public static ApiService1 getApiService1(String url){
        if (apiService1 == null){
            synchronized (OkHttpClient.class){
                apiService1 = createApiService(ApiService1.class,url);
            }
        }
        return apiService1;
    }
    public static ApiService getApiService(String url){
        if (apiService == null){
            synchronized (OkHttpClient.class){
                apiService = createApiService(ApiService.class,url);
            }
        }
        return apiService;
    }

    private static <T>T createApiService(Class<T> tClass, String url) {
        T t = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(tClass);
        return t;
    }
}
