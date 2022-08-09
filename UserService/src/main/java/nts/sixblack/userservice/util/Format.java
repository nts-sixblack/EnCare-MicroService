package nts.sixblack.userservice.util;

import nts.sixblack.userservice.config.RegexConfig;

public class Format {

    public static boolean phoneNotCorrectFormat(String phone) {
        return !phone.matches(RegexConfig.phone);
    }

    public static boolean otpNotCorrectFormat(String otp) {
        return !otp.matches(RegexConfig.otp);
    }

    public static boolean dayNotCorrectFormat(String day) {
        return !day.matches(RegexConfig.day);
    }

}
