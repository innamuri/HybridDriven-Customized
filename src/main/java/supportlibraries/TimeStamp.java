package supportlibraries;

import java.io.File;

import com.fm.craft.*;

/**
 * Singleton class which manages the creation of timestamped result folders for
 * every test batch execution
 */
public class TimeStamp {
	private static String timeStamp;

	/**
	 * Function to return the timestamped result folder path
	 * 
	 * @return The timestamped result folder path
	 */
	public static synchronized String getInstance() {
		if (timeStamp == null) {
			FrameworkParameters frameworkParameters = FrameworkParameters
					.getInstance();

			timeStamp = frameworkParameters.getRunConfiguration()
					+ frameworkParameters.fileSeparator
					+ "Run_"
					+ Util.getCurrentFormattedTime().replace(" ", "_")
							.replace(":", "-");

			String reportPathWithTimeStamp = frameworkParameters
					.getRelativePath()
					+ frameworkParameters.fileSeparator
					+ "Results" + frameworkParameters.fileSeparator + timeStamp;

			new File(reportPathWithTimeStamp).mkdirs();
			new File(reportPathWithTimeStamp
					+ frameworkParameters.fileSeparator + "Screenshots")
					.mkdir();
		}

		return timeStamp;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}