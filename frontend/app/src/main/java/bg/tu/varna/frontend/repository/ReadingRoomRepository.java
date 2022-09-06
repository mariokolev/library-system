package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.model.ReadingRoomDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ReadingRoomRepository {
    @GET("/api/v1/reading-rooms")
    @Headers({"Accept: application/json"})
    Call<List<ReadingRoomDto>> fetchAll(@Header("Authorization") String token);
}
