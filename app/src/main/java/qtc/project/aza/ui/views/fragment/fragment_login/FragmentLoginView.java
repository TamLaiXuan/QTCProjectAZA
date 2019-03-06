package qtc.project.aza.ui.views.fragment.fragment_login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.widgets.custom_edittext_mask_format.EditTextMaskFormatter;
import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import qtc.project.aza.R;

public class FragmentLoginView extends BaseView<FragmentLoginView.UiContainer> implements FragmentLoginViewInterface {

    private FragmentLoginViewCallback callback;

    @Override
    public void init(final FragmentLoginViewCallback callback) {

        this.callback = callback;

        ScaleTouchListener.Config conf = new ScaleTouchListener.Config(100, 1f, 0.5f);
        ui.btnLogin.setOnTouchListener(new ScaleTouchListener(conf) {
            @Override
            public void onClick(View view) {
                if (checkDataInput()) {

                    String userEmail = ui.edtUsername.getText().toString();
                    String password = ui.edtPassword.getText().toString();

                    callback.onClickLogin(userEmail, password);
                   

                }
            }
        });

        ui.edtUsername.addTextChangedListener(new EditTextMaskFormatter(EditTextMaskFormatter.MARK.NONE, ui.edtUsername));
        ui.edtPassword.addTextChangedListener(new EditTextMaskFormatter(EditTextMaskFormatter.MARK.NONE, ui.edtPassword));
    }

    private boolean checkDataInput() {
        if (!TextUtils.isEmpty(ui.edtUsername.getText()) && !TextUtils.isEmpty(ui.edtPassword.getText())) {
            return true;
        } else {
            AppUtils.hideKeyBoard(getView());
            if (TextUtils.isEmpty(ui.edtUsername.getText())) {
                ui.edtUsername.setError(getString(R.string.title_hint_username));
            } else {
                ui.edtPassword.setError(getString(R.string.title_hint_password));
            }
        }

        return false;

    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentLoginView.UiContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_login;
    }

    public static class UiContainer extends BaseUiContainer {

        @UiElement(R.id.btnLogin)
        public View btnLogin;

        @UiElement(R.id.edtUsername)
        public EditText edtUsername;

        @UiElement(R.id.edtPassword)
        public EditText edtPassword;

    }

}
