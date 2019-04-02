package qtc.project.aza.api.update_product_quantity;

import android.support.annotation.Nullable;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.aza.helper.Consts;
import qtc.project.aza.model.ListProductResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("ProductDetail")
public class RequestUpdateProductQuantity extends ApiRequest<RequestUpdateProductQuantity.Service, ListProductResponseModel, RequestUpdateProductQuantity.ApiParams> {

    public RequestUpdateProductQuantity() {
        super(RequestUpdateProductQuantity.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(ListProductResponseModel result) throws Exception {

    }

    @Override
    protected Call<ListProductResponseModel> call(ApiParams params) {
//        params.detect = "Update_quantity";
        return getService().call(params);
    }

    public interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<ListProductResponseModel> call(@Body RequestUpdateProductQuantity.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String id_order;
        @Nullable
        public String quantity_1;

        @Nullable
        public String quantity_2;

        @Nullable
        public String price;

    }
}
