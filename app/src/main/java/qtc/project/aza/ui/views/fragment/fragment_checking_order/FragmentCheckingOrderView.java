package qtc.project.aza.ui.views.fragment.fragment_checking_order;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.aza.R;
import qtc.project.aza.adapter.ListCheckingOrderAdapter;
import qtc.project.aza.model.ProductResponseModel;

public class FragmentCheckingOrderView extends BaseView<FragmentCheckingOrderView.UIContainer> implements FragmentCheckingOrderViewInterface {

    private ListCheckingOrderAdapter adapter;

    private List<ProductResponseModel> listProductResponseModels = new ArrayList<>();


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

        adapter = new ListCheckingOrderAdapter(getContext(), listProductResponseModels, new ListCheckingOrderAdapter.ListProductAdapterListener() {
            @Override
            public void onItemProductSelected(ProductResponseModel item, View view, int pos) {
//                Toast.makeText(getContext(), "ClickItem " + item.getName_product(), Toast.LENGTH_SHORT).show();
                callback.onItemSelected(item,view,pos);
            }
        });
        ui.lvListItem.setAdapter(adapter);
    }

    @Override
    public void showEmptyListProduct() {
        setVisible(ui.layoutEmptyListProduct);
    }

    @Override
    public void hideEmptyListProduct() {
        setGone(ui.layoutEmptyListProduct);
    }

//    @Override
//    public void setDataListItem(ProductResponseModel[] dataListItem) {
//        if (dataListItem != null && dataListItem.length > 0) {
//            hideEmptyListProduct();
//            List<ProductResponseModel> listProduct = new ArrayList<>();
//
//            for (ProductResponseModel item : dataListItem) {
//                listProduct.add(item);
//            }
//
//            adapter = new ListCheckingOrderAdapter(getContext(), listProduct, new ListCheckingOrderAdapter.ListProductAdapterListener() {
//                @Override
//                public void onItemProductSelected(ProductResponseModel item, View view, int pos) {
//                    Toast.makeText(getContext(), "ClickItem " + item.getName_product(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            ui.lvListItem.setAdapter(adapter);
//
//        } else {
//            showEmptyListProduct();
//        }
//    }

    @Override
    public void setDataListItem(List<ProductResponseModel> list) {
        if (list == null || list.size() == 0) {
            showEmptyListProduct();
            return;
        }

        listProductResponseModels.clear();
        listProductResponseModels.addAll(list);

        adapter.notifyDataSetChanged();
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
