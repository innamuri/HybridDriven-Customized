/*    */package com.fm.craft;

/*    */
/*    */public class FrameworkException extends RuntimeException
/*    */{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* 16 */public String errorName = "Error";

	/*    */
	/*    */public FrameworkException(String errorDescription)
	/*    */{
		/* 25 */super(errorDescription);
		/*    */}

	/*    */
	/*    */public FrameworkException(String errorName, String errorDescription)
	/*    */{
		/* 35 */super(errorDescription);
		/* 36 */this.errorName = errorName;
		/*    */}
	/*    */
}
