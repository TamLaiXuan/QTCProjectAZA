package qtc.project.aza.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.aza.R;
import qtc.project.aza.api.update_product_quantity.RequestUpdateProductQuantity;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.event.ReloadDataCheckingOrderEvent;
import qtc.project.aza.event.ReloadDataListOrderEvent;
import qtc.project.aza.model.ListProductResponseModel;
import qtc.project.aza.model.ProductResponseModel;
import qtc.project.aza.ui.views.fragment.fragment_product_detail.FragmentProductDetailView;
import qtc.project.aza.ui.views.fragment.fragment_product_detail.FragmentProductDetailViewCallback;
import qtc.project.aza.ui.views.fragment.fragment_product_detail.FragmentProductDetailViewInterface;

public class FragmentProductDetail extends BaseFragment<FragmentProductDetailViewInterface, BaseParameters> implements FragmentProductDetailViewCallback {

    public static FragmentProductDetail newInstance(ProductResponseModel item, String type) {
        FragmentProductDetail frag = new FragmentProductDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        bundle.putString("type", type);
        frag.setArguments(bundle);
        return frag;
    }

    private String type;

    @Override
    protected void initialize() {
        view.init(this);

        if (getArguments() != null) {
            ProductResponseModel item = (ProductResponseModel) getArguments().getSerializable("item");
            type = getArguments().getString("type", "");
            if (item != null) {
                view.setDataProductItem(item, type);
            }

        }
    }

    @Override
    protected FragmentProductDetailViewInterface getViewInstance() {
        return new FragmentProductDetailView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onUpdateProductQuantity(String orderId, String quantityPurchase1, String quantityPurchase2, String pricePurchase, String unit) {

        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showToast(R.string.error_connect_internet);
            return;
        }

        showProgress();


        RequestUpdateProductQuantity.ApiParams params = new RequestUpdateProductQuantity.ApiParams();

        if (type.equalsIgnoreCase("checking")) {
            params.detect = "update_quantity_kiemhang";

        } else {
            params.detect = "Update_quantity";

        }

        params.id_order = orderId;
        if (!TextUtils.isEmpty(quantityPurchase1))
            params.quantity_1 = quantityPurchase1;

        if (!TextUtils.isEmpty(quantityPurchase2))
            params.quantity_2 = (quantityPurchase2);

        if (!TextUtils.isEmpty(pricePurchase))
            params.price = (pricePurchase);

        AppProvider.getApiManagement().call(RequestUpdateProductQuantity.class, params, new ApiRequest.ApiCallback<ListProductResponseModel>() {
            @Override
            public void onSuccess(ListProductResponseModel result) {
                dismissProgress();
                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
                    showToast("Cập nhật thông tin thành công.");

                    if (type.equalsIgnoreCase("checking")) {
                        ReloadDataCheckingOrderEvent.post();
                    } else {
                        ReloadDataListOrderEvent.post();
                    }
//                    if (result.getData() != null) {
//                        ProductResponseModel item = result.getData()[0];
//                        view.setDataProductItem(item);
//                    }
                    view.UpdateProduct(quantityPurchase1, quantityPurchase2, pricePurchase, unit);
                } else {
                    showToast("Thay đổi số lượng cung ứng không thành công.");
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                showToast("Thay đổi số lượng cung ứng không thành công.");
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                showToast("Thay đổi số lượng cung ứng không thành công.");
            }
        });


    }
}
