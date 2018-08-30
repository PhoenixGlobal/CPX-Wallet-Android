package chinapex.com.wallet.view.excitation;

import java.util.List;

import chinapex.com.wallet.bean.ExcitationBean;

public class GetExcitationPresenter implements IGetExcitationPresenter, IGetExcitationModelCallback {
    private IGetExcitationView mIGetExcitationView;
    private IGetExcitationModel mIGetExcitationModel;

    public GetExcitationPresenter(IGetExcitationView iGetExcitationView) {
        mIGetExcitationView = iGetExcitationView;
    }

    @Override
    public void getExcitation() {
        mIGetExcitationModel = new GetExcitationModel(this);
        mIGetExcitationModel.getExcitation();
    }

    @Override
    public void getExcitation(List<ExcitationBean> excitationBeans) {
        mIGetExcitationView.getExcitation(excitationBeans);
    }
}
