package in.eightbitlabs.carpool.data.remote;

import java.util.List;

import in.eightbitlabs.carpool.data.model.Post;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Single;

public interface CarpoolService {

    String ENDPOINT = "http://www.8bitlabs.in/api/v1/";
//    String ENDPOINT = "http://192.168.122.1:3000/api/v1/";

    @GET("auth/facebook/token")
    Single<ResponseBody> verifyUser();

    @GET("me/posts")
    Observable<List<Post>> getUserPosts();

    @GET("posts")
    Observable<List<Post>> getPosts(@Query("page") int page);

    @POST("post")
    Observable<Response> post(@Body Post post);
}
