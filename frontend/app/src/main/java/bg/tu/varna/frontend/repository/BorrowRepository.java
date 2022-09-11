package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.model.BorrowDto;
import bg.tu.varna.frontend.network.model.BorrowRequestDto;
import bg.tu.varna.frontend.network.model.BorrowReturnDto;
import bg.tu.varna.frontend.network.model.BorrowReturnResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BorrowRepository {
    @GET("/api/v1/borrows")
    @Headers({"Accept: application/json"})
    Call<List<BorrowDto>> fetchAll(@Header("Authorization") String token);

    @PUT("/api/v1/borrows/{id}/return")
    @Headers({"Accept: application/json"})
    Call<BorrowReturnResponseDto> returnBook(@Header("Authorization") String token, @Path("id") Long borrowId, @Body BorrowReturnDto borrowReturnDto);

    @POST("/api/v1/borrows")
    @Headers({"Accept: application/json"})
    Call<BorrowDto> save(@Header("Authorization") String token, @Body BorrowRequestDto borrowRequestDto);
}
