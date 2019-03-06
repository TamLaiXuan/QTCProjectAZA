package qtc.project.aza.api.check_order;

import android.support.annotation.Nullable;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.aza.helper.Consts;
import qtc.project.aza.model.BaseResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("CheckOrder")
public class RequestCheckOrder extends ApiRequest<RequestCheckOrder.Service, BaseResponseModel, RequestCheckOrder.ApiParams> {

    public RequestCheckOrder() {
        super(RequestCheckOrder.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel> call(ApiParams params) {
        return getService().call(params);
    }

    public interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel> call(@Body RequestCheckOrder.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {

        public String detect;

        @Nullable
        public String id_order;
    }
}
