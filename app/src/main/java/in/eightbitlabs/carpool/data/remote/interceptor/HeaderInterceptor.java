package in.eightbitlabs.carpool.data.remote.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request newRequest = originalRequest.newBuilder()
                .header("Accept", "application/json")
                .header("User-Agent","carpool-android-app")
                .build();
        return chain.proceed(newRequest);
    }
}
