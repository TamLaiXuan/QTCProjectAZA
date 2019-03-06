package qtc.project.aza.ui.views.fragment.fragment_list_item;

import android.view.View;

import qtc.project.aza.model.ProductResponseModel;

/**
 * Created by laixuantam on 4/23/18.
 */

public interface FragmentListItemViewCallback {
    void onItemProductSelected(ProductResponseModel item, View v, int pos);

    void onClickConfirm();
}
