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
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import qtc.project.aza.R;
import qtc.project.aza.adapter.ListCheckingOrderAdapter;
import qtc.project.aza.adapter.ListProductAdapter;
import qtc.project.aza.model.ProductResponseModel;

public class FragmentCheckingOrderView extends BaseView<FragmentCheckingOrderView.UIContainer> implements FragmentCheckingOrderViewInterface {

    private ListCheckingOrderAdapter adapter;
    private ListProductAdapter listProductPurchaseAdapter;

    boolean isShowListProductChecking = true;
    private FragmentCheckingOrderViewCallback callback;

    @Override
    public void init(FragmentCheckingOrderViewCallback callback) {
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

                if (isShowListProductChecking) {
                    if (adapter != null) {
                        adapter.getFilter().filter(keyword.toString());
                    }
                } else {
                    if (listProductPurchaseAdapter != null) {
                        listProductPurchaseAdapter.getFilter().filter(keyword.toString());
                    }
                }
            }
        });


        ui.btnListProductChecking.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                isShowListProductChecking = true;
                ui.edit_filter.setText("");
                AppUtils.hideKeyBoard(getView());
                setVisible(ui.viewIndicatorListProductChecking);
                setGone(ui.viewIndicatorListProductPurchase);

                setVisible(ui.lvListItem);
                setGone(ui.lvListPurchaseItem);

                callback.setChangeViewMode("checking");

                if (adapter != null) {
                    if (adapter.getCount() == 0) {
                        callback.onRequestGetListProductChecking();
                    }
                } else {
                    callback.onRequestGetListProductChecking();
                }
            }
        });

        ui.btnListProductPurchase.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                isShowListProductChecking = false;
                ui.edit_filter.setText("");
                AppUtils.hideKeyBoard(getView());
                setGone(ui.viewIndicatorListProductChecking);
                setVisible(ui.viewIndicatorListProductPurchase);

                setGone(ui.lvListItem);
                setVisible(ui.lvListPurchaseItem);
                callback.setChangeViewMode("purchase");

                if (listProductPurchaseAdapter != null) {
                    if (listProductPurchaseAdapter.getCount() == 0) {
                        callback.onRequestGetListProductPurchase();
                    }
                } else {
                    callback.onRequestGetListProductPurchase();
                }

            }
        });
    }

    @Override
    public void showEmptyListProduct() {
        setVisible(ui.layoutEmptyListProduct);
        setGone(ui.lvListItem);
        setGone(ui.lvListPurchaseItem);
    }

    @Override
    public void hideEmptyListProduct() {
        setGone(ui.layoutEmptyListProduct);
    }

    @Override
    public void setDataListItemPurchase(ProductResponseModel[] dataListItem) {
        setVisible(ui.lvListPurchaseItem);
        setGone(ui.lvListItem);
        if (dataListItem != null && dataListItem.length > 0) {
            hideEmptyListProduct();
            List<ProductResponseModel> listProduct = new ArrayList<>();

            for (ProductResponseModel item : dataListItem) {
                listProduct.add(item);
            }

            listProductPurchaseAdapter = new ListProductAdapter(getContext(), listProduct, null);
            ui.lvListPurchaseItem.setAdapter(listProductPurchaseAdapter);

        } else {
            showEmptyListProduct();
        }
    }

    @Override
    public void setDataListItem(List<ProductResponseModel> list) {
        setGone(ui.lvListPurchaseItem);
        setVisible(ui.lvListItem);
        if (list == null || list.size() == 0) {
            showEmptyListProduct();
            return;
        }

        List<ProductResponseModel> listProductResponseModels = new ArrayList<>();

        listProductResponseModels.addAll(list);

        adapter = new ListCheckingOrderAdapter(getContext(), listProductResponseModels, new ListCheckingOrderAdapter.ListProductAdapterListener() {
            @Override
            public void onItemProductSelected(ProductResponseModel item, View view, int pos) {
//                Toast.makeText(getContext(), "ClickItem " + item.getName_product(), Toast.LENGTH_SHORT).show();
                callback.onItemSelected(item, view, pos);
            }
        });
        ui.lvListItem.setAdapter(adapter);
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

        @UiElement(R.id.btnListProductChecking)
        public View btnListProductChecking;

        @UiElement(R.id.viewIndicatorListProductChecking)
        public View viewIndicatorListProductChecking;

        @UiElement(R.id.btnListProductPurchase)
        public View btnListProductPurchase;

        @UiElement(R.id.viewIndicatorListProductPurchase)
        public View viewIndicatorListProductPurchase;

        @UiElement(R.id.lvListPurchaseItem)
        public ListView lvListPurchaseItem;

    }
}
