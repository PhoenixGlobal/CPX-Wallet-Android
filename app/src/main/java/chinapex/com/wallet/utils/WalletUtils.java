package chinapex.com.wallet.utils;

/**
 * Created by SteelCabbage on 2018/6/13 0013 14:58.
 * E-Mail：liuyi_61@163.com
 */

public class WalletUtils {

    public static byte[] reverseArray(String string) {
        if ("0".equals(string)) {
            byte[] zero = new byte[1];
            return zero;
        }
        byte[] array = hexStringToBytes(string);
        byte[] array_list = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            array_list[i] = (array[array.length - i - 1]);
        }
        return array_list;
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
