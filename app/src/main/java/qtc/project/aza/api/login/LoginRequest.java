package qtc.project.aza.api.login;

import android.text.TextUtils;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.helper.Consts;
import qtc.project.aza.model.BaseResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("LoginRequest")
public class LoginRequest extends ApiRequest<LoginRequest.Service, BaseResponseModel, LoginRequest.ApiParams> {

    public LoginRequest() {
        super(LoginRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel result) throws Exception {
        if (result != null && !TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

            AppProvider.getPreferences().saveStatusLogin(true);
        }
    }

    @Override
    protected Call<BaseResponseModel> call(ApiParams params) {
        params.detect = "login";
        return getService().call(params);
    }

    public interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel> call(@Body LoginRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String username;
        public String password;

    }
}
