package qtc.project.aza.activity;

import b.laixuantam.myaarlibrary.base.BaseActivity;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.aza.R;
import qtc.project.aza.ui.views.action_bar.base_main_actionbar.BaseMainActionbarView;
import qtc.project.aza.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewCallback;
import qtc.project.aza.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewInterface;
import qtc.project.aza.ui.views.activity.base_main_activity.BaseMainActivityView;
import qtc.project.aza.ui.views.activity.base_main_activity.BaseMainActivityViewCallback;
import qtc.project.aza.ui.views.activity.base_main_activity.BaseMainActivityViewInterface;

public class MainActivity extends BaseActivity<BaseMainActivityViewInterface, BaseMainActionbarViewInterface, BaseParameters> implements BaseMainActivityViewCallback, BaseMainActionbarViewCallback {

    @Override
    protected void initialize() {
        view.init(this);
        actionbar.initialize("Main", this);
        actionbar.hideLayoutFilter();

        actionbar.showButtonRightActionBar();

        actionbar.configButtonLeftActionBar(R.drawable.ic_keyboard_arrow_left_black_24dp, R.color.blue_light);

        actionbar.configButtonRightActionBar(R.drawable.ic_check_black_24dp, R.color.blue_light);

        actionbar.configBackgroundLayoutFilter(R.color.white);

        actionbar.configBackgroundLayoutFilterContainer(R.drawable.border_shape_primary);

        actionbar.configButtonBackLayoutFilter(R.color.black);

        actionbar.configEdtSearchLayoutFilter(R.color.white, R.color.color_primary);

    }

//    @Override
//    protected void setupActionbar(ViewGroup container) {
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.layoutToolBar);
//        this.actionbar = getActionbarInstance();
//
//        if ((toolbar != null) && (this.actionbar != null)) {
//            setSupportActionBar(toolbar);
//
//            View actionbarView = this.actionbar.inflate(getLayoutInflater(), container);
//            toolbar.addView(actionbarView);
//        }
//    }

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
    public void onFilterToggle(boolean showFilter) {

    }

    @Override
    public void onFiltering(String keyword) {

    }

    @Override
    public void onClickButtonLeftActionbar() {

    }

    @Override
    public void onClickButtonRightActionbar() {
        actionbar.showLayoutFilter();

    }
}
