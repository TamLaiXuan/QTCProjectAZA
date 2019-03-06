package qtc.project.aza.model;

public class ListProductResponseModel extends BaseResponseModel {
    private ProductResponseModel[] data;

    public ProductResponseModel[] getData() {
        return data;
    }

    public void setData(ProductResponseModel[] data) {
        this.data = data;
    }
}
