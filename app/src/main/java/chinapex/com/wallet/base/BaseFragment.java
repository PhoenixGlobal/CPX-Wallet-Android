package chinapex.com.wallet.base;

import android.app.Fragment;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;

import chinapex.com.wallet.utils.CpLog;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    public void startActivity(Class cls, boolean isFinish) {
        Intent intent = new Intent(this.getActivity(), cls);
        startActivity(intent);
        if (isFinish) {
            this.getActivity().finish();
        }
    }

    public void startActivityParcelable(Class cls, boolean isFinish, String parcelableKey,
                                        Parcelable parcelable) {
        if (null == parcelable || TextUtils.isEmpty(parcelableKey)) {
            CpLog.e(TAG, "parcelable or parcelableKey is null!");
            return;
        }

        Intent intent = new Intent(this.getActivity(), cls);
        intent.putExtra(parcelableKey, parcelable);
        startActivity(intent);
        if (isFinish) {
            this.getActivity().finish();
        }
    }
}
