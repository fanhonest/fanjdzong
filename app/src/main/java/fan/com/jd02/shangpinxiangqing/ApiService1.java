package fan.com.jd02.shangpinxiangqing;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by fxf on 2018.05.18.
 */

public interface ApiService1 {
    @GET
    Observable<ResponseBody> doGet1(@Url String url, @QueryMap Map<String, String> map);

}
