package net.smactechnology.medha;

public class gridItem {
    private String photUrl;
    private String nameText;
    private String balanceText;

    public gridItem() {
    }

    public gridItem(String photUrl, String nameText, String balanceText) {
        this.photUrl = photUrl;
        this.nameText = nameText;
        this.balanceText = balanceText;
    }

    public String getPhotUrl() {
        return photUrl;
    }

    public void setPhotUrl(String photUrl) {
        this.photUrl = photUrl;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getBalanceText() {
        return balanceText;
    }

    public void setBalanceText(String balanceText) {
        this.balanceText = balanceText;
    }

    @Override
    public String toString() {
        return "gridItem{" +
                "photUrl='" + photUrl + '\'' +
                ", nameText='" + nameText + '\'' +
                ", balanceText='" + balanceText + '\'' +
                '}';
    }
}
