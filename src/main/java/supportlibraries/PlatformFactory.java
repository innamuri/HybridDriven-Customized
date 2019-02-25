package supportlibraries;

import org.openqa.selenium.Platform;

public class PlatformFactory {
	/**
	 * Function to return the appropriate {@link Platform} object based on the
	 * platform name passed
	 * 
	 * @param platformName
	 *            The name of the platform
	 * @return The corresponding {@link Platform} object
	 */
	public static Platform getPlatform(String platformName) {
		Platform platform = null;

		if (platformName.equalsIgnoreCase("windows"))
			platform = Platform.WINDOWS;
		if (platformName.equalsIgnoreCase("android"))
			platform = Platform.ANDROID;
		if (platformName.equalsIgnoreCase("any"))
			platform = Platform.ANY;
		if (platformName.equalsIgnoreCase("xp"))
			platform = Platform.XP;
		if (platformName.equalsIgnoreCase("vista"))
			platform = Platform.VISTA;
		if (platformName.equalsIgnoreCase("unix"))
			platform = Platform.UNIX;
		if (platformName.equalsIgnoreCase("mac"))
			platform = Platform.MAC;
		if (platformName.equalsIgnoreCase("linux"))
			platform = Platform.LINUX;

		return platform;
	}
}