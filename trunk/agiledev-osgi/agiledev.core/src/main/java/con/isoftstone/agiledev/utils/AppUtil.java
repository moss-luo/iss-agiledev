package con.isoftstone.agiledev.utils;

import java.io.File;

public class AppUtil {

	public static String getAppHome() {
        String appHome = System.getProperty("user.dir");

        if (appHome == null) {
            appHome = new File(".").getAbsolutePath();
            if (appHome.endsWith("/.")) {
                appHome = appHome.substring(0, appHome.length() - 2);
            }
        }

        if (appHome == null || "".equals(appHome)) {
            throw new RuntimeException("Can't determine application home.");
        }

        if (appHome.endsWith("/")) {
            appHome = appHome.substring(0, appHome.length() - 1);
        }

        return appHome;
    }
}
