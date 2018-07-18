package chinapex.com.wallet.bean;

/**
 * Created by SteelCabbage on 2018/5/24 0024.
 */

public class LanguageState {
    private String languageName;
    private String languageValue;
    private boolean isChecked;

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageValue() {
        return languageValue;
    }

    public void setLanguageValue(String languageValue) {
        this.languageValue = languageValue;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageState that = (LanguageState) o;

        if (!languageName.equals(that.languageName)) return false;
        return languageValue.equals(that.languageValue);
    }

    @Override
    public int hashCode() {
        int result = languageName.hashCode();
        result = 31 * result + languageValue.hashCode();
        return result;
    }
}
