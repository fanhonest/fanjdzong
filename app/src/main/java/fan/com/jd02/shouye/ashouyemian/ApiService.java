package fan.com.jd02.shouye.ashouyemian;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by fxf on 2018.05.17.
 */

public interface ApiService {
    @GET
    Observable<ResponseBody> doGet(@Url String url,@QueryMap Map<String, String> parmars);
}
