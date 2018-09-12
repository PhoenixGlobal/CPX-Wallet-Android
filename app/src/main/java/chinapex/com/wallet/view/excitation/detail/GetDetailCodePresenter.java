package chinapex.com.wallet.view.excitation.detail;

import chinapex.com.wallet.bean.AddressResultCode;
import chinapex.com.wallet.bean.request.RequestSubmitExcitation;

public class GetDetailCodePresenter implements IGetDetailCodePresenter, IGetResultCodeModelCallback {

    private IDetailView mIDetailView;
    private IDetailCodeModel mIDetailCodeModel;

    public GetDetailCodePresenter(IDetailView iDetailView) {
        mIDetailView = iDetailView;
    }

    @Override
    public void getDetailCode(RequestSubmitExcitation requestSubmitExcitation) {
        mIDetailCodeModel = new GetDetailCodeModel(this);
        mIDetailCodeModel.getDetailCode(requestSubmitExcitation);
    }

    @Override
    public void getResultCode(AddressResultCode resultCode) {
        mIDetailView.getDetailCode(resultCode);
    }
}
