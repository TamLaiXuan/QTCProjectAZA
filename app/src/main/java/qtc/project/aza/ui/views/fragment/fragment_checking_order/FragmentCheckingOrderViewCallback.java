package qtc.project.aza.ui.views.fragment.fragment_checking_order;

import android.view.View;

import qtc.project.aza.model.ProductResponseModel;

public interface FragmentCheckingOrderViewCallback {
    void onItemSelected(ProductResponseModel item, View view, int pos);
}
