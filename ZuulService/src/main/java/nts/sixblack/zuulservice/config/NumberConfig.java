package nts.sixblack.zuulservice.config;

public class NumberConfig {
    public static float round(float number){
        return Math.round(number*100f)/100f;
    }
}
