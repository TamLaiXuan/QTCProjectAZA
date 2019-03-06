package qtc.project.aza.ui.views.fragment.fragment_list_item;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import qtc.project.aza.R;
import qtc.project.aza.adapter.ListProductAdapter;
import qtc.project.aza.model.ProductResponseModel;

/**
 * Created by laixuantam on 4/23/18.
 */

public class FragmentListItemView extends BaseView<FragmentListItemView.UIContainer> implements FragmentListItemViewInterface, View.OnClickListener {
    private FragmentListItemViewCallback callback;
    private ListProductAdapter adapter;

    @Override
    public void init(FragmentListItemViewCallback callback) {
        this.callback = callback;

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

        ui.btnConfirm.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                callback.onClickConfirm();
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
        }
    }

    @Override
    public void filterDataSearch(String keyword) {
        if (adapter != null) {
            adapter.getFilter().filter(keyword);
        }
    }

    @Override
    public void updateStatusItem(int positionItemSelected) {
        if (adapter != null) {

        }
    }

    @Override
    public void setDataListItem(ProductResponseModel[] dataListItem) {

        if (dataListItem != null && dataListItem.length > 0) {
            hideEmptyListProduct();
            List<ProductResponseModel> listProduct = new ArrayList<>();

            for (ProductResponseModel item : dataListItem) {
                listProduct.add(item);
            }

            adapter = new ListProductAdapter(getContext(), listProduct, new ListProductAdapter.ListProductAdapterListener() {
                @Override
                public void onItemProductSelected(ProductResponseModel item, View view, int pos) {
                    callback.onItemProductSelected(item, view, pos);
                }
            });
            ui.lvListItem.setAdapter(adapter);

        } else {
            showEmptyListProduct();
        }
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

        @UiElement(R.id.layoutEmptyListProduct)
        public View layoutEmptyListProduct;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.btnConfirm)
        public View btnConfirm;

    }
}
