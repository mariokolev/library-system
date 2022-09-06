package bg.tu.varna.frontend.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ErrorMessage parseError(Response<?> response) {
        Converter<ResponseBody, ErrorMessage> converter = RetrofitConfig
                .getInstance()
                .responseBodyConverter(ErrorMessage.class, new Annotation[0]);

        ErrorMessage error;
        try {
            ResponseBody value = response.errorBody();
            error = converter.convert(value);
        } catch (IOException e) {
            return new ErrorMessage();
        }

        return error;
    }
}
