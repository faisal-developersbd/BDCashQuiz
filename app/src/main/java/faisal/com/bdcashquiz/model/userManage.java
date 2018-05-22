package faisal.com.bdcashquiz.model;

public class userManage {
    private String name;
    private String email;
    private String phoneNumber;
    private String photoUrl;
    private String totalLife;
    private String gameLife;
    private String balance;

    public userManage(String name, String email, String phoneNumber, String photoUrl, String totalLife, String gameLife) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.totalLife = totalLife;
        this.gameLife = gameLife;
    }

    public userManage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTotalLife() {
        return totalLife;
    }

    public void setTotalLife(String totalLife) {
        this.totalLife = totalLife;
    }

    public String getGameLife() {
        return gameLife;
    }

    public void setGameLife(String gameLife) {
        this.gameLife = gameLife;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "userManage{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", totalLife='" + totalLife + '\'' +
                ", gameLife='" + gameLife + '\'' +
                '}';
    }
}
