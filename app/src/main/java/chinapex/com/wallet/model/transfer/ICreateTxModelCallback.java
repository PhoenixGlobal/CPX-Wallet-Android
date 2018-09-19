package chinapex.com.wallet.model.transfer;

/**
 * Created by SteelCabbage on 2018/8/24 0024 16:03.
 * E-Mail：liuyi_61@163.com
 */
public interface ICreateTxModelCallback {
    void checkTxFee(boolean isEnough, String msg);

    void CreateTxModel(String toastMsg, boolean isFinish);

}
