package chinapex.com.wallet.view.wallet;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by SteelCabbage on 2018/5/23 0023.
 */

public class MyTextWatcher implements TextWatcher {

    private EditText mEditText;

    public MyTextWatcher(EditText editText) {
        mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
