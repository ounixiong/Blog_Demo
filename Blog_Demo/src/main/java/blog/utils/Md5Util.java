package blog.utils;


import java.security.MessageDigest;

/**
 * @author 欧尼熊
 * MD5 工具类
 */
public class Md5Util {

    /**
     * 定义的十六进制字符数组
     */
    private static final String[] HEX_DIGITS =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 字节数组到十六进制字符串
     * 将字节数组中每个字节转为两个十六进制字符串并拼接
     *
     * @param b 字节数组
     * @return 返回十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    /**
     * 将 1 个字节转为 2个十六进制位
     * 1 byte = 8 bit，1 个 16 进制位 = 4 个二进制位（4 bit）
     * 转换思路：最简单的办法就是先将 byte 转为 10 进制的int类型，然后将十进制数转十六进制
     *
     * @param b 字节数据
     * @return 返回转换后的十六进制字符串
     */
    private static String byteToHexString(byte b) {
        // 将 byte 转为 int 类型（10 进制）
        int n = b;
        // 10 进制数转为 16 进制
        if (n < 0) {
            n += 256;
        }
        // 十六进制十位数与个位数大小
        int d1 = n / 16;
        int d2 = n % 16;
        // 从十六进制字符串数组中访问对应的十六进制字符串
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * MD5 编码加密：统一返回大写形式摘要结果，默认固定长度 128 bit（32位16进制）
     *
     * @param origin      原字符串
     * @param charsetName MD5算法字符集类型
     * @return 返回加密后的字符串
     */
    public static String md5Encode(String origin, String charsetName) {
        // 定义结果字符串
        String resultString = null;
        try {
            // 赋值原字符串
            resultString = origin;
            // 创建 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 未指定字符集使用默认的编码
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(
                        md.digest(resultString.getBytes()));
            }
            // 否则使用指定的编码格式
            else {
                // 计算摘要后转为十六进制字符串
                resultString = byteArrayToHexString(
                        // 向 MessageDigest 传送要计算的数据：数据要转化为指定编码的字节数组
                        md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception ignored) {
        }
        // 返回计算后的字符串摘要
        return resultString;
    }

}
