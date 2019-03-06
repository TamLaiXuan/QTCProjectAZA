package qtc.project.aza.api.checking_order;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.aza.helper.Consts;
import qtc.project.aza.model.ListProductResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("CheckingOrder")
public class RequestCheckingOrder extends ApiRequest<RequestCheckingOrder.Service, ListProductResponseModel, RequestCheckingOrder.ApiParams> {

    public RequestCheckingOrder() {
        super(RequestCheckingOrder.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(ListProductResponseModel result) throws Exception {

    }

    @Override
    protected Call<ListProductResponseModel> call(ApiParams params) {
        params.detect = "purchases_product_list";
        return getService().call(params);
    }

    public interface Service {

        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<ListProductResponseModel> call(@Body RequestCheckingOrder.ApiParams params);

    }

    public static class ApiParams extends BaseApiParams {

        public String detect;
    }
}

