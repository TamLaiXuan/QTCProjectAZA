package lxt.project.aza.ui.views.fragment.fragment_list_item;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;

/**
 * Created by laixuantam on 4/23/18.
 */

public interface FragmentListItemViewInterface extends BaseViewInterface {

    void init(FragmentListItemViewCallback callback);

    void filterDataSearch(String key);

}
