package com.foodaclic.livraison.utils;

import java.util.regex.Pattern;

/**
 * Created by cyrilleguipie on 6/2/16.
 */

public class PhoneUtils {

    private static final String EMAIL_PATTERN = "^[+]?[0-9]*$";


    public static boolean validate(String s)
    {
        return Pattern.compile(EMAIL_PATTERN).matcher(s).matches();
    }
}
