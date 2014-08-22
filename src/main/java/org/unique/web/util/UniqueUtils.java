package org.unique.web.util;

import java.util.ArrayList;
import java.util.List;


public class UniqueUtils {

    public static final String ALL_PATHS = "+/*paths";

    private UniqueUtils() {
    }

    public static List<String> convertRouteToList(String route) {
        String[] pathArray = route.split("/");
        List<String> path = new ArrayList<String>();
        for (String p : pathArray) {
            if (p.length() > 0) {
                path.add(p);
            }
        }
        return path;
    }

    public static boolean isParam(String routePart) {
        return routePart.startsWith(":");
    }

    public static boolean isSplat(String routePart) {
        return routePart.equals("*");
    }
    
}
