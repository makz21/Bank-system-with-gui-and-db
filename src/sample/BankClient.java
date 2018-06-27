package sample;

public class BankClient {
    private Person klientBanku;
    private double stanKonta;
    private int klientID;

    public BankClient(Person klientBanku, double stanKonta) {
        this.klientBanku = klientBanku;
        this.stanKonta = stanKonta;
    }

    public Person getKlientBanku() {
        return klientBanku;
    }

    public double getStanKonta() {
        return stanKonta;
    }

    @Override
    public String toString() {
        return "BankClient{" +
                "bankClient=" + klientBanku +
                ", clientFunds=" + stanKonta +
                '}';
    }
}
