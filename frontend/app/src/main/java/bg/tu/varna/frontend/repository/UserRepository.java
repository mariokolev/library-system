package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.LoginRequest;
import bg.tu.varna.frontend.network.model.LoginResponse;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.network.model.UserRequestDto;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserRepository {

    @POST("/api/v1/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/api/v1/users")
    @Headers({"Accept: application/json"})
    Call<List<UserDto>> fetchAll(@Header("Authorization") String token);

    @POST("/api/v1/users")
    @Headers({"Accept: application/json"})
    Call<UserDto> save(@Header("Authorization") String token, @Body UserRequestDto userRequestDto);
}
