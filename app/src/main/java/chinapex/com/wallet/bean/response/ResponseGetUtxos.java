package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/30 0030.
 */

public class ResponseGetUtxos {

    /**
     * state : 200
     * result : [{"vout":{"Value":"98000001","N":0,
     * "Asset":"0x0c092117b4ba47b81001712425e6e7f760a637695eaf23741ba335925b195ecd",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0xe4d2ea5df2adf77df91049beccbb16f98863b93a16439c60381eac1f23bff178",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1494495227","block":4999},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x1d311a8732698e04a49e82cf4778173af531ed142eae7c852b852948bf0e3dc8",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498578389","block":202255},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x7bb1bc6d2ff9c1427f742fce5d7e6159dafc1fcfe0fc9a410773781596503279",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498578426","block":202257},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x649d7704611f6fefa35fc97eb56d72ac196c5523bd863e8f0fad718f7c1724df",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498578465","block":202259},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0xc56af91b06d33586fd44866f65b1532fc5d255726537f74e35b55bccac011e2a",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498578535","block":202262},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x9f3b57363f8fa478f4876cb5d6db2dbb17d46889665393ccb9dc34da6b959c04",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498578697","block":202270},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x5cb27cfacb05f7ad13fcf47e49e0018ec1f63070ea7cafcd28c032578f4bf1e9",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498579222","block":202295},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0xe3b0aa45438d701ba6ca9487f952c472e68daffc9eb4d91d8441f76e38df2ebc",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498579327","block":202300},
     * {"vout":{"Value":"0.00000001","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x4a83fe9e19c0127e12cc72e5c8a30179122079d1d4e344fbfeabc056a22e9749",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1498579562","block":202311},
     * {"vout":{"Value":"385.637","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x772bf84fd302eddeeeb9fe50e01b45053af91229eff854a1e8f5f843344c8f76",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1522318075","block":1304094},
     * {"vout":{"Value":"21552.38","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0xfea58e4cf87ed10a03eb846742a1173e9fb79ff4851afaf437b6900ddf15caea",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1522318335","block":1304106},
     * {"vout":{"Value":"1","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x0d178cfd2bb2f115e226c38edf95eb6ac24393944b338bfd3bd5851b2551a739",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1524213069","block":1383309},
     * {"vout":{"Value":"1","N":0,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x9f02f2bf6719e2c63b445235cf0ff6e8e7ce90bb9744fa193dd57eb9dbc6c868",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1524218005","block":1383503},
     * {"vout":{"Value":"92600000","N":2,
     * "Asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x3f256b4e003e1e6b3b361af6f83bd7876760dbfaa4ffd009860bae3b9cc6267b",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1524570610","block":1397981},
     * {"vout":{"Value":"1133390.902","N":3,
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"},
     * "txid":"0x3f256b4e003e1e6b3b361af6f83bd7876760dbfaa4ffd009860bae3b9cc6267b",
     * "spentTime":"","spentBlock":-1,"gas":"","createTime":"1524570610","block":1397981}]
     */

    private int state;
    private List<ResultBean> result;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * vout : {"Value":"98000001","N":0,
         * "Asset":"0x0c092117b4ba47b81001712425e6e7f760a637695eaf23741ba335925b195ecd",
         * "Address":"ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX"}
         * txid : 0xe4d2ea5df2adf77df91049beccbb16f98863b93a16439c60381eac1f23bff178
         * spentTime :
         * spentBlock : -1
         * gas :
         * createTime : 1494495227
         * block : 4999
         */

        private VoutBean vout;
        private String txid;
        private String spentTime;
        private int spentBlock;
        private String gas;
        private String createTime;
        private int block;

        public VoutBean getVout() {
            return vout;
        }

        public void setVout(VoutBean vout) {
            this.vout = vout;
        }

        public String getTxid() {
            return txid;
        }

        public void setTxid(String txid) {
            this.txid = txid;
        }

        public String getSpentTime() {
            return spentTime;
        }

        public void setSpentTime(String spentTime) {
            this.spentTime = spentTime;
        }

        public int getSpentBlock() {
            return spentBlock;
        }

        public void setSpentBlock(int spentBlock) {
            this.spentBlock = spentBlock;
        }

        public String getGas() {
            return gas;
        }

        public void setGas(String gas) {
            this.gas = gas;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getBlock() {
            return block;
        }

        public void setBlock(int block) {
            this.block = block;
        }

        public static class VoutBean {
            /**
             * Value : 98000001
             * N : 0
             * Asset : 0x0c092117b4ba47b81001712425e6e7f760a637695eaf23741ba335925b195ecd
             * Address : ARFe4mTKRTETerRoMsyzBXoPt2EKBvBXFX
             */

            private String Value;
            private int N;
            private String Asset;
            private String Address;

            public String getValue() {
                return Value;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }

            public int getN() {
                return N;
            }

            public void setN(int N) {
                this.N = N;
            }

            public String getAsset() {
                return Asset;
            }

            public void setAsset(String Asset) {
                this.Asset = Asset;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }
        }
    }
}
