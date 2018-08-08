package chinapex.com.wallet.base;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    public void startActivity(Class cls, boolean isFinish) {
        Intent intent = new Intent(ApexWalletApplication.getInstance(), cls);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void startActivityParcelable(Class cls, boolean isFinish, String parcelableKey,
                                        Parcelable parcelable) {
        if (null == parcelable || TextUtils.isEmpty(parcelableKey)) {
            CpLog.e(TAG, "parcelable or parcelableKey is null!");
            return;
        }

        Intent intent = new Intent(ApexWalletApplication.getInstance(), cls);
        intent.putExtra(parcelableKey, parcelable);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void startActivityStringExtra(Class cls, boolean isFinish, String key, String value) {
        Intent intent = new Intent(ApexWalletApplication.getInstance(), cls);
        intent.putExtra(key, value);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
