package qtc.project.aza.fragment;

import android.text.TextUtils;


import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.aza.R;
import qtc.project.aza.activity.HomeActivity;
import qtc.project.aza.api.login.LoginRequest;
import qtc.project.aza.dependency.AppProvider;
import qtc.project.aza.model.BaseResponseModel;
import qtc.project.aza.ui.views.fragment.fragment_login.FragmentLoginView;
import qtc.project.aza.ui.views.fragment.fragment_login.FragmentLoginViewCallback;
import qtc.project.aza.ui.views.fragment.fragment_login.FragmentLoginViewInterface;

public class FragmentLogin extends BaseFragment<FragmentLoginViewInterface, BaseParameters> implements FragmentLoginViewCallback {

    private HomeActivity activity;

    @Override
    protected void initialize() {
        view.init(this);
        activity = (HomeActivity) getActivity();
    }

    @Override
    protected FragmentLoginViewInterface getViewInstance() {
        return new FragmentLoginView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }


    @Override
    public void onClickLogin(String email, String password) {
        requestLogin(email, password);
    }

    private void requestLogin(String email, String password) {

        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showToast(R.string.error_connect_internet);
            return;
        }

        showProgress(getString(R.string.loading));

        LoginRequest.ApiParams params = new LoginRequest.ApiParams();
        params.username = email;

        params.password = password;
        AppProvider.getApiManagement().call(LoginRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
            @Override
            public void onSuccess(BaseResponseModel result) {

                dismissProgress();

                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

                    if (result.getLevel().equalsIgnoreCase("3")) {
                        //nhân viên mua hàng

                        AppProvider.getPreferences().saveLoginType("3");
                        if (activity != null) {
                            activity.changeToFragmentListProduct();

                        }
                    } else if (result.getLevel().equalsIgnoreCase("2")) {
                        //nhân viên kiểm tra
                        AppProvider.getPreferences().saveLoginType("2");
                        if (activity != null) {
                            activity.changeToFragmentCheckingOrder();

                        }
                    }

                } else {
                    showToast(R.string.login_error);
                }

            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                showToast(R.string.login_error);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                showToast(R.string.login_error);
            }
        });

    }

}
