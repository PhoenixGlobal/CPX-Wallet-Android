package chinapex.com.wallet.view.me.portrait;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.PortraitRecyclerViewAdapter;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.PortraitBean;
import chinapex.com.wallet.bean.PortraitShow;
import chinapex.com.wallet.bean.PortraitTagsBean;
import chinapex.com.wallet.bean.json.PortraitEnterpriseEn;
import chinapex.com.wallet.bean.json.PortraitEnterpriseZh;
import chinapex.com.wallet.bean.response.ResponsePortrait;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.view.dialog.WhorlViewDialog;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by SteelCabbage on 2018/7/24 0024 15:10.
 * E-Mail：liuyi_61@163.com
 */

public class MeEnterprisePortraitFragment extends BaseFragment implements
        PortraitRecyclerViewAdapter.OnItemClickListener, View.OnClickListener {

    private static final String TAG = MeEnterprisePortraitFragment.class.getSimpleName();

    private static final int REQUEST_CODE = 101;

    private Button mBt_portrait_enterprise_save;
    private RecyclerView mRv_portrait_enterprise;
    private PortraitRecyclerViewAdapter mPortraitRecyclerViewAdapter;

    private String mAppLanguage;
    private List<PortraitBean> mPortraitBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me_enterprise, container, false);
    }

    @Override
    protected void init(View view) {
        super.init(view);

        initData();
        initView(view);
    }

    private void initData() {
        mPortraitBeans = new ArrayList<>();
        mAppLanguage = PhoneUtils.getAppLanguage();
        String portraitJson;
        if (mAppLanguage.contains(Locale.CHINA.toString())) {
            portraitJson = PortraitEnterpriseZh.PORTRAIT_ENTERPRISE_ZH;
        } else if (mAppLanguage.contains(Locale.ENGLISH.toString())) {
            portraitJson = PortraitEnterpriseEn.PORTRAIT_ENTERPRISE_EN;
        } else {
            portraitJson = PortraitEnterpriseEn.PORTRAIT_ENTERPRISE_EN;
        }

        ResponsePortrait responsePortrait = GsonUtils.json2Bean(portraitJson, ResponsePortrait
                .class);
        if (null == responsePortrait) {
            CpLog.e(TAG, "responsePortrait is null!");
            return;
        }

        List<ResponsePortrait.ResultBean> result = responsePortrait.getResult();
        if (null == result || result.isEmpty()) {
            CpLog.e(TAG, "result is null or empty!");
            return;
        }

        ApexWalletDbDao instance = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == instance) {
            CpLog.e(TAG, "instance is null!");
            return;
        }

        HashMap<String, PortraitShow> hm = instance.queryPortraitShowByType(mAppLanguage);

        for (ResponsePortrait.ResultBean resultBean : result) {
            PortraitBean portraitBean = new PortraitBean();
            portraitBean.setType(resultBean.getType());
            String title = resultBean.getTitle();
            portraitBean.setTitle(title);
            portraitBean.setResource(resultBean.getResource());
            if (null != hm && hm.containsKey(title)) {
                portraitBean.setSelectedContent(hm.get(title).getValue());
            } else {
                portraitBean.setSelectedContent("");
            }
            List<ResponsePortrait.ResultBean.DataBean> data = resultBean.getData();
            List<PortraitTagsBean> portraitTagsBeans = new ArrayList<>();
            for (ResponsePortrait.ResultBean.DataBean datum : data) {
                PortraitTagsBean portraitTagsBean = new PortraitTagsBean();
                portraitTagsBean.setName(datum.getName());
                portraitTagsBean.setId(datum.getId());
                portraitTagsBeans.add(portraitTagsBean);
            }
            portraitBean.setData(portraitTagsBeans);
            mPortraitBeans.add(portraitBean);
        }

    }

    private void initView(View view) {
        mRv_portrait_enterprise = view.findViewById(R.id.rv_portrait_enterprise);
        mRv_portrait_enterprise.setLayoutManager(new LinearLayoutManager(ApexWalletApplication
                .getInstance(), LinearLayoutManager.VERTICAL, false));
        mPortraitRecyclerViewAdapter = new PortraitRecyclerViewAdapter(mPortraitBeans);
        mPortraitRecyclerViewAdapter.setOnItemClickListener(this);
        mRv_portrait_enterprise.setAdapter(mPortraitRecyclerViewAdapter);

        mBt_portrait_enterprise_save = view.findViewById(R.id.bt_portrait_enterprise_save);
        mBt_portrait_enterprise_save.setOnClickListener(this);
    }


    @Override
    public void onItemClick(int position) {
        PortraitBean portraitBean = mPortraitBeans.get(position);
        if (null == portraitBean) {
            CpLog.e(TAG, "portraitBean is null!");
            return;
        }

        CpLog.i(TAG, "portraitBean:" + portraitBean.toString());

        switch (portraitBean.getType()) {
            case Constant.TYPE_INPUT:
                Intent intent = new Intent(ApexWalletApplication.getInstance(), TypeInputActivity
                        .class);
                intent.putExtra(Constant.EXTRA_TYPE_INPUT_TITLE, portraitBean.getTitle());
                intent.putExtra(Constant.EXTRA_TYPE_INPUT_POSITION, position);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case Constant.TYPE_LEVEL_ONE_LINKAGE:
                showOptionPicker(portraitBean);
                break;
            case Constant.TYPE_LEVEL_TWO_LINKAGE:

                break;
            case Constant.TYPE_LEVEL_THREE_LINKAGE:
                showDatePicker(portraitBean);
                break;
            case Constant.TYPE_TAGS:

                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            CpLog.e(TAG, "requestCode != REQUEST_CODE");
            return;
        }

        if (null == data) {
            CpLog.e(TAG, "onActivityResult() -> data is null!");
            return;
        }

        String inputContent = data.getStringExtra(Constant.EXTRA_TYPE_INPUT_CONTENT);
        int position = data.getIntExtra(Constant.EXTRA_TYPE_INPUT_POSITION, -1);
        if (position == -1) {
            CpLog.e(TAG, "position is -1");
            return;
        }

        PortraitBean portraitBean = mPortraitBeans.get(position);
        if (null == portraitBean) {
            CpLog.e(TAG, "portraitBean is null!");
            return;
        }

        ApexWalletDbDao instance = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == instance) {
            CpLog.e(TAG, "instance is null！");
            return;
        }

        PortraitShow portraitShow = new PortraitShow();
        portraitShow.setType(mAppLanguage);
        portraitShow.setLabel(portraitBean.getTitle());
        portraitShow.setValue(inputContent);
        instance.insertPortraitShow(portraitShow);

        portraitBean.setSelectedContent(inputContent);
        mPortraitRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void showOptionPicker(final PortraitBean portraitBean) {
        if (null == portraitBean) {
            CpLog.e(TAG, "portraitBean is null!");
            return;
        }

        final List<PortraitTagsBean> portraitTagsBeans = portraitBean.getData();
        if (null == portraitTagsBeans || portraitTagsBeans.isEmpty()) {
            CpLog.e(TAG, "portraitTagsBeans is null or empty!");
            return;
        }

        ArrayList<String> list = new ArrayList<>();
        for (PortraitTagsBean portraitTagsBean : portraitTagsBeans) {
            if (null == portraitTagsBean) {
                CpLog.e(TAG, "portraitTagsBean is null!");
                continue;
            }

            list.add(portraitTagsBean.getName());
        }

        OptionPicker picker = new OptionPicker(this.getActivity(), list);
        picker.setOffset(2);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setHeight(DensityUtil.dip2px(ApexWalletApplication.getInstance(), 200));
        picker.setTopHeight(40);
        picker.setDividerColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_DDDDDD));
        picker.setTopLineColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_DDDDDD));
        picker.setTextColor(
                Color.BLACK,
                ApexWalletApplication.getInstance().getResources().getColor(R.color.c_999999));
        picker.setSelectedIndex(1);
        picker.setTextSize(16);

        // set cancel
        picker.setCancelText(ApexWalletApplication.getInstance().getResources().getString(R
                .string.cancel));
        picker.setCancelTextColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_1253BF));
        picker.setCancelTextSize(14);

        // set confirm
        picker.setSubmitText(ApexWalletApplication.getInstance().getResources().getString(R
                .string.confirm));
        picker.setSubmitTextColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_1253BF));
        picker.setSubmitTextSize(14);

        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                ApexWalletDbDao instance = ApexWalletDbDao.getInstance(ApexWalletApplication
                        .getInstance());
                if (null == instance) {
                    CpLog.e(TAG, "instance is null!");
                    return;
                }

                PortraitShow portraitShow = new PortraitShow();
                portraitShow.setType(mAppLanguage);
                portraitShow.setLabel(portraitBean.getTitle());
                portraitShow.setValue(item);
                instance.insertPortraitShow(portraitShow);

                portraitBean.setSelectedContent(item);
                mPortraitRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        picker.show();
    }

    private void showDatePicker(final PortraitBean portraitBean) {
        DatePicker picker = new DatePicker(this.getActivity(), DateTimePicker.YEAR_MONTH_DAY);
        picker.setRangeStart(1918, 1, 1);
        picker.setRangeEnd(2118, 12, 31);

        picker.setTopHeight(40);
        picker.setDividerColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_DDDDDD));
        picker.setTopLineColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_DDDDDD));
        picker.setTextColor(
                Color.BLACK,
                ApexWalletApplication.getInstance().getResources().getColor(R.color.c_999999));
        picker.setContentPadding(20, 20);
        picker.setSelectedItem(2018, 7, 27);
        picker.setLabelTextColor(Color.BLACK);

        String year = ApexWalletApplication.getInstance().getResources().getString(R.string.year);
        String month = ApexWalletApplication.getInstance().getResources().getString(R.string.month);
        String day = ApexWalletApplication.getInstance().getResources().getString(R.string.day);
        picker.setLabel(year, month, day);

        // set cancel
        picker.setCancelText(ApexWalletApplication.getInstance().getResources().getString(R
                .string.cancel));
        picker.setCancelTextColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_1253BF));
        picker.setCancelTextSize(14);

        // set confirm
        picker.setSubmitText(ApexWalletApplication.getInstance().getResources().getString(R
                .string.confirm));
        picker.setSubmitTextColor(ApexWalletApplication.getInstance().getResources().getColor(R
                .color.c_1253BF));

        picker.setSubmitTextSize(14);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                ApexWalletDbDao instance = ApexWalletDbDao.getInstance(ApexWalletApplication
                        .getInstance());
                if (null == instance) {
                    CpLog.e(TAG, "instance is null!");
                    return;
                }

                PortraitShow portraitShow = new PortraitShow();
                portraitShow.setType(mAppLanguage);
                portraitShow.setLabel(portraitBean.getTitle());
                portraitShow.setValue(year + "-" + month + "-" + day);
                instance.insertPortraitShow(portraitShow);

                portraitBean.setSelectedContent(year + "-" + month + "-" + day);
                mPortraitRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        picker.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_portrait_enterprise_save:
                showWhorlViewDialog();
                break;
            default:
                break;
        }
    }

    private void showWhorlViewDialog() {
        WhorlViewDialog whorlViewDialog = WhorlViewDialog.newInstance();
        whorlViewDialog.show(getFragmentManager(), "WhorlViewDialog");
    }
}
