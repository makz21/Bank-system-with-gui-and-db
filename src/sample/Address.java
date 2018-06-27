package sample;

public class Address {
    private String kraj;
    private String miasto;
    private String adres;

    public Address(String kraj, String miasto, String adres) {
        this.kraj = kraj;
        this.miasto =  miasto;
        this.adres = adres;
    }

    public String getKraj() {
        return kraj;
    }

    public String getMiasto() {
        return miasto;
    }

    public String getAdres() {
        return adres;
    }

    public String forSQLquery(){
        return "'" + kraj + "'" + ',' + "'" + miasto + "'" + ',' + "'" + adres + "'" + ',';
    }

    @Override
    public String toString() {
        return "Adres{" +
                "kraj='" + kraj + '\'' +
                ", miasto='" + miasto + '\'' +
                ", adres='" + adres + '\'' +
                '}';
    }
}
