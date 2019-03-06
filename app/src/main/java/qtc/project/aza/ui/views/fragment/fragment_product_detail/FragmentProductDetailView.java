package qtc.project.aza.ui.views.fragment.fragment_product_detail;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.NumericFormater;
import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import qtc.project.aza.R;
import qtc.project.aza.helper.CurrencyUltils;
import qtc.project.aza.model.ProductResponseModel;

public class FragmentProductDetailView extends BaseView<FragmentProductDetailView.UIContainer> implements FragmentProductDetailViewInterface {
    private ProductResponseModel itemProduct;

    @Override
    public void init(FragmentProductDetailViewCallback callback) {

        ui.btnSubmitUpdateProductDetail.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                if (ui.edtUpdateQuantityPurchase2.isEnabled()) {

                    if (TextUtils.isEmpty(ui.edtUpdateQuantityPurchase1.getText()) &&
                            TextUtils.isEmpty(ui.edtUpdateQuantityPurchase2.getText())
                            && TextUtils.isEmpty(ui.edtUpdatePricePurchase.getText())) {

                        Toast.makeText(getContext(), "Bạn chưa nhập thông tin cập nhật.", Toast.LENGTH_SHORT).show();
                    } else {

                        if (itemProduct != null) {
                            String valueQuantityPurchase1 = "";
                            String valueQuantityPurchase2 = "";
                            String valuePricePurchase = "";

                            if (!TextUtils.isEmpty(ui.edtUpdateQuantityPurchase1.getText())) {
                                valueQuantityPurchase1 = ui.edtUpdateQuantityPurchase1.getText().toString();
                            }

                            if (!TextUtils.isEmpty(ui.edtUpdateQuantityPurchase2.getText())) {
                                valueQuantityPurchase2 = ui.edtUpdateQuantityPurchase2.getText().toString();
                            }

                            if (!TextUtils.isEmpty(ui.edtUpdatePricePurchase.getText())) {
                                valuePricePurchase = ui.edtUpdatePricePurchase.getText().toString();
                            }

                            callback.onUpdateProductQuantity(itemProduct.getId_order(), valueQuantityPurchase1, valueQuantityPurchase2, valuePricePurchase, itemProduct.getUnit_name());
                        }

                    }
                } else {
                    Toast.makeText(getContext(), "Sản phẩm đã mua.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ui.edtUpdateQuantityPurchase1.setEnabled(true);
        ui.edtUpdateQuantityPurchase2.setEnabled(true);
        ui.edtUpdatePricePurchase.setEnabled(true);

        ui.layoutDisableEditUpdateQuantityPurchase1.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                if (itemProduct != null) {
                    if (itemProduct.getCheck_box().equalsIgnoreCase("1")) {
                        Toast.makeText(getContext(), "Sản phẩm đợt 1 đã được mua.", Toast.LENGTH_SHORT).show();
                    }else if (itemProduct.getCheck_box().equalsIgnoreCase("2")){
                        Toast.makeText(getContext(), "Sản phẩm đã mua hoàn tất.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ui.layoutDisableEditUpdateQuantityPurchase2.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                if (itemProduct != null) {
                    if (itemProduct.getCheck_box().equalsIgnoreCase("2")) {
                        Toast.makeText(getContext(), "Sản phẩm đã mua hoàn tất.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ui.layoutDisableEditUpdatePricePurchase.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                if (itemProduct != null) {
                    if (itemProduct.getCheck_box().equalsIgnoreCase("2")) {
                        Toast.makeText(getContext(), "Sản phẩm đã mua hoàn tất.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public void setDataProductItem(ProductResponseModel item) {
        if (item != null) {
            itemProduct = item;
            ui.tvProductTitle.setText(item.getName_product());
            String price = CurrencyUltils.getStringPrice(Long.valueOf(item.getPrice()), CurrencyUltils.LONG_PRICE);

            if (!TextUtils.isEmpty(item.getPrice_purchased()) && Integer.valueOf(item.getPrice_purchased()) > 0) {
                price = CurrencyUltils.getStringPrice(Long.valueOf(item.getPrice_purchased()), CurrencyUltils.LONG_PRICE);
            }
            ui.tvPricePurchase.setText(price + "/" + item.getUnit_name());

            ui.tvQuantityPurchase1.setText(item.getQuantity_purchased_1() + " " + item.getUnit_name());
            ui.tvQuantityPurchase2.setText(item.getQuantity_purchased_2() + " " + item.getUnit_name());

            if (item.getCheck_box().equalsIgnoreCase("2")) {
                ui.edtUpdateQuantityPurchase1.setEnabled(false);
                ui.edtUpdateQuantityPurchase2.setEnabled(false);
                ui.edtUpdatePricePurchase.setEnabled(false);

                ui.layoutDisableEditUpdateQuantityPurchase1.setVisibility(View.VISIBLE);
                ui.layoutDisableEditUpdateQuantityPurchase2.setVisibility(View.VISIBLE);
                ui.layoutDisableEditUpdatePricePurchase.setVisibility(View.VISIBLE);

            } else if (item.getCheck_box().equalsIgnoreCase("1")) {
                ui.edtUpdateQuantityPurchase1.setEnabled(false);
                ui.edtUpdateQuantityPurchase2.setEnabled(true);
                ui.edtUpdatePricePurchase.setEnabled(true);

                ui.layoutDisableEditUpdateQuantityPurchase1.setVisibility(View.VISIBLE);
                ui.layoutDisableEditUpdateQuantityPurchase2.setVisibility(View.GONE);
                ui.layoutDisableEditUpdatePricePurchase.setVisibility(View.GONE);

            } else {
                ui.edtUpdateQuantityPurchase1.setEnabled(true);
                ui.edtUpdateQuantityPurchase2.setEnabled(true);
                ui.edtUpdatePricePurchase.setEnabled(true);
                ui.layoutDisableEditUpdateQuantityPurchase1.setVisibility(View.GONE);
                ui.layoutDisableEditUpdateQuantityPurchase2.setVisibility(View.GONE);
                ui.layoutDisableEditUpdatePricePurchase.setVisibility(View.GONE);
            }
            ui.tvProductMaNCU.setText(item.getCode_product());
            ui.edtUpdateQuantityPurchase1.setText("");
            ui.edtUpdateQuantityPurchase2.setText("");
            ui.edtUpdatePricePurchase.setText("");
        }
    }

    @Override
    public void UpdateProduct(String quantityPurchase1, String quantityPurchase2, String pricePurchase, String unit) {
        if (!TextUtils.isEmpty(quantityPurchase1)) {
            ui.tvQuantityPurchase1.setText(quantityPurchase1 + " " + unit);
            ui.edtUpdateQuantityPurchase1.setText("");
        }
        if (!TextUtils.isEmpty(quantityPurchase2)) {
            ui.tvQuantityPurchase2.setText(quantityPurchase2 + " " + unit);
            ui.edtUpdateQuantityPurchase2.setText("");
        }

        if (!TextUtils.isEmpty(pricePurchase) && Integer.valueOf(pricePurchase) > 0) {
            String price = CurrencyUltils.getStringPrice(Long.valueOf(pricePurchase), CurrencyUltils.LONG_PRICE);
            ui.tvPricePurchase.setText(price);
            ui.edtUpdatePricePurchase.setText("");
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_product_detail;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.tvProductTitle)
        public TextView tvProductTitle;

        @UiElement(R.id.tvQuantityPurchase1)
        public TextView tvQuantityPurchase1;

        @UiElement(R.id.tvQuantityPurchase2)
        public TextView tvQuantityPurchase2;

        @UiElement(R.id.tvPricePurchase)
        public TextView tvPricePurchase;

        @UiElement(R.id.tvProductMaNCU)
        public TextView tvProductMaNCU;

        @UiElement(R.id.edtUpdateQuantityPurchase1)
        public EditText edtUpdateQuantityPurchase1;

        @UiElement(R.id.layoutDisableEditUpdateQuantityPurchase1)
        public View layoutDisableEditUpdateQuantityPurchase1;

        @UiElement(R.id.edtUpdateQuantityPurchase2)
        public EditText edtUpdateQuantityPurchase2;

        @UiElement(R.id.layoutDisableEditUpdateQuantityPurchase2)
        public View layoutDisableEditUpdateQuantityPurchase2;

        @UiElement(R.id.edtUpdatePricePurchase)
        public EditText edtUpdatePricePurchase;

        @UiElement(R.id.layoutDisableEditUpdatePricePurchase)
        public View layoutDisableEditUpdatePricePurchase;


        @UiElement(R.id.btnSubmitUpdateProductDetail)
        public View btnSubmitUpdateProductDetail;
    }
}
