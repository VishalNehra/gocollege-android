package in.eightbitlabs.gocollege.data.remote;

import java.util.List;

import in.eightbitlabs.gocollege.data.model.Post;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface GoCollegeService {

    String ENDPOINT = "http://api.gocollege.8bitlabs.in/api/v1/";
//    String ENDPOINT = "http://192.168.1.161:3000/api/v1/";

    @GET("auth/facebook/token")
    Observable<ResponseBody> verifyUser();

    @GET("me/posts")
    Observable<List<Post>> getUserPosts();

    @GET("posts")
    Observable<List<Post>> getPosts(@Query("page") int page);

    @POST("post")
    Observable<ResponseBody> post(@Body Post post);
}
