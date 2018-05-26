package faisal.com.bdcashquiz;

public class Item {
    private String idText;
    private String Balance;
    private String Name;
    private String img;

    public Item() {
    }

    public Item(String idText, String balance, String name, String img) {
        this.idText = idText;
        Balance = balance;
        Name = name;
        this.img = img;
    }

    public Item(String balance, String name, String img) {
        Balance = balance;
        Name = name;
        this.img = img;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Item{" +
                "idText='" + idText + '\'' +
                ", Balance='" + Balance + '\'' +
                ", Name='" + Name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
