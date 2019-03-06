package qtc.project.aza.fragment;

import android.text.TextUtils;
import android.view.View;

import java.util.concurrent.TimeoutException;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.popupmenu.ActionItem;
import b.laixuantam.myaarlibrary.widgets.popupmenu.MyCustomPopupMenu;
import qtc.project.aza.R;
import qtc.project.aza.activity.HomeActivity;
import qtc.project.aza.api.check_order.RequestCheckOrder;
import qtc.project.aza.api.get_list_product.RequestGetListProduct;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.model.BaseResponseModel;
import qtc.project.aza.model.ListProductResponseModel;
import qtc.project.aza.model.ProductResponseModel;
import qtc.project.aza.ui.views.fragment.fragment_list_item.FragmentListItemView;
import qtc.project.aza.ui.views.fragment.fragment_list_item.FragmentListItemViewCallback;
import qtc.project.aza.ui.views.fragment.fragment_list_item.FragmentListItemViewInterface;

public class FragmentListProduct extends BaseFragment<FragmentListItemViewInterface, BaseParameters> implements FragmentListItemViewCallback {

    HomeActivity activity;

    ProductResponseModel itemSelected;

    int positionItemSelected = -1;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(this);
        //request get lít product
        requestGetListProduct();

        setUpPopupMenu();
    }

    public void requestGetListProduct() {
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
    protected FragmentListItemViewInterface getViewInstance() {
        return new FragmentListItemView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onItemProductSelected(ProductResponseModel item, View v, int pos) {
//        itemSelected = item;
//        positionItemSelected = pos;
//        quickAction.show(v);
        showPopupMenuItem(item, v, pos);
    }

    private void showPopupMenuItem(ProductResponseModel item, View v, int pos) {
        MyCustomPopupMenu quickAction = new MyCustomPopupMenu(getContext());
        if (item.getCheck_box().equalsIgnoreCase("0")) {
            ActionItem check = new ActionItem(1, "Check đã mua đợt 1", null);
            ActionItem showDetail = new ActionItem(2, getString(R.string.title_select_detail_info), null);
            quickAction.addActionItem(check);
            quickAction.addActionItem(showDetail);
        } else if (item.getCheck_box().equalsIgnoreCase("1")) {
            ActionItem check = new ActionItem(1, "Check đã mua đợt 2", null);
            ActionItem showDetail = new ActionItem(2, getString(R.string.title_select_detail_info), null);
            quickAction.addActionItem(check);
            quickAction.addActionItem(showDetail);
        } else {
            ActionItem showDetail = new ActionItem(2, getString(R.string.title_select_detail_info), null);
            quickAction.addActionItem(showDetail);
        }


        quickAction.setOnActionItemClickListener(new MyCustomPopupMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(MyCustomPopupMenu source, int pos, int actionId) {
                switch (actionId) {
                    case 1:
                        if (item != null)
                            requestUpdateCheckProduct(item.getId_order());
                        break;

                    case 2:
                        if (activity != null && item != null) {
                            activity.changeToFragmentProductDetail(item);
                        }
                        break;
                }
            }
        });

        quickAction.show(v);

    }

    private MyCustomPopupMenu quickAction;

    private void setUpPopupMenu() {
        ActionItem check = new ActionItem(1, getString(R.string.title_select_check), null);
        ActionItem showDetail = new ActionItem(2, getString(R.string.title_select_detail_info), null);
        quickAction = new MyCustomPopupMenu(getContext());
        quickAction.addActionItem(check);
        quickAction.addActionItem(showDetail);

        quickAction.setOnActionItemClickListener(new MyCustomPopupMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(MyCustomPopupMenu source, int pos, int actionId) {
                switch (actionId) {
                    case 1:
                        if (itemSelected != null)
                            requestUpdateCheckProduct(itemSelected.getId_order());

                        break;

                    case 2:
                        if (activity != null && itemSelected != null) {
                            activity.changeToFragmentProductDetail(itemSelected);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onClickConfirm() {
        requestUpdateCheckProduct("");
    }

    private void requestUpdateCheckProduct(String id_order) {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showToast(R.string.error_connect_internet);
            return;
        }

        showProgress();
        RequestCheckOrder.ApiParams params = new RequestCheckOrder.ApiParams();
        if (TextUtils.isEmpty(id_order))
            params.detect = "Update_check_box_all";

        else {
            params.detect = "Update_check_box";
            params.id_order = id_order;
        }

        AppProvider.getApiManagement().call(RequestCheckOrder.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
            @Override
            public void onSuccess(BaseResponseModel result) {
                dismissProgress();
                showToast(R.string.update_success);
                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
                    requestGetListProduct();
//                    if (TextUtils.isEmpty(id_order)) {
//                        view.setDataListItem(null);
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                requestGetListProduct();
//                            }
//                        }, 1000);
//                    } else {
//                        //truong hop update 1 item
//
//                        view.updateStatusItem(positionItemSelected);
//                    }


                }

            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                showToast(R.string.update_failed);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                showToast(R.string.update_failed);
            }
        });
    }
}
