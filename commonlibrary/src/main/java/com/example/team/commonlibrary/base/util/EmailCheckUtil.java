package com.example.team.commonlibrary.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 邹特强 on 2018/4/2.
 * 检查邮箱地址是否有效的工具类
 * @author 邹特强
 */

public class EmailCheckUtil {
    private static Pattern EMAILPATTERN = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    public static boolean checkEmail(String emailAddress) {
        if (null == emailAddress || "".equals(emailAddress)) {
            return false;
        }
        Matcher m = EMAILPATTERN.matcher(emailAddress);
        return m.matches();
    }
}
