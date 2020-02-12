package nu.toko.mitra.Model;

import java.util.ArrayList;
import java.util.List;

public class PurchaseModel {

    private int id;
    private int idproduct;
    private int price;
    private int status;
    private String productname;
    private String img;

    public PurchaseModel() {

    }

    public PurchaseModel(int price, int status, String productname, String img) {
        this.price = price;
        this.status = status;
        this.productname = productname;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public static List<PurchaseModel> DataPurc() {
        List<PurchaseModel> data = new ArrayList<>();
        data.add(new PurchaseModel(
                42,
                1,
                "Bellivera Women’s Faux Fur Jacket with 2 Side-Seam Pockets, The Coat with Hood and Bottom Ribbing",
                "img17.jpg"
        ));
        data.add(new PurchaseModel(
                43,
                2,
                "Global Blank Heavyweight Sherpa Lined Zip Up Fleece Hoodie Jacket Men",
                "img5.jpg"
        ));
        data.add(new PurchaseModel(
                60,
                4,
                "Flygo Men's Classic Sherpa Lined Full Zip Up Hoodies Sweatshirt Jacket Outwear",
                "img10.jpg"
        ));
        data.add(new PurchaseModel(
                45,
                5,
                "Pink Platinum Women's Faux Fur Bomber Jacket",
                "img24.jpg"
        ));

        return data;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
