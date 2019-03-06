package qtc.project.aza.ui.views.activity.base_main_activity;

import android.view.View;
import android.widget.FrameLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.aza.R;

/**
 * Created by laixuantam on 7/6/17.
 */

public class BaseMainActivityView extends BaseView<BaseMainActivityView.UIContainer> implements BaseMainActivityViewInterface {
    private BaseMainActivityViewCallback callback;

    @Override
    public void init(BaseMainActivityViewCallback callback) {
        this.callback = callback;

    }

    @Override
    public void showToolBar() {
        setVisible(ui.layoutToolBar);
    }

    @Override
    public void hideToolBar() {
        setGone(ui.layoutToolBar);
    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new BaseMainActivityView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_base_container_and_toolbar;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.container)
        public FrameLayout container;

        @UiElement(R.id.layoutToolBar)
        public View layoutToolBar;


    }
}
