package qtc.project.aza.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseListAdapter;
import b.laixuantam.myaarlibrary.base.BaseViewHolder;
import b.laixuantam.myaarlibrary.helper.AccentRemove;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import qtc.project.aza.R;
import qtc.project.aza.model.ProductResponseModel;

public class ListCheckingOrderAdapter extends BaseListAdapter<ProductResponseModel, ListCheckingOrderAdapter.ViewHolder> {

    public interface ListProductAdapterListener {
        void onItemProductSelected(ProductResponseModel item, View v, int pos);
    }

    private ArrayList<ProductResponseModel> listProduct;
    private ProductFilter filter;

    private ListProductAdapterListener listener;

    public ListCheckingOrderAdapter(Context context, List<ProductResponseModel> list, ListProductAdapterListener listener) {
        super(context, R.layout.row_item_checking_order, list);
        this.listener = listener;
        listProduct = new ArrayList<>();
        listProduct.addAll(list);
        filter = new ProductFilter();
    }

    @NonNull
    @Override
    public ProductFilter getFilter() {
        return filter;
    }

    @Override
    protected void fillView(ViewHolder viewHolder, ProductResponseModel item, int position, View rowView) {

        ScaleTouchListener.Config conf = new ScaleTouchListener.Config(100, 1f, 0.5f);

        viewHolder.tvProductName2.setText(item.getName_product());
        viewHolder.tvQuantityPurchase1.setText(item.getQuantity_purchased_1() + item.getUnit_name());
        viewHolder.tvQuantityPurchase2.setText(item.getQuantity_purchased_2() + item.getUnit_name());
        viewHolder.tvTotalPurchase.setText(item.getTotal_purchase() + item.getUnit_name());


        if (!TextUtils.isEmpty(filterString)) {

            if (item.getName_product().toLowerCase().contains(filterString))
                AppUtils.hightlight(viewHolder.tvProductName2, filterString);

        }

        if (!item.getQuantity().equalsIgnoreCase(item.getTotal_purchase())) {
            viewHolder.tvProductName2.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            viewHolder.tvQuantityPurchase1.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            viewHolder.tvQuantityPurchase2.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            viewHolder.tvTotalPurchase.setTextColor(ContextCompat.getColor(getContext(), R.color.red));


        } else {
            viewHolder.tvProductName2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            viewHolder.tvQuantityPurchase1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            viewHolder.tvQuantityPurchase2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            viewHolder.tvTotalPurchase.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        }

    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder();
    }


    private String filterString;

    public class ProductFilter extends Filter {
        public ProductFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (!TextUtils.isEmpty(constraint)) {
                filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (listProduct != null && listProduct.size() > 0) {
                    int count = listProduct.size();
                    List<ProductResponseModel> tempItems = new ArrayList<ProductResponseModel>();

                    // search exactly
                    for (int i = 0; i < count; i++) {
                        String name = listProduct.get(i).getName_product().toLowerCase();
                        if (name.contains(filterString)) {
                            tempItems.add(listProduct.get(i));
                        }
                    }

                    // search for no accent if no exactly result
                    filterString = AccentRemove.removeAccent(filterString);
                    if (tempItems.size() == 0) {
                        for (int i = 0; i < count; i++) {
                            String name = AccentRemove.removeAccent(listProduct.get(i).getName_product().toLowerCase());
                            if (name.contains(filterString)) {
                                tempItems.add(listProduct.get(i));
                            }
                        }
                    }
                    results.values = tempItems;
                    results.count = tempItems.size();
                    return results;
                } else {
                    return null;
                }
            } else {
                filterString = "";
                return null;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            if (results != null) {
                List<ProductResponseModel> listProductResult = (List<ProductResponseModel>) results.values;
                if (listProductResult != null && listProductResult.size() > 0) {
                    list.addAll(listProductResult);
                }
            } else {
                list.addAll(listProduct);
            }
            notifyDataSetInvalidated();
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @UiElement(R.id.rLayoutItem)
        public View rLayoutItem;

        @UiElement(R.id.tvSTTLable)
        public TextView tvSTTLable;

        @UiElement(R.id.tvSTT)
        public TextView tvSTT;

        @UiElement(R.id.tvProductNameLable)
        public TextView tvProductNameLable;

        @UiElement(R.id.tvProductName)
        public TextView tvProductName;


        @UiElement(R.id.tvSLCanMuaLable)
        public TextView tvSLCanMuaLable;

        @UiElement(R.id.tvSLCanMua)
        public TextView tvSLCanMua;

        @UiElement(R.id.tvSLDaMuaLable)
        public TextView tvSLDaMuaLable;

        @UiElement(R.id.tvSLDaMua)
        public TextView tvSLDaMua;


        @UiElement(R.id.tvProductName2)
        public TextView tvProductName2;

        @UiElement(R.id.tvQuantityPurchase1)
        public TextView tvQuantityPurchase1;

        @UiElement(R.id.tvQuantityPurchase2)
        public TextView tvQuantityPurchase2;

        @UiElement(R.id.tvTotalPurchase)
        public TextView tvTotalPurchase;

    }
}
