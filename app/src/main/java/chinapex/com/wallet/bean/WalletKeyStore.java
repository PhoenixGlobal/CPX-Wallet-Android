package chinapex.com.wallet.bean;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class WalletKeyStore {

    private String walletName;
    private String walletAddr;
    private String walletKeyStore;

    public WalletKeyStore() {

    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

    public String getWalletKeyStore() {
        return walletKeyStore;
    }

    public void setWalletKeyStore(String walletKeyStore) {
        this.walletKeyStore = walletKeyStore;
    }

    @Override
    public String toString() {
        return "WalletKeyStore{" +
                "walletName='" + walletName + '\'' +
                ", walletAddr='" + walletAddr + '\'' +
                ", walletKeyStore='" + walletKeyStore + '\'' +
                '}';
    }
}
