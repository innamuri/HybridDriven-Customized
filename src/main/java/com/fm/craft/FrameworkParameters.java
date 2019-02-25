/*    */package com.fm.craft;

/*    */
/*    */public class FrameworkParameters
/*    */{
	/*    */private String relativePath;
	/* 33 */public final String fileSeparator = System
			.getProperty("file.separator");
	/*    */
	/* 35 */private Boolean stopExecution = Boolean.valueOf(false);
	/*    */private String runConfiguration;
	/*    */private static FrameworkParameters frameworkParameters;

	/*    */
	/*    */public String getRelativePath()
	/*    */{
		/* 19 */return this.relativePath;
		/*    */}

	/*    */
	/*    */public void setRelativePath(String relativePath)
	/*    */{
		/* 27 */this.relativePath = relativePath;
		/*    */}

	/*    */
	/*    */public Boolean getStopExecution()
	/*    */{
		/* 42 */return this.stopExecution;
		/*    */}

	/*    */
	/*    */public void setStopExecution(Boolean stopExecution)
	/*    */{
		/* 50 */this.stopExecution = stopExecution;
		/*    */}

	/*    */
	/*    */public String getRunConfiguration()
	/*    */{
		/* 60 */return this.runConfiguration;
		/*    */}

	/*    */
	/*    */public void setRunConfiguration(String runConfiguration)
	/*    */{
		/* 68 */this.runConfiguration = runConfiguration;
		/*    */}

	/*    */
	/*    */public static synchronized FrameworkParameters getInstance()
	/*    */{
		/* 82 */if (frameworkParameters == null) {
			/* 83 */frameworkParameters = new FrameworkParameters();
			/*    */}
		/*    */
		/* 86 */return frameworkParameters;
		/*    */}

	/*    */
	/*    */public Object clone() throws CloneNotSupportedException
	/*    */{
		/* 91 */throw new CloneNotSupportedException();
		/*    */}
	/*    */
}
