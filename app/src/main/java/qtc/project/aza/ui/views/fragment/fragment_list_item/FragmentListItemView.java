package lxt.project.aza.ui.views.fragment.fragment_list_item;

import android.view.View;
import android.widget.ListView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import lxt.project.aza.R;

/**
 * Created by laixuantam on 4/23/18.
 */

public class FragmentListItemView extends BaseView<FragmentListItemView.UIContainer> implements FragmentListItemViewInterface, View.OnClickListener {
    private FragmentListItemViewCallback callback;

    @Override
    public void init(FragmentListItemViewCallback callback) {
        this.callback = callback;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
        }
    }

    @Override
    public void filterDataSearch(String keyword) {
//        if (contactAdapter != null) {
//            contactAdapter.getFilter().filter(keyword);
//        }
    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentListItemView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_list_item;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.lvListItem)
        public ListView lvListItem;

    }
}
