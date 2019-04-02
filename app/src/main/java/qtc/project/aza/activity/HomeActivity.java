package qtc.project.aza.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseFragmentActivity;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.helper.OnKeyboardVisibilityListener;
import b.laixuantam.myaarlibrary.widgets.dialog.AppDialog;
import b.laixuantam.myaarlibrary.widgets.dialog.ConfirmDialog;
import qtc.project.aza.R;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.fragment.FragmentCheckingOrder;
import qtc.project.aza.fragment.FragmentListProduct;
import qtc.project.aza.fragment.FragmentLogin;
import qtc.project.aza.fragment.FragmentProductDetail;
import qtc.project.aza.model.ProductResponseModel;
import qtc.project.aza.ui.views.action_bar.base_main_actionbar.BaseMainActionbarView;
import qtc.project.aza.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewCallback;
import qtc.project.aza.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewInterface;
import qtc.project.aza.ui.views.activity.base_main_activity.BaseMainActivityView;
import qtc.project.aza.ui.views.activity.base_main_activity.BaseMainActivityViewCallback;
import qtc.project.aza.ui.views.activity.base_main_activity.BaseMainActivityViewInterface;

public class HomeActivity extends BaseFragmentActivity<BaseMainActivityViewInterface, BaseMainActionbarViewInterface, BaseParameters> implements BaseMainActivityViewCallback, BaseMainActionbarViewCallback, OnKeyboardVisibilityListener {

    @Override
    protected BaseMainActivityViewInterface getViewInstance() {
        return new BaseMainActivityView();
    }

    @Override
    protected BaseMainActionbarViewInterface getActionbarInstance() {
        return new BaseMainActionbarView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.container;
    }

    @Override
    protected void setupActionbar(ViewGroup container) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.layoutToolBar);
        this.actionbar = getActionbarInstance();

        if ((toolbar != null) && (this.actionbar != null)) {
            setSupportActionBar(toolbar);

            View actionbarView = this.actionbar.inflate(getLayoutInflater(), container);
            toolbar.addView(actionbarView);
        }
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        view.init(this);
        actionbar.initialize("", this);
        actionbar.hideLayoutFilter();
        actionbar.hideButtonRightActionBar();
        actionbar.hideButtonLeftActionBar();
        actionbar.configBackgroundLayoutFilter(R.color.white);
        actionbar.hideActionBarIndicator();

//        actionbar.setTitle("AZA");
//        actionbar.configTitleColor(R.color.txt_primary);

        if (AppProvider.getPreferences().checkLoginStatus()) {

            String loginType = AppProvider.getPreferences().getLoginType();
            if (loginType.equalsIgnoreCase("2")) {
                //kiểm hàng
                changeToFragmentCheckingOrder();
            } else {
                //mua hàng
                changeToFragmentListProduct();
            }
        } else
            changeToFragmentLogin();
    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }

    @Override
    public void onFilterToggle(boolean showFilter) {

    }

    @Override
    public void onFiltering(String keyword) {

    }

    @Override
    public void onClickButtonLeftActionbar() {
        if (isShowListOrder) {

            BaseFragment fragment = getCurrentFragment();
            if (fragment instanceof FragmentListProduct) {
                ((FragmentListProduct) fragment).requestGetListProduct();
            } else if (fragment instanceof FragmentCheckingOrder) {
                ((FragmentCheckingOrder) fragment).resetListData();
                ((FragmentCheckingOrder) fragment).requestGetListCheckingOrder(true);
            }
        } else {

            checkFragment();
        }
    }

    public void checkFragment() {

        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BaseFragment frag = getCurrentFragment();

                    if (frag instanceof FragmentCheckingOrder) {
                        isShowListOrder = true;
                        isShowDetail = false;

                        actionbar.showButtonRightActionBar();
                        actionbar.showButtonLeftActionBar();
                        actionbar.configButtonLeftActionBar(R.drawable.ic_loop_black_24dp, R.color.txt_primary);
                        actionbar.setTitle("Kiểm hàng");
                        actionbar.showActionBarIndicator();
                        actionbar.configTitleColor(R.color.txt_primary);
                    } else {
                        isShowListOrder = true;
                        isShowDetail = false;
                        actionbar.showButtonRightActionBar();
                        actionbar.showButtonLeftActionBar();
                        actionbar.configButtonLeftActionBar(R.drawable.ic_loop_black_24dp, R.color.txt_primary);
                        actionbar.setTitle("Danh sách đặt hàng");
                        actionbar.showActionBarIndicator();
                        actionbar.configTitleColor(R.color.txt_primary);
                    }
                }
            }, 300);


        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClickButtonRightActionbar() {

        ConfirmDialog.showLogoutDialog(this, new ConfirmDialog.ConfirmDialogListener() {
            @Override
            public void onOk(AppDialog<?> f) {

                AppProvider.getPreferences().saveStatusLogin(false);
                changeToFragmentLogin();
            }

            @Override
            public void onCancel(AppDialog<?> f) {

            }
        });


    }

    //============================Main fuction===============================

    public void changeToFragmentLogin() {
        actionbar.configTitleColor(R.color.white);
        actionbar.hideButtonRightActionBar();
        actionbar.hideButtonLeftActionBar();
        actionbar.hideActionBarIndicator();
        addFragment(new FragmentLogin(), false, Animation.SLIDE_IN_OUT);

    }

    boolean isShowListOrder = false;
    boolean isShowDetail = false;


    public void changeToFragmentListProduct() {
        isShowListOrder = true;
        isShowDetail = false;
        actionbar.showButtonRightActionBar();
        actionbar.showButtonLeftActionBar();
        actionbar.configButtonLeftActionBar(R.drawable.ic_loop_black_24dp, R.color.txt_primary);
        actionbar.setTitle("Danh sách đặt hàng");
        actionbar.showActionBarIndicator();
        actionbar.configTitleColor(R.color.txt_primary);

        addFragment(new FragmentListProduct(), false, Animation.SLIDE_IN_OUT);
    }

    public void changeToFragmentProductDetail(ProductResponseModel item) {

        isShowListOrder = false;
        isShowDetail = true;
        String type = "";

        actionbar.hideButtonRightActionBar();
        actionbar.showButtonLeftActionBar();
        actionbar.configButtonLeftActionBar(R.drawable.ic_keyboard_backspace_black_24dp, R.color.txt_primary);
        actionbar.setTitle("Chi tiết sản phẩm");
        actionbar.showActionBarIndicator();
        actionbar.configTitleColor(R.color.txt_primary);

        BaseFragment baseFragment = getCurrentFragment();
        if (baseFragment instanceof FragmentListProduct) {
            type = "order";
        } else {
            type = "checking";
        }

        addFragment(FragmentProductDetail.newInstance(item, type), true, Animation.SLIDE_IN_OUT);

    }

    public void changeToFragmentCheckingOrder() {

        isShowListOrder = true;
        isShowDetail = false;

        actionbar.showButtonRightActionBar();
        actionbar.showButtonLeftActionBar();
        actionbar.configButtonLeftActionBar(R.drawable.ic_loop_black_24dp, R.color.txt_primary);
        actionbar.setTitle("Kiểm hàng");
        actionbar.showActionBarIndicator();
        actionbar.configTitleColor(R.color.txt_primary);

        addFragment(new FragmentCheckingOrder(), false, Animation.SLIDE_IN_OUT);
    }
}
