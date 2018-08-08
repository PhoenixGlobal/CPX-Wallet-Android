package chinapex.com.wallet.bean;

/**
 * Created by SteelCabbage on 2018/5/29 0029.
 */

public class MnemonicState {
    private String mnemonic;
    private boolean isSelected;

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MnemonicState that = (MnemonicState) o;

        if (isSelected != that.isSelected) return false;
        return mnemonic.equals(that.mnemonic);
    }

    @Override
    public int hashCode() {
        int result = mnemonic.hashCode();
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
