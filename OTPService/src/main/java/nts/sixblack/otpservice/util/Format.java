package nts.sixblack.otpservice.util;

import nts.sixblack.otpservice.config.RegexConfig;

public class Format {

    public static boolean phoneNotCorrectFormat(String phone) {
        return !phone.matches(RegexConfig.phone);
    }

    public static boolean otpNotCorrectFormat(String otp) {
        return !otp.matches(RegexConfig.otp);
    }

}
