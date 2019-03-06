package qtc.project.aza.api.get_list_product;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.aza.helper.Consts;
import qtc.project.aza.model.ListProductResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("GetListProduct")
public class RequestGetListProduct extends ApiRequest<RequestGetListProduct.Service, ListProductResponseModel, RequestGetListProduct.ApiParams> {

    public RequestGetListProduct() {
        super(RequestGetListProduct.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(ListProductResponseModel result) throws Exception {

    }

    @Override
    protected Call<ListProductResponseModel> call(ApiParams params) {
        params.detect = "order_products_list";
        return getService().call(params);
    }

    public interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<ListProductResponseModel> call(@Body RequestGetListProduct.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
    }
}
