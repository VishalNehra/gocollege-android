package in.eightbitlabs.carpool.data.remote.interceptor;

import com.facebook.AccessToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request newRequest = originalRequest.newBuilder()
                .header("Authorization",
                        "Bearer " + AccessToken.getCurrentAccessToken().getToken())
                .build();
        return chain.proceed(newRequest);
    }
}