package qtc.project.aza.ui.views.fragment.fragment_product_detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.aza.model.ProductResponseModel;

public interface FragmentProductDetailViewInterface extends BaseViewInterface {

    void init(FragmentProductDetailViewCallback callback);

    void setDataProductItem(ProductResponseModel item, String type);

    void UpdateProduct(String quantityPurchase1, String quantityPurchase2, String pricePurchase, String unit);

}
