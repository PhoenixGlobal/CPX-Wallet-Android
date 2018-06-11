package chinapex.com.wallet.view.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;

/**
 * Created by SteelCabbage on 2018/6/10 22:31
 * E-Mail：liuyi_61@163.com
 */
public class ImportKeystoreFragment extends BaseFragment {

    private static final String TAG = ImportKeystoreFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import_keystore, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }
}
