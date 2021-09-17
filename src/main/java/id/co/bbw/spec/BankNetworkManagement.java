package id.co.bbw.spec;

public class BankNetworkManagement extends LogonManager {
    public BankNetworkManagement() {
        super("digitalbank-mux", "digitalbank-send", 300000);
    }
}
