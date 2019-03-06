package qtc.project.aza.ui.views.fragment.fragment_list_item;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.aza.model.ListProductResponseModel;
import qtc.project.aza.model.ProductResponseModel;

/**
 * Created by laixuantam on 4/23/18.
 */

public interface FragmentListItemViewInterface extends BaseViewInterface {

    void init(FragmentListItemViewCallback callback);

    void filterDataSearch(String key);

    void setDataListItem(ProductResponseModel[] dataListItem);

    void showEmptyListProduct();

    void hideEmptyListProduct();

    void updateStatusItem(int positionItemSelected);
}
