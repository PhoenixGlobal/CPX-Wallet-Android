package chinapex.com.wallet.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.LanguageRecyclerViewAdapter;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.LanguageState;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import chinapex.com.wallet.view.MainActivity;

public class MeLanguageSettingsActivity extends BaseActivity implements
        LanguageRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = MeLanguageSettingsActivity.class.getSimpleName();

    private LanguageRecyclerViewAdapter mLanguageRecyclerViewAdapter;

    private List<LanguageState> mLanguageStates;
    private LanguageState mCurrentLanguage;
    private LanguageState mPreLanguage;

    @Override
    protected void setContentView() {
        super.setContentView();

        setContentView(R.layout.activity_me_language_settings);
    }

    @Override
    protected void init() {
        super.init();

        initData();
        initView();
    }

    private void initData() {
        String defLanguage = Locale.getDefault().toString();
        String spLanguage = (String) SharedPreferencesUtils.getParam(ApexWalletApplication
                .getInstance(), Constant.CURRENT_LANGUAGE, defLanguage);
        mCurrentLanguage = new LanguageState();
        mCurrentLanguage.setLanguageValue(spLanguage);
        mPreLanguage = new LanguageState();
        mPreLanguage.setLanguageValue(spLanguage);
    }

    private void initView() {
        RecyclerView rv_me_language = (RecyclerView) findViewById(R.id.rv_me_language_settings);
        rv_me_language.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));
        mLanguageStates = getAssetsMenus();
        mLanguageRecyclerViewAdapter = new LanguageRecyclerViewAdapter(mLanguageStates);
        mLanguageRecyclerViewAdapter.setOnItemClickListener(this);
        rv_me_language.setAdapter(mLanguageRecyclerViewAdapter);
    }

    private List<LanguageState> getAssetsMenus() {
        ArrayList<LanguageState> languageStates = new ArrayList<>();
        String[] languageNames = getResources().getStringArray(R.array.me_language_name);
        String[] languageValues = getResources().getStringArray(R.array.me_language_value);

        for (int i = 0; i < languageNames.length; i++) {
            LanguageState languageState = new LanguageState();
            languageState.setLanguageName(languageNames[i]);
            languageState.setLanguageValue(languageValues[i]);

            if (languageValues[i].equals(mCurrentLanguage.getLanguageValue())) {
                languageState.setChecked(true);
            } else {
                languageState.setChecked(false);
            }

            languageStates.add(languageState);
        }
        return languageStates;
    }

    @Override
    public void onItemClick(int position) {
        if (null == mLanguageStates || mLanguageStates.isEmpty()) {
            CpLog.e(TAG, "mLanguageStates is null or empty!");
            return;
        }

        mCurrentLanguage = mLanguageStates.get(position);
        if (null == mCurrentLanguage) {
            CpLog.e(TAG, "mCurrentLanguage is null!");
            return;
        }

        for (LanguageState LanguageStateTmp : mLanguageStates) {
            if (null == LanguageStateTmp) {
                CpLog.e(TAG, "LanguageStateTmp is null!");
                continue;
            }

            LanguageStateTmp.setChecked(false);
        }

        mCurrentLanguage.setChecked(true);
        mLanguageRecyclerViewAdapter.notifyDataSetChanged();
        switchLanguage(mCurrentLanguage.getLanguageValue());
    }

    private void switchLanguage(String languageValue) {
        if (TextUtils.isEmpty(languageValue)) {
            CpLog.e(TAG, "languageValue is null or empty!");
            return;
        }

        if (languageValue.equals(mPreLanguage.getLanguageValue())) {
            CpLog.w(TAG, "language is not change,no need to switch!");
            return;
        }

        if (languageValue.contains(Locale.CHINA.toString())) {
            PhoneUtils.updateLocale(Locale.SIMPLIFIED_CHINESE);
        } else if (languageValue.contains(Locale.ENGLISH.toString())) {
            PhoneUtils.updateLocale(Locale.US);
        } else {
            PhoneUtils.updateLocale(Locale.getDefault());
        }

        finish();

        Intent intent = new Intent(ApexWalletApplication.getInstance(), MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

}
