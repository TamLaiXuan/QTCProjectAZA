package qtc.project.aza.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.aza.R;
import qtc.project.aza.api.get_product_detail.RequestGetProductDetail;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.model.ListProductResponseModel;
import qtc.project.aza.model.ProductResponseModel;
import qtc.project.aza.ui.views.fragment.fragment_product_detail.FragmentProductDetailView;
import qtc.project.aza.ui.views.fragment.fragment_product_detail.FragmentProductDetailViewCallback;
import qtc.project.aza.ui.views.fragment.fragment_product_detail.FragmentProductDetailViewInterface;

public class FragmentProductDetail extends BaseFragment<FragmentProductDetailViewInterface, BaseParameters> implements FragmentProductDetailViewCallback {

    public static FragmentProductDetail newInstance(ProductResponseModel item) {
        FragmentProductDetail frag = new FragmentProductDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        view.init(this);

        if (getArguments() != null) {
            ProductResponseModel item = (ProductResponseModel) getArguments().getSerializable("item");
            if (item != null)
                view.setDataProductItem(item);
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

        RequestGetProductDetail.ApiParams params = new RequestGetProductDetail.ApiParams();

        params.id_order = orderId;
        if (!TextUtils.isEmpty(quantityPurchase1))
            params.quantity_1 = Integer.valueOf(quantityPurchase1);

        if (!TextUtils.isEmpty(quantityPurchase2))
            params.quantity_2 = Integer.valueOf(quantityPurchase2);

        if (!TextUtils.isEmpty(pricePurchase))
            params.price = Integer.valueOf(pricePurchase);

        AppProvider.getApiManagement().call(RequestGetProductDetail.class, params, new ApiRequest.ApiCallback<ListProductResponseModel>() {
            @Override
            public void onSuccess(ListProductResponseModel result) {
                dismissProgress();
                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
                    showToast("Cập nhật thông tin thành công.");
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
