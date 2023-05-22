package com.irrelvnt.nsync;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    public static List<String> extractValuesFromString(String input) {
        List<String> values = new ArrayList<>();

        input = input.replace("StreamInfoItem", "").replace("{", "").replace("}", "");
        String[] pairs = input.split(",");

        // Extract the desired values
        for (String pair : pairs) {
            // Split each pair by the equal sign
            String[] keyValue = pair.trim().split("=");

            if (keyValue.length == 2) {
                // Check for the desired keys and add their values to the list
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                if (key.equals("uploaderName") || key.equals("name") || key.equals("url") || key.equals("thumbnailUrl")) {
                    values.add(value);
                }
            }
        }
        return values;
    }
}
