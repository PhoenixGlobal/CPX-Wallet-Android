package chinapex.com.wallet.view.excitation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class ExcitationFragment extends BaseFragment {

    private static final String TAG = ExcitationFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        return inflater.inflate(R.layout.fragment_excitation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {

    }

    private void initData() {

    }
}
