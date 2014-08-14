package org.unique.common.tools;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串操作类
 * @author:rex
 * @date:2014年8月14日
 * @version:1.0
 */
public class StringUtils {

    private static final String NUM_S = "0123456789";

    private static final String STR_S = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static boolean isEmpty(final String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(final String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) return true;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    public static String trimToNull(final String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(final String str) {
        return str == null ? "" : str.trim();
    }

    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 计算文字长度 无中文问题
     * 
     * @param @param string
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     */
    public static int getLength(final String string) {
        if (isBlank(string)) {
            return 0;
        } else {
            char[] strChars = string.toCharArray();
            return strChars.length;
        }
    }

    /**
     * 将字符串中特定模式的字符转换成map中对应的值,
     * 
     * @param s 需要转换的字符串
     * @param map 转换所需的键值对集合
     * @return 转换后的字符串
     */
    public static String replace(final String s, Map<String, String> map) {
        StringBuilder sb = new StringBuilder((int) (s.length() * 1.5));
        int cursor = 0;
        for (int start, end; (start = s.indexOf("${", cursor)) != -1 && (end = s.indexOf("}", start)) != -1;) {
            sb.append(s.substring(cursor, start));
            String key = s.substring(start + 2, end);
            sb.append(map.get(trim(key)));
            cursor = end + 1;
        }
        sb.append(s.substring(cursor, s.length()));
        return sb.toString();
    }

    /**
     * 获取ip
     * 
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Requested-For");
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * unicode编码转汉字
     * @param str
     * @return
     */
    public static String unicodeToString(String str) {
        
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");    
        }
        return str;
    }

    /**
     * 获取unicode编码
     * @param s
     * @return
     */
    public static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toLowerCase(firstChar) + tail;
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toUpperCase(firstChar) + tail;
        return str;
    }

    /**
     * 首字母大写其余小写
     *
     * @param str
     */
    public static String upperFirstLowerOther(final String str) {
        if (isEmpty(str)) return str;
        StringBuilder sb = new StringBuilder();
        char c = str.charAt(0);
        sb.append(Character.toUpperCase(c));
        String other = str.substring(1);
        sb.append(other.toLowerCase());
        return sb.toString();
    }

    /**
     * 字符串不为 null 而且不为 "" 时返回 true
     */
    public static boolean notBlank(final String str) {
        return str == null || "".equals(str.trim()) ? false : true;
    }

    public static boolean notBlank(final String... strings) {
        if (strings == null) return false;
        for (String str : strings)
            if (str == null || "".equals(str.trim())) return false;
        return true;
    }

    public static boolean notNull(Object... paras) {
        if (paras == null) return false;
        for (Object obj : paras)
            if (obj == null) return false;
        return true;
    }

    /**
     * 截取文字safe 中文
     * 
     * @param @param string
     * @param @param length
     * @param @param more like `...`,`>>>`
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String subCn(final String string, final int length, final String more) {
        if (StringUtils.isNotEmpty(string)) {
            char[] chars = string.toCharArray();
            if (chars.length > length) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    sb.append(chars[i]);
                }
                sb.append(more);
                return sb.toString();
            }
        }
        return string;
    }

    public static String join(final String[] array, final String separator) {
        if (array == null) {
            return null;
        }
        final int noOfItems = array.length;
        if (noOfItems <= 0) {
            return null;
        }
        if (noOfItems == 1) {
            return array[0].toString();
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = 0; i < noOfItems; i++) {
            buf.append(separator);
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        final int noOfItems = array.length;
        if (noOfItems <= 0) {
            return null;
        }
        if (noOfItems == 1) {
            return array[0].toString();
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = 0; i < noOfItems; i++) {
            buf.append(separator);
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static <T> String join(final List<T> array, final String separator) {
        if (array == null) {
            return null;
        }
        final int noOfItems = array.size();
        if (noOfItems <= 0) {
            return null;
        }
        if (noOfItems == 1) {
            return array.get(0).toString();
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = 0; i < noOfItems; i++) {
            buf.append(separator);
            if (array.get(i) != null) {
                buf.append(array.get(i));
            }
        }
        return buf.toString();
    }

    /**
     * 生成一个10位的tonken用于http cache
     * 
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getTonken() {
        return RandomStringUtils.random(10, NUM_S);
    }

    /**
     * 生成随机字符串
     * 
     * @author：rex
     * @param count
     * @return
     */
    public static String randomStr(final int count) {
        return RandomStringUtils.random(count, STR_S);
    }

    /**
     * 生成随机数
     * 
     * @author：rex
     * @param count
     * @return
     */
    public static String randomNum(final int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 是否为正确的用户名
     * 
     * @param msg
     * @return
     */
    public static boolean isAlphaUnderline(final String msg) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher matcher = pattern.matcher(msg);
        return matcher.matches();
    }

    /**
     * 返回字符串中首字拼音
     * 
     * @param word
     * @return
     */
    public static String getCapitalizePinyin(final String word) {
        try {
            StringBuilder pinyin = new StringBuilder();
            ;
            for (int i = 0; i < word.length(); i++) {
                String py = getPinyin(getCode(word.charAt(i)));
                pinyin.append((py.length() > 0) ? py.charAt(0) : '0');
            }
            return pinyin.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 该方法返回一个字符的DBCS编码值
     * 
     * @param cc
     * @return int
     * @throws UnsupportedEncodingException
     */
    private static int getCode(char cc) throws UnsupportedEncodingException {
        byte[] bs = String.valueOf(cc).getBytes("GBK");
        int code = (bs[0] << 8) | (bs[1] & 0x00FF);
        if (bs.length < 2) code = (int) cc;
        bs = null;
        return code;
    }

    /**
     * 该方法通过DBCS的编码值到哈希表中查询得到对应的拼音串
     * 
     * @param hz
     * @return String
     */
    protected static String getPinyin(final int hz) {
        String py = "";
        if (hz > 0 && hz < 160)
            py += hz;
        // else if (hz < -20319 || hz > -10247);
        else if (hz <= -10247 && hz >= -20319) {
            PinyinCode pc = null;
            int i = PinyinInfo.pinyin.size() - 1;
            for (; i >= 0; i--) {
                pc = (PinyinCode) PinyinInfo.pinyin.get(i);
                if (pc.code <= hz) break;
            }
            if (i >= 0) py = pc.pinyin;
        }
        return py;
    }

    /**
     * 根据汉字字符获得笔画数,拼音和非法字符默认为0
     * 
     * @param charcator
     * @return int
     */
    public static int getStrokeCount(final char charcator) {
        byte[] bytes = (String.valueOf(charcator)).getBytes();
        if (bytes == null || bytes.length > 2 || bytes.length <= 0) {
            // 错误引用,非合法字符
            return 0;
        }
        if (bytes.length == 1) {
            // 英文字符
            return 0;
        }
        if (bytes.length == 2) {
            // 中文字符
            int highByte = 256 + bytes[0];
            int lowByte = 256 + bytes[1];
            return getStrokeCount(highByte, lowByte);
        }

        // 未知错误
        return 0;
    }

    /**
     * @param highByte 高位字节
     * @param lowByte 低位字节
     * @return int
     */
    private static int getStrokeCount(final int highByte, final int lowByte) {
        if (highByte < 0xB0 || highByte > 0xF7 || lowByte < 0xA1 || lowByte > 0xFE) {
            // 非GB2312合法字符
            return -1;
        }
        int offset = (highByte - 0xB0) * (0xFE - 0xA0) + (lowByte - 0xA1);
        return PinyinInfo.gb2312StrokeCount[offset];
    }

    private static class PinyinCode {

        public PinyinCode(final String py, final int cd) {
            pinyin = py;
            code = cd;
        }

        public String pinyin = null;

        public int code = 0;
    }

    private static class PinyinInfo {

        public static ArrayList<PinyinCode> pinyin = new ArrayList<PinyinCode>(395);
        static {
            pinyin.add(new PinyinCode("a", -20319));
            pinyin.add(new PinyinCode("ai", -20317));
            pinyin.add(new PinyinCode("an", -20304));
            pinyin.add(new PinyinCode("ang", -20295));
            pinyin.add(new PinyinCode("ao", -20292));
            pinyin.add(new PinyinCode("ba", -20283));
            pinyin.add(new PinyinCode("bai", -20265));
            pinyin.add(new PinyinCode("ban", -20257));
            pinyin.add(new PinyinCode("bang", -20242));
            pinyin.add(new PinyinCode("bao", -20230));
            pinyin.add(new PinyinCode("bei", -20051));
            pinyin.add(new PinyinCode("ben", -20036));
            pinyin.add(new PinyinCode("beng", -20032));
            pinyin.add(new PinyinCode("bi", -20026));
            pinyin.add(new PinyinCode("bian", -20002));
            pinyin.add(new PinyinCode("biao", -19990));
            pinyin.add(new PinyinCode("bie", -19986));
            pinyin.add(new PinyinCode("bin", -19982));
            pinyin.add(new PinyinCode("bing", -19976));
            pinyin.add(new PinyinCode("bo", -19805));
            pinyin.add(new PinyinCode("bu", -19784));
            pinyin.add(new PinyinCode("ca", -19775));
            pinyin.add(new PinyinCode("cai", -19774));
            pinyin.add(new PinyinCode("can", -19763));
            pinyin.add(new PinyinCode("cang", -19756));
            pinyin.add(new PinyinCode("cao", -19751));
            pinyin.add(new PinyinCode("ce", -19746));
            pinyin.add(new PinyinCode("ceng", -19741));
            pinyin.add(new PinyinCode("cha", -19739));
            pinyin.add(new PinyinCode("chai", -19728));
            pinyin.add(new PinyinCode("chan", -19725));
            pinyin.add(new PinyinCode("chang", -19715));
            pinyin.add(new PinyinCode("chao", -19540));
            pinyin.add(new PinyinCode("che", -19531));
            pinyin.add(new PinyinCode("chen", -19525));
            pinyin.add(new PinyinCode("cheng", -19515));
            pinyin.add(new PinyinCode("chi", -19500));
            pinyin.add(new PinyinCode("chong", -19484));
            pinyin.add(new PinyinCode("chou", -19479));
            pinyin.add(new PinyinCode("chu", -19467));
            pinyin.add(new PinyinCode("chuai", -19289));
            pinyin.add(new PinyinCode("chuan", -19288));
            pinyin.add(new PinyinCode("chuang", -19281));
            pinyin.add(new PinyinCode("chui", -19275));
            pinyin.add(new PinyinCode("chun", -19270));
            pinyin.add(new PinyinCode("chuo", -19263));
            pinyin.add(new PinyinCode("ci", -19261));
            pinyin.add(new PinyinCode("cong", -19249));
            pinyin.add(new PinyinCode("cou", -19243));
            pinyin.add(new PinyinCode("cu", -19242));
            pinyin.add(new PinyinCode("cuan", -19238));
            pinyin.add(new PinyinCode("cui", -19235));
            pinyin.add(new PinyinCode("cun", -19227));
            pinyin.add(new PinyinCode("cuo", -19224));
            pinyin.add(new PinyinCode("da", -19218));
            pinyin.add(new PinyinCode("dai", -19212));
            pinyin.add(new PinyinCode("dan", -19038));
            pinyin.add(new PinyinCode("dang", -19023));
            pinyin.add(new PinyinCode("dao", -19018));
            pinyin.add(new PinyinCode("de", -19006));
            pinyin.add(new PinyinCode("deng", -19003));
            pinyin.add(new PinyinCode("di", -18996));
            pinyin.add(new PinyinCode("dian", -18977));
            pinyin.add(new PinyinCode("diao", -18961));
            pinyin.add(new PinyinCode("die", -18952));
            pinyin.add(new PinyinCode("ding", -18783));
            pinyin.add(new PinyinCode("diu", -18774));
            pinyin.add(new PinyinCode("dong", -18773));
            pinyin.add(new PinyinCode("dou", -18763));
            pinyin.add(new PinyinCode("du", -18756));
            pinyin.add(new PinyinCode("duan", -18741));
            pinyin.add(new PinyinCode("dui", -18735));
            pinyin.add(new PinyinCode("dun", -18731));
            pinyin.add(new PinyinCode("duo", -18722));
            pinyin.add(new PinyinCode("e", -18710));
            pinyin.add(new PinyinCode("en", -18697));
            pinyin.add(new PinyinCode("er", -18696));
            pinyin.add(new PinyinCode("fa", -18526));
            pinyin.add(new PinyinCode("fan", -18518));
            pinyin.add(new PinyinCode("fang", -18501));
            pinyin.add(new PinyinCode("fei", -18490));
            pinyin.add(new PinyinCode("fen", -18478));
            pinyin.add(new PinyinCode("feng", -18463));
            pinyin.add(new PinyinCode("fo", -18448));
            pinyin.add(new PinyinCode("fou", -18447));
            pinyin.add(new PinyinCode("fu", -18446));
            pinyin.add(new PinyinCode("ga", -18239));
            pinyin.add(new PinyinCode("gai", -18237));
            pinyin.add(new PinyinCode("gan", -18231));
            pinyin.add(new PinyinCode("gang", -18220));
            pinyin.add(new PinyinCode("gao", -18211));
            pinyin.add(new PinyinCode("ge", -18201));
            pinyin.add(new PinyinCode("gei", -18184));
            pinyin.add(new PinyinCode("gen", -18183));
            pinyin.add(new PinyinCode("geng", -18181));
            pinyin.add(new PinyinCode("gong", -18012));
            pinyin.add(new PinyinCode("gou", -17997));
            pinyin.add(new PinyinCode("gu", -17988));
            pinyin.add(new PinyinCode("gua", -17970));
            pinyin.add(new PinyinCode("guai", -17964));
            pinyin.add(new PinyinCode("guan", -17961));
            pinyin.add(new PinyinCode("guang", -17950));
            pinyin.add(new PinyinCode("gui", -17947));
            pinyin.add(new PinyinCode("gun", -17931));
            pinyin.add(new PinyinCode("guo", -17928));
            pinyin.add(new PinyinCode("ha", -17922));
            pinyin.add(new PinyinCode("hai", -17759));
            pinyin.add(new PinyinCode("han", -17752));
            pinyin.add(new PinyinCode("hang", -17733));
            pinyin.add(new PinyinCode("hao", -17730));
            pinyin.add(new PinyinCode("he", -17721));
            pinyin.add(new PinyinCode("hei", -17703));
            pinyin.add(new PinyinCode("hen", -17701));
            pinyin.add(new PinyinCode("heng", -17697));
            pinyin.add(new PinyinCode("hong", -17692));
            pinyin.add(new PinyinCode("hou", -17683));
            pinyin.add(new PinyinCode("hu", -17676));
            pinyin.add(new PinyinCode("hua", -17496));
            pinyin.add(new PinyinCode("huai", -17487));
            pinyin.add(new PinyinCode("huan", -17482));
            pinyin.add(new PinyinCode("huang", -17468));
            pinyin.add(new PinyinCode("hui", -17454));
            pinyin.add(new PinyinCode("hun", -17433));
            pinyin.add(new PinyinCode("huo", -17427));
            pinyin.add(new PinyinCode("ji", -17417));
            pinyin.add(new PinyinCode("jia", -17202));
            pinyin.add(new PinyinCode("jian", -17185));
            pinyin.add(new PinyinCode("jiang", -16983));
            pinyin.add(new PinyinCode("jiao", -16970));
            pinyin.add(new PinyinCode("jie", -16942));
            pinyin.add(new PinyinCode("jin", -16915));
            pinyin.add(new PinyinCode("jing", -16733));
            pinyin.add(new PinyinCode("jiong", -16708));
            pinyin.add(new PinyinCode("jiu", -16706));
            pinyin.add(new PinyinCode("ju", -16689));
            pinyin.add(new PinyinCode("juan", -16664));
            pinyin.add(new PinyinCode("jue", -16657));
            pinyin.add(new PinyinCode("jun", -16647));
            pinyin.add(new PinyinCode("ka", -16474));
            pinyin.add(new PinyinCode("kai", -16470));
            pinyin.add(new PinyinCode("kan", -16465));
            pinyin.add(new PinyinCode("kang", -16459));
            pinyin.add(new PinyinCode("kao", -16452));
            pinyin.add(new PinyinCode("ke", -16448));
            pinyin.add(new PinyinCode("ken", -16433));
            pinyin.add(new PinyinCode("keng", -16429));
            pinyin.add(new PinyinCode("kong", -16427));
            pinyin.add(new PinyinCode("kou", -16423));
            pinyin.add(new PinyinCode("ku", -16419));
            pinyin.add(new PinyinCode("kua", -16412));
            pinyin.add(new PinyinCode("kuai", -16407));
            pinyin.add(new PinyinCode("kuan", -16403));
            pinyin.add(new PinyinCode("kuang", -16401));
            pinyin.add(new PinyinCode("kui", -16393));
            pinyin.add(new PinyinCode("kun", -16220));
            pinyin.add(new PinyinCode("kuo", -16216));
            pinyin.add(new PinyinCode("la", -16212));
            pinyin.add(new PinyinCode("lai", -16205));
            pinyin.add(new PinyinCode("lan", -16202));
            pinyin.add(new PinyinCode("lang", -16187));
            pinyin.add(new PinyinCode("lao", -16180));
            pinyin.add(new PinyinCode("le", -16171));
            pinyin.add(new PinyinCode("lei", -16169));
            pinyin.add(new PinyinCode("leng", -16158));
            pinyin.add(new PinyinCode("li", -16155));
            pinyin.add(new PinyinCode("lia", -15959));
            pinyin.add(new PinyinCode("lian", -15958));
            pinyin.add(new PinyinCode("liang", -15944));
            pinyin.add(new PinyinCode("liao", -15933));
            pinyin.add(new PinyinCode("lie", -15920));
            pinyin.add(new PinyinCode("lin", -15915));
            pinyin.add(new PinyinCode("ling", -15903));
            pinyin.add(new PinyinCode("liu", -15889));
            pinyin.add(new PinyinCode("long", -15878));
            pinyin.add(new PinyinCode("lou", -15707));
            pinyin.add(new PinyinCode("lu", -15701));
            pinyin.add(new PinyinCode("lv", -15681));
            pinyin.add(new PinyinCode("luan", -15667));
            pinyin.add(new PinyinCode("lue", -15661));
            pinyin.add(new PinyinCode("lun", -15659));
            pinyin.add(new PinyinCode("luo", -15652));
            pinyin.add(new PinyinCode("ma", -15640));
            pinyin.add(new PinyinCode("mai", -15631));
            pinyin.add(new PinyinCode("man", -15625));
            pinyin.add(new PinyinCode("mang", -15454));
            pinyin.add(new PinyinCode("mao", -15448));
            pinyin.add(new PinyinCode("me", -15436));
            pinyin.add(new PinyinCode("mei", -15435));
            pinyin.add(new PinyinCode("men", -15419));
            pinyin.add(new PinyinCode("meng", -15416));
            pinyin.add(new PinyinCode("mi", -15408));
            pinyin.add(new PinyinCode("mian", -15394));
            pinyin.add(new PinyinCode("miao", -15385));
            pinyin.add(new PinyinCode("mie", -15377));
            pinyin.add(new PinyinCode("min", -15375));
            pinyin.add(new PinyinCode("ming", -15369));
            pinyin.add(new PinyinCode("miu", -15363));
            pinyin.add(new PinyinCode("mo", -15362));
            pinyin.add(new PinyinCode("mou", -15183));
            pinyin.add(new PinyinCode("mu", -15180));
            pinyin.add(new PinyinCode("na", -15165));
            pinyin.add(new PinyinCode("nai", -15158));
            pinyin.add(new PinyinCode("nan", -15153));
            pinyin.add(new PinyinCode("nang", -15150));
            pinyin.add(new PinyinCode("nao", -15149));
            pinyin.add(new PinyinCode("ne", -15144));
            pinyin.add(new PinyinCode("nei", -15143));
            pinyin.add(new PinyinCode("nen", -15141));
            pinyin.add(new PinyinCode("neng", -15140));
            pinyin.add(new PinyinCode("ni", -15139));
            pinyin.add(new PinyinCode("nian", -15128));
            pinyin.add(new PinyinCode("niang", -15121));
            pinyin.add(new PinyinCode("niao", -15119));
            pinyin.add(new PinyinCode("nie", -15117));
            pinyin.add(new PinyinCode("nin", -15110));
            pinyin.add(new PinyinCode("ning", -15109));
            pinyin.add(new PinyinCode("niu", -14941));
            pinyin.add(new PinyinCode("nong", -14937));
            pinyin.add(new PinyinCode("nu", -14933));
            pinyin.add(new PinyinCode("nv", -14930));
            pinyin.add(new PinyinCode("nuan", -14929));
            pinyin.add(new PinyinCode("nue", -14928));
            pinyin.add(new PinyinCode("nuo", -14926));
            pinyin.add(new PinyinCode("o", -14922));
            pinyin.add(new PinyinCode("ou", -14921));
            pinyin.add(new PinyinCode("pa", -14914));
            pinyin.add(new PinyinCode("pai", -14908));
            pinyin.add(new PinyinCode("pan", -14902));
            pinyin.add(new PinyinCode("pang", -14894));
            pinyin.add(new PinyinCode("pao", -14889));
            pinyin.add(new PinyinCode("pei", -14882));
            pinyin.add(new PinyinCode("pen", -14873));
            pinyin.add(new PinyinCode("peng", -14871));
            pinyin.add(new PinyinCode("pi", -14857));
            pinyin.add(new PinyinCode("pian", -14678));
            pinyin.add(new PinyinCode("piao", -14674));
            pinyin.add(new PinyinCode("pie", -14670));
            pinyin.add(new PinyinCode("pin", -14668));
            pinyin.add(new PinyinCode("ping", -14663));
            pinyin.add(new PinyinCode("po", -14654));
            pinyin.add(new PinyinCode("pu", -14645));
            pinyin.add(new PinyinCode("qi", -14630));
            pinyin.add(new PinyinCode("qia", -14594));
            pinyin.add(new PinyinCode("qian", -14429));
            pinyin.add(new PinyinCode("qiang", -14407));
            pinyin.add(new PinyinCode("qiao", -14399));
            pinyin.add(new PinyinCode("qie", -14384));
            pinyin.add(new PinyinCode("qin", -14379));
            pinyin.add(new PinyinCode("qing", -14368));
            pinyin.add(new PinyinCode("qiong", -14355));
            pinyin.add(new PinyinCode("qiu", -14353));
            pinyin.add(new PinyinCode("qu", -14345));
            pinyin.add(new PinyinCode("quan", -14170));
            pinyin.add(new PinyinCode("que", -14159));
            pinyin.add(new PinyinCode("qun", -14151));
            pinyin.add(new PinyinCode("ran", -14149));
            pinyin.add(new PinyinCode("rang", -14145));
            pinyin.add(new PinyinCode("rao", -14140));
            pinyin.add(new PinyinCode("re", -14137));
            pinyin.add(new PinyinCode("ren", -14135));
            pinyin.add(new PinyinCode("reng", -14125));
            pinyin.add(new PinyinCode("ri", -14123));
            pinyin.add(new PinyinCode("rong", -14122));
            pinyin.add(new PinyinCode("rou", -14112));
            pinyin.add(new PinyinCode("ru", -14109));
            pinyin.add(new PinyinCode("ruan", -14099));
            pinyin.add(new PinyinCode("rui", -14097));
            pinyin.add(new PinyinCode("run", -14094));
            pinyin.add(new PinyinCode("ruo", -14092));
            pinyin.add(new PinyinCode("sa", -14090));
            pinyin.add(new PinyinCode("sai", -14087));
            pinyin.add(new PinyinCode("san", -14083));
            pinyin.add(new PinyinCode("sang", -13917));
            pinyin.add(new PinyinCode("sao", -13914));
            pinyin.add(new PinyinCode("se", -13910));
            pinyin.add(new PinyinCode("sen", -13907));
            pinyin.add(new PinyinCode("seng", -13906));
            pinyin.add(new PinyinCode("sha", -13905));
            pinyin.add(new PinyinCode("shai", -13896));
            pinyin.add(new PinyinCode("shan", -13894));
            pinyin.add(new PinyinCode("shang", -13878));
            pinyin.add(new PinyinCode("shao", -13870));
            pinyin.add(new PinyinCode("she", -13859));
            pinyin.add(new PinyinCode("shen", -13847));
            pinyin.add(new PinyinCode("sheng", -13831));
            pinyin.add(new PinyinCode("shi", -13658));
            pinyin.add(new PinyinCode("shou", -13611));
            pinyin.add(new PinyinCode("shu", -13601));
            pinyin.add(new PinyinCode("shua", -13406));
            pinyin.add(new PinyinCode("shuai", -13404));
            pinyin.add(new PinyinCode("shuan", -13400));
            pinyin.add(new PinyinCode("shuang", -13398));
            pinyin.add(new PinyinCode("shui", -13395));
            pinyin.add(new PinyinCode("shun", -13391));
            pinyin.add(new PinyinCode("shuo", -13387));
            pinyin.add(new PinyinCode("si", -13383));
            pinyin.add(new PinyinCode("song", -13367));
            pinyin.add(new PinyinCode("sou", -13359));
            pinyin.add(new PinyinCode("su", -13356));
            pinyin.add(new PinyinCode("suan", -13343));
            pinyin.add(new PinyinCode("sui", -13340));
            pinyin.add(new PinyinCode("sun", -13329));
            pinyin.add(new PinyinCode("suo", -13326));
            pinyin.add(new PinyinCode("ta", -13318));
            pinyin.add(new PinyinCode("tai", -13147));
            pinyin.add(new PinyinCode("tan", -13138));
            pinyin.add(new PinyinCode("tang", -13120));
            pinyin.add(new PinyinCode("tao", -13107));
            pinyin.add(new PinyinCode("te", -13096));
            pinyin.add(new PinyinCode("teng", -13095));
            pinyin.add(new PinyinCode("ti", -13091));
            pinyin.add(new PinyinCode("tian", -13076));
            pinyin.add(new PinyinCode("tiao", -13068));
            pinyin.add(new PinyinCode("tie", -13063));
            pinyin.add(new PinyinCode("ting", -13060));
            pinyin.add(new PinyinCode("tong", -12888));
            pinyin.add(new PinyinCode("tou", -12875));
            pinyin.add(new PinyinCode("tu", -12871));
            pinyin.add(new PinyinCode("tuan", -12860));
            pinyin.add(new PinyinCode("tui", -12858));
            pinyin.add(new PinyinCode("tun", -12852));
            pinyin.add(new PinyinCode("tuo", -12849));
            pinyin.add(new PinyinCode("wa", -12838));
            pinyin.add(new PinyinCode("wai", -12831));
            pinyin.add(new PinyinCode("wan", -12829));
            pinyin.add(new PinyinCode("wang", -12812));
            pinyin.add(new PinyinCode("wei", -12802));
            pinyin.add(new PinyinCode("wen", -12607));
            pinyin.add(new PinyinCode("weng", -12597));
            pinyin.add(new PinyinCode("wo", -12594));
            pinyin.add(new PinyinCode("wu", -12585));
            pinyin.add(new PinyinCode("xi", -12556));
            pinyin.add(new PinyinCode("xia", -12359));
            pinyin.add(new PinyinCode("xian", -12346));
            pinyin.add(new PinyinCode("xiang", -12320));
            pinyin.add(new PinyinCode("xiao", -12300));
            pinyin.add(new PinyinCode("xie", -12120));
            pinyin.add(new PinyinCode("xin", -12099));
            pinyin.add(new PinyinCode("xing", -12089));
            pinyin.add(new PinyinCode("xiong", -12074));
            pinyin.add(new PinyinCode("xiu", -12067));
            pinyin.add(new PinyinCode("xu", -12058));
            pinyin.add(new PinyinCode("xuan", -12039));
            pinyin.add(new PinyinCode("xue", -11867));
            pinyin.add(new PinyinCode("xun", -11861));
            pinyin.add(new PinyinCode("ya", -11847));
            pinyin.add(new PinyinCode("yan", -11831));
            pinyin.add(new PinyinCode("yang", -11798));
            pinyin.add(new PinyinCode("yao", -11781));
            pinyin.add(new PinyinCode("ye", -11604));
            pinyin.add(new PinyinCode("yi", -11589));
            pinyin.add(new PinyinCode("yin", -11536));
            pinyin.add(new PinyinCode("ying", -11358));
            pinyin.add(new PinyinCode("yo", -11340));
            pinyin.add(new PinyinCode("yong", -11339));
            pinyin.add(new PinyinCode("you", -11324));
            pinyin.add(new PinyinCode("yu", -11303));
            pinyin.add(new PinyinCode("yuan", -11097));
            pinyin.add(new PinyinCode("yue", -11077));
            pinyin.add(new PinyinCode("yun", -11067));
            pinyin.add(new PinyinCode("za", -11055));
            pinyin.add(new PinyinCode("zai", -11052));
            pinyin.add(new PinyinCode("zan", -11045));
            pinyin.add(new PinyinCode("zang", -11041));
            pinyin.add(new PinyinCode("zao", -11038));
            pinyin.add(new PinyinCode("ze", -11024));
            pinyin.add(new PinyinCode("zei", -11020));
            pinyin.add(new PinyinCode("zen", -11019));
            pinyin.add(new PinyinCode("zeng", -11018));
            pinyin.add(new PinyinCode("zha", -11014));
            pinyin.add(new PinyinCode("zhai", -10838));
            pinyin.add(new PinyinCode("zhan", -10832));
            pinyin.add(new PinyinCode("zhang", -10815));
            pinyin.add(new PinyinCode("zhao", -10800));
            pinyin.add(new PinyinCode("zhe", -10790));
            pinyin.add(new PinyinCode("zhen", -10780));
            pinyin.add(new PinyinCode("zheng", -10764));
            pinyin.add(new PinyinCode("zhi", -10587));
            pinyin.add(new PinyinCode("zhong", -10544));
            pinyin.add(new PinyinCode("zhou", -10533));
            pinyin.add(new PinyinCode("zhu", -10519));
            pinyin.add(new PinyinCode("zhua", -10331));
            pinyin.add(new PinyinCode("zhuai", -10329));
            pinyin.add(new PinyinCode("zhuan", -10328));
            pinyin.add(new PinyinCode("zhuang", -10322));
            pinyin.add(new PinyinCode("zhui", -10315));
            pinyin.add(new PinyinCode("zhun", -10309));
            pinyin.add(new PinyinCode("zhuo", -10307));
            pinyin.add(new PinyinCode("zi", -10296));
            pinyin.add(new PinyinCode("zong", -10281));
            pinyin.add(new PinyinCode("zou", -10274));
            pinyin.add(new PinyinCode("zu", -10270));
            pinyin.add(new PinyinCode("zuan", -10262));
            pinyin.add(new PinyinCode("zui", -10260));
            pinyin.add(new PinyinCode("zun", -10256));
            pinyin.add(new PinyinCode("zuo", -10254));
        }

        // GB2312的字符集的笔划列表，可以参考gb2312字符全集
        public static int[] gb2312StrokeCount = {
        /* B0 */
        10, 7, 10, 10, 8, 10, 9, 11, 17, 14, 13, 5, 13, 10, 12, 15, 10, 6, 10, 9, 13, 8, 10, 10, 8, 8, 10, 5, 10, 14, 16, 9, 12, 12, 15, 15, 7, 10,
                5, 5, 7, 10, 2, 9, 4, 8, 12, 13, 7, 10, 7, 21, 10, 8, 5, 9, 6, 13, 8, 8, 9, 13, 12, 10, 13, 7, 10, 10, 8, 8, 7, 8, 7, 19, 5, 4, 8, 5,
                9, 10, 14, 14, 9, 12, 15, 10, 15, 12, 12, 8, 9, 5, 15, 10,
                /* B1 */
                16, 13, 9, 12, 8, 8, 8, 7, 15, 10, 13, 19, 8, 13, 12, 8, 5, 12, 9, 4, 9, 10, 7, 8, 12, 12, 10, 8, 8, 5, 11, 11, 11, 9, 9, 18, 9, 12,
                14, 4, 13, 10, 8, 14, 13, 14, 6, 10, 9, 4, 7, 13, 6, 11, 14, 5, 13, 16, 17, 16, 9, 18, 5, 12, 8, 9, 9, 8, 4, 16, 16, 17, 12, 9, 11,
                15, 8, 19, 16, 7, 15, 11, 12, 16, 13, 10, 13, 7, 6, 9, 5, 8, 9, 9,
                /* B2 */
                10, 6, 8, 11, 15, 8, 10, 8, 12, 9, 13, 10, 14, 7, 8, 11, 11, 14, 12, 8, 7, 10, 2, 10, 7, 11, 4, 5, 7, 19, 10, 8, 17, 11, 12, 7, 3, 7,
                12, 15, 8, 11, 11, 14, 16, 8, 10, 9, 11, 11, 7, 7, 10, 4, 7, 17, 16, 16, 15, 11, 9, 8, 12, 8, 5, 9, 7, 19, 12, 3, 9, 9, 9, 14, 12,
                14, 7, 9, 8, 8, 10, 10, 12, 11, 14, 12, 11, 13, 11, 6, 11, 19, 8, 11,
                /* B3 */
                6, 9, 11, 4, 11, 7, 2, 12, 8, 11, 10, 12, 7, 9, 12, 15, 15, 11, 7, 8, 4, 7, 15, 12, 7, 15, 10, 6, 7, 6, 11, 7, 7, 7, 12, 8, 15, 10,
                9, 16, 6, 7, 10, 12, 12, 15, 8, 8, 10, 10, 10, 6, 13, 9, 11, 6, 7, 6, 6, 10, 8, 8, 4, 7, 10, 5, 9, 6, 6, 6, 11, 8, 8, 13, 12, 14, 13,
                13, 13, 4, 11, 14, 4, 10, 7, 5, 16, 12, 18, 12, 13, 12, 9, 13,
                /* B4 */
                10, 12, 24, 13, 13, 5, 12, 3, 9, 13, 7, 11, 12, 7, 9, 12, 15, 7, 6, 6, 7, 8, 11, 13, 8, 9, 13, 15, 10, 11, 7, 21, 18, 11, 11, 9, 14,
                14, 13, 13, 10, 7, 6, 8, 12, 6, 15, 12, 7, 5, 4, 5, 11, 11, 15, 17, 9, 19, 16, 12, 14, 11, 13, 10, 13, 14, 11, 14, 7, 6, 3, 14, 15,
                12, 11, 10, 13, 12, 6, 12, 14, 5, 3, 7, 4, 12, 17, 9, 9, 5, 9, 11, 9, 11,
                /* B5 */
                9, 10, 8, 4, 8, 10, 11, 9, 5, 12, 7, 11, 11, 8, 11, 11, 6, 9, 10, 9, 10, 2, 10, 17, 10, 7, 11, 6, 8, 15, 11, 12, 11, 15, 11, 8, 19,
                6, 12, 12, 17, 14, 4, 12, 7, 14, 8, 10, 11, 7, 10, 14, 14, 8, 8, 6, 12, 11, 9, 7, 10, 12, 16, 11, 13, 13, 9, 8, 16, 9, 5, 7, 7, 8,
                11, 12, 11, 13, 13, 5, 16, 10, 2, 11, 6, 8, 10, 12, 10, 14, 15, 8, 11, 13,
                /* B6 */
                2, 7, 5, 7, 8, 12, 13, 8, 4, 6, 5, 5, 12, 15, 6, 9, 8, 9, 7, 9, 11, 7, 4, 9, 7, 10, 12, 10, 13, 9, 12, 9, 10, 11, 13, 12, 7, 14, 7,
                9, 12, 7, 14, 12, 14, 9, 11, 12, 11, 7, 4, 5, 15, 7, 19, 12, 10, 7, 9, 9, 12, 11, 9, 6, 6, 9, 13, 6, 13, 11, 8, 12, 11, 13, 10, 12,
                9, 15, 6, 10, 10, 4, 7, 12, 11, 10, 10, 6, 2, 6, 5, 9, 9, 2,
                /* B7 */
                9, 5, 9, 12, 6, 4, 9, 8, 9, 18, 6, 12, 18, 15, 8, 8, 17, 3, 10, 4, 7, 8, 8, 5, 7, 7, 7, 7, 4, 8, 8, 6, 7, 6, 6, 7, 8, 11, 8, 11, 3,
                8, 10, 10, 7, 8, 8, 8, 9, 7, 11, 7, 8, 4, 7, 7, 12, 7, 10, 8, 6, 8, 12, 12, 4, 9, 8, 13, 10, 12, 4, 9, 11, 10, 5, 13, 6, 8, 4, 7, 7,
                4, 15, 8, 14, 7, 8, 13, 12, 9, 11, 6, 9, 8,
                /* B8 */
                10, 11, 13, 11, 5, 7, 7, 11, 10, 10, 8, 11, 12, 8, 14, 9, 11, 18, 12, 9, 12, 5, 8, 4, 13, 6, 12, 4, 7, 6, 13, 8, 15, 14, 8, 7, 13, 9,
                11, 12, 3, 5, 7, 9, 9, 7, 10, 13, 8, 11, 21, 4, 6, 9, 9, 7, 7, 7, 12, 7, 16, 10, 10, 14, 10, 16, 13, 15, 15, 7, 10, 14, 12, 4, 11,
                10, 8, 12, 9, 12, 10, 12, 9, 12, 11, 3, 6, 9, 10, 13, 10, 7, 8, 19,
                /* B9 */
                10, 10, 11, 3, 7, 5, 10, 11, 8, 10, 4, 9, 3, 6, 7, 9, 7, 6, 9, 4, 7, 8, 8, 9, 8, 8, 11, 12, 11, 8, 14, 7, 8, 8, 8, 13, 5, 11, 9, 7,
                8, 9, 10, 8, 12, 8, 5, 9, 14, 9, 13, 8, 8, 8, 12, 6, 8, 9, 6, 14, 11, 23, 12, 20, 8, 6, 3, 10, 13, 8, 6, 11, 5, 7, 9, 6, 9, 8, 9, 10,
                8, 13, 9, 8, 12, 13, 12, 12, 10, 8, 8, 14, 6, 9, 15, 9, 10, 10, 6, 10, 9, 12, 14, 7, 12, 7, 11, 12, 8, 12, 7, 16, 16, 10, 7, 16, 10,
                11, 6, 5, 5, 8, 10, 17, 17, 14, 11, 9, 6, 10, 5, 10, 8, 12, 10, 11, 10, 5, 8, 7, 6, 11, 13, 9, 8, 11, 14, 14, 15, 9, 15, 12, 11, 9,
                9, 9, 10, 7, 15, 16, 9, 8, 9, 10, 9, 11, 9, 7, 5, 6, 12, 9, 12, 7, 9, 10, 6, 8, 5, 8, 13, 10, 12, 9, 15, 8, 15, 12,
                /* BB */
                8, 8, 11, 7, 4, 7, 4, 7, 9, 6, 12, 12, 8, 6, 4, 8, 13, 9, 7, 11, 7, 6, 8, 10, 7, 12, 10, 11, 10, 12, 13, 11, 10, 9, 4, 9, 12, 11, 16,
                15, 17, 9, 11, 12, 13, 10, 13, 9, 11, 6, 9, 12, 17, 9, 12, 6, 13, 10, 15, 5, 12, 11, 10, 11, 6, 10, 5, 6, 9, 9, 9, 8, 11, 13, 9, 11,
                17, 9, 6, 4, 10, 8, 12, 16, 8, 11, 5, 6, 11, 6, 13, 15, 10, 14,
                /* BC */
                6, 5, 9, 16, 4, 7, 10, 11, 12, 6, 7, 12, 13, 20, 12, 3, 9, 10, 6, 7, 13, 6, 9, 2, 10, 3, 13, 7, 16, 8, 6, 11, 8, 11, 9, 11, 11, 4, 5,
                9, 7, 7, 7, 10, 6, 14, 9, 6, 8, 10, 5, 9, 12, 10, 5, 10, 11, 15, 6, 9, 8, 13, 7, 10, 7, 6, 11, 7, 13, 10, 8, 8, 6, 12, 9, 11, 9, 14,
                12, 8, 10, 13, 9, 11, 11, 9, 14, 13, 12, 9, 4, 13, 15, 6,
                /* BD */
                10, 10, 9, 8, 11, 12, 10, 8, 15, 9, 9, 10, 6, 19, 12, 10, 9, 6, 6, 13, 8, 15, 12, 17, 12, 10, 6, 8, 9, 9, 9, 20, 12, 11, 11, 8, 11,
                9, 7, 9, 16, 9, 13, 11, 14, 10, 10, 5, 12, 12, 11, 9, 11, 12, 6, 14, 7, 5, 10, 8, 11, 13, 14, 9, 9, 13, 8, 7, 17, 7, 9, 10, 4, 9, 9,
                8, 3, 12, 4, 8, 4, 9, 18, 10, 13, 4, 13, 7, 13, 10, 13, 7, 10, 10,
                /* BE */
                6, 7, 9, 14, 8, 13, 12, 16, 8, 11, 14, 13, 8, 4, 19, 12, 11, 14, 14, 12, 16, 8, 10, 13, 11, 10, 8, 9, 12, 12, 7, 5, 7, 9, 3, 7, 2,
                10, 11, 11, 5, 6, 13, 8, 12, 8, 17, 8, 8, 10, 8, 8, 11, 7, 8, 9, 9, 8, 14, 7, 11, 4, 8, 11, 15, 13, 10, 5, 11, 8, 10, 10, 12, 10, 10,
                11, 8, 10, 15, 23, 7, 11, 10, 17, 9, 6, 6, 9, 7, 11, 9, 6, 7, 10,
                /* BF */
                9, 12, 10, 9, 10, 12, 8, 5, 9, 4, 12, 13, 8, 12, 5, 12, 11, 7, 9, 9, 11, 14, 17, 6, 7, 4, 8, 6, 9, 10, 15, 8, 8, 9, 12, 15, 14, 9, 7,
                9, 5, 12, 7, 8, 9, 10, 8, 11, 9, 10, 7, 7, 8, 10, 4, 11, 7, 3, 6, 11, 9, 10, 13, 8, 14, 7, 12, 6, 9, 9, 13, 10, 7, 13, 8, 7, 10, 12,
                6, 12, 7, 10, 8, 11, 7, 7, 3, 11, 8, 13, 12, 9, 13, 11,
                /* C0 */
                12, 12, 12, 8, 8, 10, 7, 9, 6, 13, 12, 8, 8, 12, 14, 12, 14, 11, 10, 7, 13, 13, 11, 9, 8, 16, 12, 5, 15, 14, 12, 9, 16, 12, 9, 13,
                11, 12, 10, 11, 8, 10, 10, 10, 7, 7, 6, 8, 9, 13, 10, 10, 11, 5, 13, 18, 16, 15, 11, 17, 9, 16, 6, 9, 8, 12, 13, 7, 9, 11, 11, 15,
                16, 10, 10, 13, 11, 7, 7, 15, 5, 10, 9, 6, 10, 7, 5, 7, 10, 4, 7, 12, 8, 9,
                /* C1 */
                12, 5, 11, 7, 8, 2, 14, 10, 9, 12, 10, 7, 18, 13, 8, 10, 8, 11, 11, 12, 10, 9, 8, 13, 10, 11, 13, 7, 7, 11, 12, 12, 9, 10, 15, 11,
                14, 7, 16, 14, 5, 15, 2, 14, 17, 14, 10, 6, 12, 10, 6, 11, 12, 8, 17, 16, 9, 7, 20, 11, 15, 10, 7, 8, 9, 11, 13, 13, 10, 7, 11, 10,
                7, 10, 8, 11, 5, 5, 13, 11, 14, 12, 13, 10, 6, 15, 10, 9, 4, 5, 11, 8, 11, 16,
                /* C2 */
                11, 8, 8, 7, 13, 9, 12, 15, 14, 8, 7, 5, 11, 7, 8, 11, 7, 8, 12, 19, 13, 21, 13, 10, 11, 16, 12, 8, 7, 15, 7, 6, 11, 8, 10, 15, 12,
                12, 10, 12, 9, 11, 13, 11, 9, 10, 9, 13, 7, 7, 11, 11, 7, 8, 6, 4, 7, 7, 6, 11, 17, 8, 11, 13, 14, 14, 13, 12, 9, 9, 9, 6, 11, 7, 8,
                9, 3, 9, 14, 6, 10, 6, 7, 8, 6, 9, 15, 14, 12, 13, 14, 11, 14, 14,
                /* C3 */
                13, 6, 9, 8, 8, 6, 10, 11, 8, 13, 4, 5, 10, 5, 8, 9, 12, 14, 9, 3, 8, 8, 11, 14, 15, 13, 7, 9, 12, 14, 7, 9, 9, 12, 8, 12, 3, 7, 5,
                11, 13, 17, 13, 13, 11, 11, 8, 11, 15, 19, 17, 9, 11, 8, 6, 10, 8, 8, 14, 11, 12, 12, 10, 11, 11, 7, 9, 10, 12, 9, 8, 11, 13, 17, 9,
                12, 8, 7, 14, 5, 5, 8, 5, 11, 10, 9, 8, 16, 8, 11, 6, 8, 13, 13,
                /* C4 */
                14, 19, 14, 14, 16, 15, 20, 8, 5, 10, 15, 16, 8, 13, 13, 8, 11, 6, 9, 8, 7, 7, 8, 5, 13, 14, 13, 12, 14, 4, 5, 13, 8, 16, 10, 9, 7,
                9, 6, 9, 7, 6, 2, 5, 9, 8, 9, 7, 10, 22, 9, 10, 9, 8, 11, 8, 10, 4, 14, 10, 8, 16, 10, 8, 5, 7, 7, 10, 13, 9, 13, 14, 8, 6, 15, 15,
                11, 8, 10, 14, 5, 7, 10, 10, 19, 11, 15, 15, 10, 11, 9, 8, 16, 5,
                /* C5 */
                8, 8, 4, 7, 9, 7, 10, 9, 6, 7, 5, 7, 9, 3, 13, 9, 8, 9, 17, 20, 10, 10, 8, 9, 8, 18, 7, 11, 7, 11, 9, 8, 8, 8, 12, 8, 11, 12, 11, 12,
                9, 19, 15, 11, 15, 9, 10, 7, 9, 6, 8, 10, 16, 9, 7, 8, 7, 9, 10, 12, 8, 8, 9, 11, 14, 12, 10, 10, 8, 7, 12, 9, 10, 8, 11, 15, 12, 13,
                12, 13, 16, 16, 8, 13, 11, 13, 8, 9, 21, 7, 8, 15, 12, 9,
                /* C6 */
                11, 12, 10, 5, 4, 12, 15, 7, 20, 15, 11, 4, 12, 15, 14, 16, 11, 14, 16, 9, 13, 8, 9, 13, 6, 8, 8, 11, 5, 8, 10, 7, 9, 8, 8, 11, 11,
                10, 14, 8, 11, 10, 5, 12, 4, 10, 12, 11, 13, 10, 6, 10, 12, 10, 14, 19, 18, 12, 12, 10, 11, 8, 2, 10, 14, 9, 7, 8, 12, 8, 8, 11, 11,
                10, 6, 14, 8, 6, 11, 10, 6, 3, 6, 7, 9, 9, 16, 4, 6, 7, 7, 8, 5, 11,
                /* C7 */
                9, 9, 9, 6, 8, 10, 3, 6, 13, 5, 12, 11, 16, 10, 10, 9, 15, 13, 8, 15, 11, 12, 4, 14, 8, 7, 12, 7, 14, 14, 12, 7, 16, 14, 14, 10, 10,
                17, 6, 8, 5, 16, 15, 12, 10, 9, 10, 4, 8, 5, 8, 9, 9, 9, 9, 10, 12, 13, 7, 15, 12, 13, 7, 8, 9, 9, 10, 10, 11, 16, 12, 12, 11, 8, 10,
                6, 12, 7, 9, 5, 7, 11, 7, 5, 9, 8, 12, 4, 11, 6, 11, 8, 7, 11,
                /* C8 */
                8, 11, 17, 15, 5, 11, 23, 6, 16, 10, 6, 11, 10, 4, 8, 4, 10, 8, 16, 7, 13, 14, 12, 11, 12, 13, 12, 16, 5, 9, 22, 20, 20, 20, 5, 9, 7,
                9, 12, 10, 4, 4, 2, 7, 7, 6, 4, 3, 7, 6, 5, 4, 4, 6, 9, 13, 9, 16, 14, 13, 10, 9, 4, 12, 9, 6, 9, 20, 16, 17, 6, 10, 8, 6, 2, 15, 8,
                6, 15, 13, 12, 7, 10, 8, 10, 15, 9, 11, 13, 17, 13, 14, 3, 8,
                /* C9 */
                6, 12, 10, 13, 8, 12, 12, 6, 12, 13, 6, 10, 12, 14, 10, 9, 6, 8, 7, 7, 13, 11, 13, 12, 10, 9, 8, 7, 3, 7, 14, 8, 5, 8, 16, 17, 16,
                12, 6, 10, 15, 14, 6, 11, 12, 10, 3, 8, 14, 11, 10, 12, 10, 6, 3, 14, 4, 10, 7, 8, 11, 11, 11, 6, 8, 11, 13, 10, 13, 10, 7, 6, 10, 5,
                8, 7, 7, 11, 10, 8, 9, 7, 8, 11, 9, 8, 13, 11, 7, 5, 12, 9, 4, 11,
                /* CA */
                9, 11, 12, 9, 5, 6, 5, 9, 9, 12, 8, 3, 8, 2, 5, 9, 7, 4, 9, 9, 8, 7, 5, 5, 8, 9, 8, 8, 6, 5, 3, 5, 9, 8, 9, 14, 10, 8, 9, 13, 16, 9,
                5, 8, 12, 8, 4, 5, 9, 9, 8, 8, 6, 4, 9, 6, 7, 11, 11, 8, 14, 11, 15, 8, 11, 10, 7, 13, 8, 12, 11, 12, 4, 12, 11, 15, 16, 12, 17, 13,
                13, 12, 13, 12, 5, 8, 9, 7, 6, 9, 14, 11, 13, 14,
                /* CB */
                10, 8, 9, 14, 10, 5, 5, 10, 9, 17, 4, 11, 10, 4, 13, 12, 7, 17, 9, 12, 9, 11, 10, 9, 12, 15, 15, 9, 7, 5, 5, 6, 13, 6, 13, 5, 7, 6,
                8, 3, 8, 10, 8, 10, 9, 7, 6, 9, 12, 15, 16, 14, 7, 12, 9, 10, 10, 12, 14, 13, 13, 11, 7, 8, 14, 13, 14, 9, 11, 11, 10, 21, 13, 6, 17,
                12, 14, 10, 6, 10, 10, 13, 11, 10, 14, 11, 10, 12, 8, 13, 5, 5, 6, 12,
                /* CC */
                16, 9, 17, 15, 9, 8, 8, 5, 10, 11, 4, 8, 7, 7, 13, 8, 15, 13, 7, 17, 13, 15, 14, 10, 8, 12, 10, 14, 11, 5, 9, 6, 13, 13, 11, 12, 15,
                10, 16, 10, 15, 11, 15, 10, 11, 10, 13, 10, 11, 10, 9, 11, 10, 5, 10, 10, 18, 13, 10, 13, 11, 10, 15, 12, 12, 15, 16, 12, 7, 12, 17,
                11, 10, 9, 8, 4, 11, 13, 5, 11, 9, 14, 12, 9, 7, 8, 11, 13, 9, 10, 8, 4, 7, 9,
                /* CD */
                5, 6, 11, 9, 9, 9, 12, 10, 10, 13, 17, 6, 11, 7, 12, 11, 10, 12, 9, 12, 11, 7, 5, 10, 5, 7, 9, 8, 10, 10, 10, 11, 3, 6, 8, 12, 6, 11,
                13, 13, 13, 14, 9, 7, 4, 17, 8, 6, 11, 10, 7, 6, 8, 12, 7, 8, 12, 9, 9, 12, 9, 9, 4, 10, 9, 5, 15, 9, 12, 8, 10, 3, 11, 7, 13, 10,
                11, 12, 11, 8, 11, 3, 12, 7, 4, 3, 8, 6, 8, 8, 11, 7, 6, 9,
                /* CE */
                20, 13, 6, 4, 7, 10, 7, 11, 11, 4, 14, 11, 7, 11, 8, 6, 6, 7, 7, 5, 14, 8, 9, 9, 12, 17, 7, 12, 11, 11, 15, 3, 14, 12, 10, 4, 9, 7,
                7, 14, 10, 6, 13, 10, 8, 9, 13, 10, 12, 7, 14, 8, 12, 7, 7, 7, 9, 4, 6, 9, 9, 4, 7, 11, 7, 7, 4, 8, 4, 10, 4, 14, 6, 9, 7, 5, 13, 11,
                8, 4, 5, 10, 9, 8, 14, 8, 6, 11, 8, 12, 15, 6, 13, 10,
                /* CF */
                12, 10, 7, 11, 15, 3, 11, 14, 11, 13, 6, 12, 17, 11, 10, 3, 13, 12, 11, 9, 7, 12, 6, 8, 15, 9, 7, 17, 14, 13, 9, 8, 9, 3, 12, 10, 6,
                11, 13, 6, 5, 14, 6, 9, 8, 11, 11, 7, 9, 8, 13, 9, 9, 8, 13, 7, 13, 11, 12, 9, 10, 8, 8, 9, 11, 22, 9, 15, 17, 12, 3, 12, 10, 8, 13,
                9, 8, 9, 9, 15, 13, 6, 11, 11, 12, 15, 9, 10, 18, 12, 10, 10, 11, 10,
                /* D0 */
                3, 7, 10, 7, 11, 10, 10, 13, 8, 13, 15, 15, 6, 9, 13, 6, 11, 8, 11, 5, 11, 9, 19, 16, 8, 8, 12, 10, 16, 7, 12, 8, 7, 13, 7, 4, 9, 11,
                9, 13, 12, 12, 6, 6, 9, 7, 6, 6, 16, 8, 7, 8, 8, 5, 4, 10, 6, 7, 12, 14, 6, 9, 10, 6, 13, 12, 7, 10, 10, 14, 6, 14, 11, 14, 9, 10, 6,
                13, 11, 9, 6, 7, 10, 9, 12, 12, 11, 11, 7, 12, 9, 11, 11, 5,
                /* D1 */
                9, 19, 10, 9, 13, 16, 8, 5, 11, 6, 9, 14, 12, 6, 8, 6, 6, 6, 10, 6, 5, 5, 9, 6, 6, 8, 9, 10, 7, 3, 7, 4, 10, 11, 13, 11, 12, 9, 6, 6,
                11, 9, 11, 10, 11, 10, 7, 9, 12, 8, 7, 7, 15, 11, 8, 8, 8, 11, 11, 9, 14, 10, 12, 16, 6, 9, 12, 10, 9, 12, 10, 11, 10, 9, 5, 10, 10,
                7, 6, 8, 8, 6, 9, 6, 10, 6, 11, 9, 10, 14, 16, 13, 7, 14,
                /* D2 */
                13, 6, 13, 11, 12, 9, 9, 10, 9, 9, 20, 12, 15, 8, 6, 11, 7, 3, 6, 11, 5, 5, 6, 12, 8, 11, 1, 12, 7, 12, 11, 8, 6, 6, 13, 6, 12, 11,
                5, 10, 14, 7, 8, 9, 18, 12, 9, 10, 3, 1, 7, 4, 4, 7, 8, 7, 6, 3, 7, 17, 11, 13, 9, 6, 13, 13, 15, 4, 3, 10, 13, 8, 5, 10, 7, 6, 17,
                11, 8, 9, 9, 6, 10, 9, 6, 8, 7, 11, 11, 11, 7, 4, 4, 11,
                /* D3 */
                5, 8, 15, 11, 18, 7, 14, 10, 11, 11, 9, 14, 7, 17, 9, 15, 13, 12, 9, 9, 8, 7, 17, 10, 11, 13, 14, 13, 8, 8, 10, 5, 11, 9, 5, 9, 6,
                11, 7, 4, 5, 7, 10, 7, 8, 12, 7, 6, 4, 5, 7, 12, 9, 2, 5, 6, 11, 3, 8, 13, 13, 13, 14, 7, 9, 12, 8, 12, 12, 11, 11, 4, 10, 8, 3, 6,
                9, 6, 9, 6, 5, 11, 6, 8, 6, 12, 12, 10, 12, 13, 11, 9, 8, 13,
                /* D4 */
                10, 12, 12, 10, 15, 5, 10, 11, 10, 4, 9, 10, 10, 12, 14, 7, 7, 10, 13, 13, 12, 7, 8, 14, 9, 9, 4, 6, 12, 11, 9, 8, 12, 4, 10, 10, 10,
                4, 9, 4, 9, 4, 7, 15, 11, 10, 13, 5, 5, 10, 6, 10, 9, 7, 10, 10, 6, 6, 9, 19, 12, 16, 10, 10, 12, 14, 17, 12, 19, 8, 6, 16, 9, 20,
                16, 10, 7, 7, 17, 8, 8, 6, 8, 10, 9, 15, 15, 12, 16, 4, 12, 12, 5, 5,
                /* D5 */
                11, 8, 9, 9, 14, 8, 5, 9, 7, 14, 10, 6, 10, 10, 14, 18, 9, 13, 11, 8, 10, 8, 14, 11, 10, 22, 9, 5, 9, 10, 12, 11, 15, 11, 14, 14, 7,
                12, 10, 7, 3, 7, 8, 5, 8, 16, 13, 8, 9, 7, 8, 9, 13, 13, 6, 14, 5, 14, 7, 10, 12, 16, 8, 13, 14, 7, 10, 9, 13, 10, 13, 10, 16, 6, 7,
                8, 8, 10, 7, 15, 10, 15, 6, 13, 9, 11, 8, 9, 6, 8, 16, 9, 5, 9,
                /* D6 */
                9, 10, 8, 7, 6, 8, 4, 7, 14, 8, 8, 10, 5, 3, 8, 11, 8, 12, 12, 6, 10, 8, 7, 9, 4, 11, 5, 6, 7, 7, 10, 11, 6, 10, 13, 8, 9, 8, 12, 10,
                13, 8, 8, 11, 12, 8, 11, 4, 9, 8, 9, 10, 8, 9, 8, 9, 6, 6, 6, 8, 6, 9, 7, 12, 9, 7, 8, 8, 10, 8, 9, 17, 10, 10, 12, 6, 11, 10, 8, 10,
                6, 10, 12, 8, 17, 15, 5, 11, 9, 7, 11, 8, 12, 12,
                /* D7 */
                7, 8, 9, 8, 7, 4, 9, 4, 9, 8, 15, 14, 15, 10, 6, 12, 6, 15, 6, 7, 12, 13, 9, 14, 7, 11, 10, 10, 10, 8, 8, 10, 12, 8, 10, 11, 11, 7,
                9, 9, 9, 10, 9, 12, 11, 7, 12, 5, 9, 13, 3, 6, 11, 6, 18, 12, 15, 8, 11, 9, 7, 7, 7, 9, 12, 10, 7, 8, 11, 9, 7, 7, 8, 10, 20, 16, 15,
                12, 13, 12, 15, 9, 5, 7, 9, 11, 7, 7, 10, 0, 0, 0, 0, 0,
                /* D8 */
                3, 3, 3, 4, 4, 4, 5, 6, 6, 10, 10, 16, 1, 8, 1, 2, 3, 4, 4, 5, 5, 6, 9, 11, 14, 14, 19, 1, 8, 14, 2, 6, 4, 7, 7, 11, 14, 4, 6, 10,
                11, 12, 14, 15, 16, 2, 5, 8, 11, 11, 15, 8, 7, 2, 4, 6, 7, 8, 8, 8, 9, 10, 10, 10, 13, 13, 14, 14, 15, 16, 2, 8, 2, 4, 4, 4, 5, 5, 5,
                5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7,
                /* D9 */
                7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11,
                11, 11, 11, 11, 11, 11, 12, 12, 12, 13, 14, 14, 14, 14, 14, 14, 15, 15, 5, 6, 7, 7, 9, 17, 6, 8, 4, 12, 16, 17, 18, 21, 2, 9, 9, 11,
                6, 6, 7, 2, 8, 10, 10, 11, 12, 12, 12, 13, 16, 19, 19, 2, 6, 8, 8,
                /* DA */
                10, 2, 10, 10, 2, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 11,
                11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 13, 13, 14, 14, 14, 15, 15, 19, 2, 8, 2, 5, 5, 6, 6, 7, 7, 7, 7, 8, 9, 9,
                10, 10, 10, 11, 11, 11, 16, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7,
                /* DB */
                7, 7, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 10, 10, 11, 11, 13, 13, 13, 14, 14, 16, 19, 17, 5, 7, 5, 7, 7, 8, 10, 10, 11, 15, 9, 17,
                20, 2, 2, 6, 10, 2, 5, 10, 12, 7, 9, 9, 14, 16, 16, 17, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11,
                /* DC */
                11, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 14, 14, 14, 15, 20, 21, 22, 3, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
                7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
                /* DD */
                9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11,
                11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14,
                /* DE */
                14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 19, 19, 19, 20, 20,
                22, 3, 9, 6, 7, 9, 9, 10, 10, 11, 3, 5, 5, 12, 3, 6, 7, 8, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
                11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14,
                /* DF */
                14, 15, 15, 15, 15, 16, 16, 16, 17, 17, 19, 23, 25, 3, 7, 8, 12, 5, 5, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8,
                8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
                /* E0 */
                11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13,
                13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
                16, 16, 16, 16, 16, 16, 17, 17, 19, 25, 3, 6, 6, 7, 7, 8, 9, 10, 11, 11, 16, 7, 8, 8, 8, 10, 11, 11,
                /* E1 */
                11, 12, 14, 14, 15, 15, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 11, 11, 11, 11, 11, 11,
                11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 14, 15, 15, 17, 17, 19, 3, 7, 8, 9, 9, 9, 10, 11, 11, 12, 13, 15, 16, 24, 3,
                3, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10,
                /* E2 */
                10, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 14, 14, 15, 15, 16, 17, 20, 6, 14, 12, 14, 3, 3, 6, 7, 7, 7, 7, 7, 8, 9, 10,
                10, 11, 12, 12, 13, 13, 14, 15, 15, 25, 5, 7, 7, 8, 9, 9, 11, 11, 11, 11, 12, 13, 14, 15, 16, 16, 17, 3, 5, 6, 6, 7, 7, 7, 7, 7, 7,
                7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9,
                /* E3 */
                9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 13, 13, 14, 15, 15, 15, 16, 16, 18,
                8, 17, 4, 6, 7, 7, 7, 7, 9, 9, 10, 10, 10, 11, 11, 11, 11, 11, 11, 12, 12, 13, 13, 13, 14, 3, 4, 8, 3, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7,
                7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
                /* E4 */
                9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11,
                11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
                13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 16,
                /* E5 */
                16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 19, 19, 19, 20, 20, 21, 24, 3, 5, 8, 8, 9, 10, 12, 13, 14, 14, 15, 16, 16, 17, 17, 3, 7, 7,
                8, 8, 8, 8, 8, 8, 8, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 13, 15, 15, 16, 16, 17, 17, 18, 3, 11,
                9, 12, 5, 9, 10, 10, 12, 14, 15, 21, 8, 8, 9, 11, 12, 22, 3, 6, 6, 7, 7, 7, 7,
                /* E6 */
                7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 13,
                13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 15, 16, 16, 17, 17, 20, 5, 9, 7, 8, 12, 3, 3, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 10,
                11, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 15, 19, 20, 3, 6, 6, 6, 6, 6,
                /* E7 */
                7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 15, 15, 15, 16, 16, 16, 16, 19, 3, 15, 3, 8, 10, 6, 6, 8, 8, 8, 9, 9, 9, 9,
                9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12,
                /* E8 */
                12, 12, 13, 13, 13, 13, 13, 14, 14, 15, 15, 15, 15, 15, 15, 15, 16, 17, 17, 17, 18, 20, 20, 13, 13, 14, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8,
                8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12,
                /* E9 */
                12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14,
                14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 18, 13, 14, 8, 9,
                9, 9, 11, 11, 11, 12, 12, 14, 16, 7, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 11, 12, 12,
                /* EA */
                12, 12, 13, 15, 16, 10, 5, 8, 11, 12, 12, 13, 13, 13, 14, 14, 8, 9, 12, 16, 16, 17, 4, 6, 6, 7, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9,
                9, 10, 10, 10, 10, 10, 10, 11, 11, 12, 13, 13, 14, 14, 16, 18, 18, 20, 21, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 12, 12, 14, 9, 10,
                11, 12, 13, 14, 15, 15, 9, 13, 6, 8, 9, 11, 11, 12, 12, 12, 13, 14, 10, 11, 12,
                /* EB */
                14, 17, 10, 10, 12, 12, 12, 13, 15, 16, 16, 22, 5, 6, 7, 7, 9, 10, 10, 11, 13, 4, 11, 13, 12, 13, 15, 9, 15, 6, 7, 7, 7, 8, 8, 8, 8,
                8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12,
                12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 15, 15, 16, 17, 17, 17, 17,
                /* EC */
                17, 16, 7, 11, 12, 13, 13, 16, 9, 9, 12, 13, 16, 16, 4, 13, 13, 17, 12, 15, 16, 8, 10, 10, 10, 11, 11, 13, 14, 7, 8, 8, 8, 9, 9, 9,
                9, 9, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 14, 15, 15, 15, 15, 16, 16, 16, 18, 21, 30, 4, 11, 13, 16, 8, 8, 9,
                11, 12, 4, 7, 8, 8, 9, 9, 9, 9, 9, 9, 9, 10, 10, 12, 12, 13, 14, 16, 21, 7, 7,
                /* ED */
                9, 10, 10, 10, 10, 10, 10, 11, 13, 13, 14, 16, 16, 17, 17, 24, 4, 6, 8, 9, 12, 7, 8, 8, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10,
                10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 12, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 15, 15, 15, 16, 16, 17, 17, 18, 19, 18,
                21, 11, 12, 17, 19, 8, 9, 9, 9, 9, 9, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13,
                /* EE */
                13, 13, 14, 14, 14, 14, 15, 15, 16, 16, 16, 17, 18, 7, 8, 9, 9, 9, 10, 12, 13, 17, 9, 10, 10, 12, 13, 14, 14, 16, 17, 17, 10, 16, 23,
                5, 6, 6, 7, 7, 7, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
                /* EF */
                11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13,
                13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 17,
                17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 18, 18, 19, 20, 14, 9, 12, 13, 9, 9, 10, 10, 11, 12, 12, 12, 13, 13,
                /* F0 */
                15, 15, 16, 17, 18, 22, 9, 11, 12, 13, 17, 10, 11, 7, 7, 8, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12,
                13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 17, 18, 18, 22, 5, 7, 7, 8, 8, 9, 9, 10, 10, 10, 10, 10, 10,
                10, 10, 11, 11, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14,
                /* F1 */
                15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 17, 18, 18, 18, 18, 21, 23, 11, 12, 8, 8, 9, 9, 10, 11, 13, 13, 14, 14, 14, 15, 5, 8,
                9, 9, 9, 9, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 15, 15, 16, 17, 19, 24, 5, 9, 11, 12, 9,
                6, 9, 10, 12, 12, 13, 14, 15, 15, 16, 16, 22, 12, 8, 11, 11, 11, 12, 15, 16, 12, 9, 10, 10,
                /* F2 */
                12, 12, 12, 12, 13, 15, 15, 16, 16, 16, 18, 20, 21, 6, 10, 7, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11,
                11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14,
                14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16,
                /* F3 */
                16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 18, 18, 18, 19, 19, 19, 19, 20, 21, 24, 26, 6, 14, 17, 17,
                10, 8, 9, 9, 9, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13,
                14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 17,
                /* F4 */
                18, 18, 18, 19, 19, 19, 8, 9, 11, 12, 10, 10, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 13, 13, 14, 15, 17, 18, 19, 10, 10, 11,
                13, 13, 19, 11, 11, 13, 15, 15, 16, 9, 10, 10, 11, 11, 12, 12, 13, 14, 14, 14, 15, 15, 15, 15, 15, 16, 18, 6, 15, 9, 11, 12, 14, 14,
                15, 15, 16, 17, 6, 12, 14, 14, 17, 25, 11, 19, 9, 12, 13, 13, 23, 11, 15, 10, 11, 9, 10, 10, 10, 12,
                /* F5 */
                12, 12, 13, 13, 13, 14, 14, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 18, 19, 19, 19, 20, 20, 21, 7, 16, 10, 13, 14, 18, 18, 10, 10,
                11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16,
                16, 16, 16, 17, 17, 17, 19, 19, 19, 19, 19, 20, 21, 22, 22, 23, 24, 7, 12, 13, 13, 17, 17, 11, 11, 12, 12, 13,
                /* F6 */
                13, 14, 15, 13, 18, 12, 11, 12, 12, 14, 14, 16, 16, 16, 19, 19, 20, 22, 10, 13, 13, 13, 14, 14, 15, 15, 17, 8, 12, 20, 8, 10, 10, 13,
                14, 18, 18, 14, 14, 15, 16, 17, 18, 18, 21, 24, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15,
                15, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 17, 17, 17,
                /* F7 */
                18, 18, 18, 18, 18, 19, 19, 19, 19, 19, 19, 20, 20, 20, 21, 14, 14, 15, 15, 16, 18, 18, 18, 19, 19, 13, 13, 14, 14, 14, 15, 15, 17,
                17, 18, 18, 19, 19, 22, 14, 14, 15, 16, 16, 17, 19, 12, 15, 18, 22, 22, 10, 13, 14, 15, 15, 16, 16, 16, 18, 19, 20, 23, 25, 14, 15,
                17, 13, 16, 16, 17, 19, 19, 21, 23, 17, 17, 17, 18, 18, 19, 20, 20, 20, 20, 21, 17, 18, 20, 23, 23, 16, 17, 23,
        /* F8 */
        };
    }
}
