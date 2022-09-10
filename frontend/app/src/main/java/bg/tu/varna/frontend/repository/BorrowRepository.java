package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.model.BorrowDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface BorrowRepository {
    @GET("/api/v1/borrows")
    @Headers({"Accept: application/json"})
    Call<List<BorrowDto>> fetchAll(@Header("Authorization") String token);
}
