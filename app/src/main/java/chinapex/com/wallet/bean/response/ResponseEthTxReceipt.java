package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/9/20 0020 14:17.
 * E-Mail：liuyi_61@163.com
 */
public class ResponseEthTxReceipt {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"blockHash":"0x50da008d950d9cf850fbee47d1eb59cc54e0b35dcc5994ff49d7e4b62688ffcd","blockNumber":"0x2e0178",
     * "contractAddress":null,"cumulativeGasUsed":"0x2935c7","from":"0x5372cadc9dda99b137f5f8dcf014a4e93114bac2",
     * "gasUsed":"0x8d38","logs":[{"address":"0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9",
     * "topics":["0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",
     * "0x0000000000000000000000005372cadc9dda99b137f5f8dcf014a4e93114bac2",
     * "0x0000000000000000000000005a897a6a4dcf546459d4221b0e2a636e9be9f41f"],
     * "data":"0x0000000000000000000000000000000000000000000000000000000000000001","blockNumber":"0x2e0178",
     * "transactionHash":"0x10cd239af747009bc48f84ef853d31c412882c90a96b6359a91affd4fd6fe866","transactionIndex":"0x18",
     * "blockHash":"0x50da008d950d9cf850fbee47d1eb59cc54e0b35dcc5994ff49d7e4b62688ffcd","logIndex":"0x23","removed":false}],
     * "logsBloom":"0x00080000000000000000000000000000000000000000000010000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000040000008000000000000000000000200800000000000000000000000000000000000000000000000400000000000000000000210000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","status":"0x1","to":"0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9","transactionHash":"0x10cd239af747009bc48f84ef853d31c412882c90a96b6359a91affd4fd6fe866","transactionIndex":"0x18"}
     */

    private String jsonrpc;
    private int id;
    private ResultBean result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * blockHash : 0x50da008d950d9cf850fbee47d1eb59cc54e0b35dcc5994ff49d7e4b62688ffcd
         * blockNumber : 0x2e0178
         * contractAddress : null
         * cumulativeGasUsed : 0x2935c7
         * from : 0x5372cadc9dda99b137f5f8dcf014a4e93114bac2
         * gasUsed : 0x8d38
         * logs : [{"address":"0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9",
         * "topics":["0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",
         * "0x0000000000000000000000005372cadc9dda99b137f5f8dcf014a4e93114bac2",
         * "0x0000000000000000000000005a897a6a4dcf546459d4221b0e2a636e9be9f41f"],
         * "data":"0x0000000000000000000000000000000000000000000000000000000000000001","blockNumber":"0x2e0178",
         * "transactionHash":"0x10cd239af747009bc48f84ef853d31c412882c90a96b6359a91affd4fd6fe866","transactionIndex":"0x18",
         * "blockHash":"0x50da008d950d9cf850fbee47d1eb59cc54e0b35dcc5994ff49d7e4b62688ffcd","logIndex":"0x23","removed":false}]
         * logsBloom :
         * 0x00080000000000000000000000000000000000000000000010000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000040000008000000000000000000000200800000000000000000000000000000000000000000000000400000000000000000000210000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
         * status : 0x1
         * to : 0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9
         * transactionHash : 0x10cd239af747009bc48f84ef853d31c412882c90a96b6359a91affd4fd6fe866
         * transactionIndex : 0x18
         */

        private String blockHash;
        private String blockNumber;
        private Object contractAddress;
        private String cumulativeGasUsed;
        private String from;
        private String gasUsed;
        private String logsBloom;
        private String status;
        private String to;
        private String transactionHash;
        private String transactionIndex;
        private List<LogsBean> logs;

        public String getBlockHash() {
            return blockHash;
        }

        public void setBlockHash(String blockHash) {
            this.blockHash = blockHash;
        }

        public String getBlockNumber() {
            return blockNumber;
        }

        public void setBlockNumber(String blockNumber) {
            this.blockNumber = blockNumber;
        }

        public Object getContractAddress() {
            return contractAddress;
        }

        public void setContractAddress(Object contractAddress) {
            this.contractAddress = contractAddress;
        }

        public String getCumulativeGasUsed() {
            return cumulativeGasUsed;
        }

        public void setCumulativeGasUsed(String cumulativeGasUsed) {
            this.cumulativeGasUsed = cumulativeGasUsed;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getGasUsed() {
            return gasUsed;
        }

        public void setGasUsed(String gasUsed) {
            this.gasUsed = gasUsed;
        }

        public String getLogsBloom() {
            return logsBloom;
        }

        public void setLogsBloom(String logsBloom) {
            this.logsBloom = logsBloom;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public String getTransactionIndex() {
            return transactionIndex;
        }

        public void setTransactionIndex(String transactionIndex) {
            this.transactionIndex = transactionIndex;
        }

        public List<LogsBean> getLogs() {
            return logs;
        }

        public void setLogs(List<LogsBean> logs) {
            this.logs = logs;
        }

        public static class LogsBean {
            /**
             * address : 0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9
             * topics : ["0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",
             * "0x0000000000000000000000005372cadc9dda99b137f5f8dcf014a4e93114bac2",
             * "0x0000000000000000000000005a897a6a4dcf546459d4221b0e2a636e9be9f41f"]
             * data : 0x0000000000000000000000000000000000000000000000000000000000000001
             * blockNumber : 0x2e0178
             * transactionHash : 0x10cd239af747009bc48f84ef853d31c412882c90a96b6359a91affd4fd6fe866
             * transactionIndex : 0x18
             * blockHash : 0x50da008d950d9cf850fbee47d1eb59cc54e0b35dcc5994ff49d7e4b62688ffcd
             * logIndex : 0x23
             * removed : false
             */

            private String address;
            private String data;
            private String blockNumber;
            private String transactionHash;
            private String transactionIndex;
            private String blockHash;
            private String logIndex;
            private boolean removed;
            private List<String> topics;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getBlockNumber() {
                return blockNumber;
            }

            public void setBlockNumber(String blockNumber) {
                this.blockNumber = blockNumber;
            }

            public String getTransactionHash() {
                return transactionHash;
            }

            public void setTransactionHash(String transactionHash) {
                this.transactionHash = transactionHash;
            }

            public String getTransactionIndex() {
                return transactionIndex;
            }

            public void setTransactionIndex(String transactionIndex) {
                this.transactionIndex = transactionIndex;
            }

            public String getBlockHash() {
                return blockHash;
            }

            public void setBlockHash(String blockHash) {
                this.blockHash = blockHash;
            }

            public String getLogIndex() {
                return logIndex;
            }

            public void setLogIndex(String logIndex) {
                this.logIndex = logIndex;
            }

            public boolean isRemoved() {
                return removed;
            }

            public void setRemoved(boolean removed) {
                this.removed = removed;
            }

            public List<String> getTopics() {
                return topics;
            }

            public void setTopics(List<String> topics) {
                this.topics = topics;
            }
        }
    }
}
