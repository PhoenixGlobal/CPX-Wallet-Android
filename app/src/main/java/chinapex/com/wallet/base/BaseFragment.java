package chinapex.com.wallet.base;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
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

    public void startActivityBundle(Class cls, boolean isFinish, String bundleKey, String key, String value, String
            parcelableKey, Parcelable parcelable) {
        Intent intent = new Intent(ApexWalletApplication.getInstance(), cls);
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        bundle.putParcelable(parcelableKey, parcelable);
        intent.putExtra(bundleKey, bundle);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void startActivityParcelable(Class cls, boolean isFinish, String parcelableKey, Parcelable parcelable) {
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
}
