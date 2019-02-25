/*    */package com.fm.craft;

/*    */
/*    */import java.io.FileInputStream;
/*    */
import java.io.FileNotFoundException;
/*    */
import java.io.IOException;
/*    */
import java.util.Properties;

/*    */
/*    */public class Settings
/*    */{
	/*    */private static Properties properties;

	/*    */
	/*    */public static synchronized Properties getInstance()
	/*    */{
		/* 27 */if (properties == null) {
			/* 28 */loadFromPropertiesFile();
			/*    */}
		/*    */
		/* 31 */return properties;
		/*    */}

	/*    */
	/*    */public Object clone() throws CloneNotSupportedException
	/*    */{
		/* 36 */throw new CloneNotSupportedException();
		/*    */}

	/*    */
	/*    */private static void loadFromPropertiesFile()
	/*    */{
		/* 41 */FrameworkParameters frameworkParameters = FrameworkParameters
				.getInstance();
		/*    */
		/* 43 */if (frameworkParameters.getRelativePath() == null) {
			/* 44 */throw new FrameworkException(
					"FrameworkParameters.relativePath is not set!");
			/*    */}
		/*    */
		/* 47 */properties = new Properties();
		/*    */try
		/*    */{
			/* 50 */properties.load(
			/* 51 */new FileInputStream(frameworkParameters.getRelativePath() +
			/* 51 */frameworkParameters.fileSeparator
					+ "Global Settings.properties"));
			/*    */} catch (FileNotFoundException e) {
			/* 53 */e.printStackTrace();
			/* 54 */throw new FrameworkException(
					"FileNotFoundException while loading the Global Settings file");
			/*    */} catch (IOException e) {
			/* 56 */e.printStackTrace();
			/* 57 */throw new FrameworkException(
					"IOException while loading the Global Settings file");
			/*    */}
		/*    */}
	/*    */
}
