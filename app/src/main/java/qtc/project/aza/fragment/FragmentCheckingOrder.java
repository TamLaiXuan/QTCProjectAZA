package qtc.project.aza.fragment;

import android.text.TextUtils;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.aza.R;
import qtc.project.aza.activity.HomeActivity;
import qtc.project.aza.api.checking_order.RequestCheckingOrder;
import qtc.project.aza.api.get_list_product.RequestGetListProduct;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.event.ReloadDataCheckingOrderEvent;
import qtc.project.aza.model.ListProductResponseModel;
import qtc.project.aza.model.ProductResponseModel;
import qtc.project.aza.ui.views.fragment.fragment_checking_order.FragmentCheckingOrderView;
import qtc.project.aza.ui.views.fragment.fragment_checking_order.FragmentCheckingOrderViewCallback;
import qtc.project.aza.ui.views.fragment.fragment_checking_order.FragmentCheckingOrderViewInterface;

public class FragmentCheckingOrder extends BaseFragment<FragmentCheckingOrderViewInterface, BaseParameters> implements FragmentCheckingOrderViewCallback {

    private List<ProductResponseModel> listProductResponseModels = new ArrayList<>();

    private HomeActivity homeActivity;

    private String viewMode = "checking";

    @Override
    protected void initialize() {
        view.init(this);

        homeActivity = (HomeActivity) getActivity();

        if (listProductResponseModels.size() == 0)
            requestGetListCheckingOrder(true);
        else
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setDataListItem(listProductResponseModels);
                }
            }, 100);
//        listProductResponseModels.clear();
//        requestGetListCheckingOrder();

    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKeyboardDissmiss(ReloadDataCheckingOrderEvent event) {
        if (view != null) {
            listProductResponseModels.clear();

            requestGetListCheckingOrder(false);
        }
    }

    @Override
    public void onItemSelected(ProductResponseModel item, View view, int pos) {
        if (homeActivity != null)
            homeActivity.changeToFragmentProductDetail(item);
    }

    public void resetListData() {
        listProductResponseModels.clear();
    }

    @Override
    public void onRequestGetListProductChecking() {
        if (listProductResponseModels.size() ==0) {
            resetListData();
            requestGetListCheckingOrder(true);
        }else{
            view.setDataListItem(listProductResponseModels);
        }
    }

    public void requestGetListCheckingOrder(boolean isShowLoadingView) {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showToast(R.string.error_connect_internet);
            view.setDataListItem(null);
            return;
        }
        if (isShowLoadingView)
            showProgress();

        RequestCheckingOrder.ApiParams params = new RequestCheckingOrder.ApiParams();
        AppProvider.getApiManagement().call(RequestCheckingOrder.class, params, new ApiRequest.ApiCallback<ListProductResponseModel>() {
            @Override
            public void onSuccess(ListProductResponseModel result) {
                if (isShowLoadingView)
                    dismissProgress();
                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

                    if (result.getData() != null && result.getData().length > 0) {

                    }
                    for (ProductResponseModel item : result.getData()) {
                        listProductResponseModels.add(item);
                    }
                    view.setDataListItem(listProductResponseModels);
                } else {
                    view.setDataListItem(null);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                if (isShowLoadingView)
                    dismissProgress();
                view.setDataListItem(null);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                if (isShowLoadingView)
                    dismissProgress();
                view.setDataListItem(null);
            }
        });
    }

    @Override
    public void onRequestGetListProductPurchase() {
        requestGetListProductPurchase();
    }

    public void requestGetListProductPurchase() {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showToast(R.string.error_connect_internet);
            view.setDataListItem(null);
            return;
        }
        showProgress();

        RequestGetListProduct.ApiParams params = new RequestGetListProduct.ApiParams();

        AppProvider.getApiManagement().call(RequestGetListProduct.class, params, new ApiRequest.ApiCallback<ListProductResponseModel>() {
            @Override
            public void onSuccess(ListProductResponseModel result) {
                dismissProgress();
                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
                    view.setDataListItemPurchase(result.getData());
                } else {
                    view.setDataListItemPurchase(null);
                }

            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                view.setDataListItemPurchase(null);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                view.setDataListItemPurchase(null);
            }
        });
    }

    @Override
    public void setChangeViewMode(String mode) {
        this.viewMode = mode;
    }

    @Override
    protected FragmentCheckingOrderViewInterface getViewInstance() {
        return new FragmentCheckingOrderView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    public String getViewMode() {
        return viewMode;
    }
}
