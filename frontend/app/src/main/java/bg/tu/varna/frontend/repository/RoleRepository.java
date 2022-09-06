package bg.tu.varna.frontend.repository;

import java.util.List;

import bg.tu.varna.frontend.network.model.RoleDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface RoleRepository {

    @GET("/api/v1/roles")
    @Headers({"Accept: application/json"})
    Call<List<RoleDto>> fetchAll(@Header("Authorization") String token);
}
