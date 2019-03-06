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

public class ListProductAdapter extends BaseListAdapter<ProductResponseModel, ListProductAdapter.ViewHolder> {

    public interface ListProductAdapterListener {
        void onItemProductSelected(ProductResponseModel item, View v, int pos);
    }

    private ArrayList<ProductResponseModel> listProduct;
    private ProductFilter filter;

    private ListProductAdapterListener listener;

    public ListProductAdapter(Context context, List<ProductResponseModel> list, ListProductAdapterListener listener) {
        super(context, R.layout.row_item_product, list);
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

        viewHolder.tvProductSTT.setText("" + (position + 1) + ".");
        viewHolder.tvProductName.setText(item.getName_product());

        if (!TextUtils.isEmpty(item.getQuantity()) && Integer.valueOf(item.getQuantity()) > 0) {
            viewHolder.tvProductNumberCount.setText(item.getQuantity() + item.getUnit_name());
        } else viewHolder.tvProductNumberCount.setText(item.getQuantity() + item.getUnit_name());

        if (item.getCheck_box().equalsIgnoreCase("1")) {
            viewHolder.rLayoutItem.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_shape_item_check_1_radius_5));
        } else if (item.getCheck_box().equalsIgnoreCase("2")) {
            viewHolder.rLayoutItem.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_shape_item_selected_radius_5));
        } else {
            viewHolder.rLayoutItem.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_shape_white_radius_5));
        }

        if (!TextUtils.isEmpty(filterString)) {

            if (item.getName_product().toLowerCase().contains(filterString))
                AppUtils.hightlight(viewHolder.tvProductName, filterString);

        }
        viewHolder.rLayoutItem.setOnTouchListener(new ScaleTouchListener(conf) {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemProductSelected(item, v, position);
                }
            }
        });
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

        @UiElement(R.id.tvProductSTT)
        public TextView tvProductSTT;

        @UiElement(R.id.tvProductName)
        public TextView tvProductName;

        @UiElement(R.id.tvProductNumberCount)
        public TextView tvProductNumberCount;


    }
}
