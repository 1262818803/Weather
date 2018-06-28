package ui.com.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asus on 2018/6/28.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient Client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        Client.newCall(request).enqueue(callback);
    }
}
