package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.model.BookDto;
import bg.tu.varna.frontend.network.model.BookRequestDto;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.network.model.UserRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BookRepository {
    @GET("/api/v1/books")
    @Headers({"Accept: application/json"})
    Call<List<BookDto>> fetchAll(@Header("Authorization") String token);

    @POST("/api/v1/books")
    @Headers({"Accept: application/json"})
    Call<BookDto> save(@Header("Authorization") String token, @Body BookRequestDto bookRequestDto);
}
