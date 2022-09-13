package api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RetrofitUtils {
    public static <T> T execute(Call<T> call){
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()){
                return response.body();
            } else {
                try (ResponseBody errorBody = response.errorBody()){
                    assert errorBody !=null;
                    String error = errorBody.string();
                    throw new RuntimeException(error);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
