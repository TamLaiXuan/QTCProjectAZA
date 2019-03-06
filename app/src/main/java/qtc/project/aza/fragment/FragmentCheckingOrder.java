package qtc.project.aza.fragment;

import android.text.TextUtils;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.aza.R;
import qtc.project.aza.api.checking_order.RequestCheckingOrder;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.model.ListProductResponseModel;
import qtc.project.aza.ui.views.fragment.fragment_checking_order.FragmentCheckingOrderView;
import qtc.project.aza.ui.views.fragment.fragment_checking_order.FragmentCheckingOrderViewCallback;
import qtc.project.aza.ui.views.fragment.fragment_checking_order.FragmentCheckingOrderViewInterface;

public class FragmentCheckingOrder extends BaseFragment<FragmentCheckingOrderViewInterface, BaseParameters> implements FragmentCheckingOrderViewCallback {

    @Override
    protected void initialize() {
        view.init(this);

        requestGetListCheckingOrder();
    }

    public void requestGetListCheckingOrder() {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showToast(R.string.error_connect_internet);
            view.setDataListItem(null);
            return;
        }
        showProgress();

        RequestCheckingOrder.ApiParams params = new RequestCheckingOrder.ApiParams();
        AppProvider.getApiManagement().call(RequestCheckingOrder.class, params, new ApiRequest.ApiCallback<ListProductResponseModel>() {
            @Override
            public void onSuccess(ListProductResponseModel result) {
                dismissProgress();
                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
                    view.setDataListItem(result.getData());
                } else {
                    view.setDataListItem(null);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                view.setDataListItem(null);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                view.setDataListItem(null);
            }
        });
    }


    @Override
    protected FragmentCheckingOrderViewInterface getViewInstance() {
        return new FragmentCheckingOrderView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }
}
