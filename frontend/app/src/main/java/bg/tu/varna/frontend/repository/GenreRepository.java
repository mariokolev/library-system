package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.model.GenreDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GenreRepository {
    @GET("/api/v1/genres")
    @Headers({"Accept: application/json"})
    Call<List<GenreDto>> fetchAll(@Header("Authorization") String token);
}
