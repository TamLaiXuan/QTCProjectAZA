package qtc.project.aza.ui.views.fragment.fragment_checking_order;

import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.aza.model.ProductResponseModel;

public interface FragmentCheckingOrderViewInterface extends BaseViewInterface {

    void init(FragmentCheckingOrderViewCallback callback);

    void showEmptyListProduct();

    void hideEmptyListProduct();

//    void setDataListItem(ProductResponseModel[] dataListItem);


    void setDataListItem(List<ProductResponseModel> listProductResponseModels);
}
