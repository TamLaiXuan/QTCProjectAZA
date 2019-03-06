package qtc.project.aza.ui.views.fragment.fragment_checking_order;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.aza.R;
import qtc.project.aza.adapter.ListCheckingOrderAdapter;
import qtc.project.aza.model.ProductResponseModel;

public class FragmentCheckingOrderView extends BaseView<FragmentCheckingOrderView.UIContainer> implements FragmentCheckingOrderViewInterface {

    private ListCheckingOrderAdapter adapter;

    @Override
    public void init(FragmentCheckingOrderViewCallback callback) {
        ui.edit_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable keyword) {
                if (adapter != null) {
                    adapter.getFilter().filter(keyword.toString());
                }
            }
        });
    }

    @Override
    public void showEmptyListProduct() {
        setVisible(ui.layoutEmptyListProduct);
    }

    @Override
    public void hideEmptyListProduct() {
        setGone(ui.layoutEmptyListProduct);
    }

    @Override
    public void setDataListItem(ProductResponseModel[] dataListItem) {
        if (dataListItem != null && dataListItem.length > 0) {
            hideEmptyListProduct();
            List<ProductResponseModel> listProduct = new ArrayList<>();

            for (ProductResponseModel item : dataListItem) {
                listProduct.add(item);
            }

            adapter = new ListCheckingOrderAdapter(getContext(), listProduct, new ListCheckingOrderAdapter.ListProductAdapterListener() {
                @Override
                public void onItemProductSelected(ProductResponseModel item, View view, int pos) {

                }
            });
            ui.lvListItem.setAdapter(adapter);

        } else {
            showEmptyListProduct();
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCheckingOrderView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_checking_order;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.lvListItem)
        public ListView lvListItem;

        @UiElement(R.id.layoutEmptyListProduct)
        public View layoutEmptyListProduct;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

    }
}
