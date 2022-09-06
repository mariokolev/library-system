package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.model.LibraryDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface LibraryRepository {

    @GET("/api/v1/libraries")
    @Headers({"Accept: application/json"})
    Call<List<LibraryDto>> fetchAll(@Header("Authorization") String token);
}
