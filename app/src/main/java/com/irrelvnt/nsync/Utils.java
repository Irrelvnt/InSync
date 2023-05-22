package com.irrelvnt.nsync;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
    public static List<String> extractValuesFromString(String input) {
        List<String> values = new ArrayList<>();

        // Remove the "StreamInfoItem" prefix and curly braces
        input = input.replace("StreamInfoItem", "").replace("{", "").replace("}", "");

        // Use regular expressions to extract the desired values
        Pattern pattern = Pattern.compile("(uploaderName|name|url|thumbnailUrl)='([^']*)'");
        Matcher matcher = pattern.matcher(input);

        // Find matches and add the captured values to the list
        while (matcher.find()) {
            values.add(matcher.group(2));
        }

        return values;
    }
}
