package qtc.project.aza.api.get_product_detail;

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
public class RequestGetProductDetail extends ApiRequest<RequestGetProductDetail.Service, ListProductResponseModel, RequestGetProductDetail.ApiParams> {

    public RequestGetProductDetail() {
        super(RequestGetProductDetail.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(ListProductResponseModel result) throws Exception {

    }

    @Override
    protected Call<ListProductResponseModel> call(ApiParams params) {
        params.detect = "Update_quantity";
        return getService().call(params);
    }

    public interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<ListProductResponseModel> call(@Body RequestGetProductDetail.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String id_order;
        @Nullable
        public int quantity_1;

        @Nullable
        public int quantity_2;

        @Nullable
        public int price;

    }
}
