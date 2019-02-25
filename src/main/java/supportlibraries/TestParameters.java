package supportlibraries;

import org.openqa.selenium.Platform;

import com.fm.craft.IterationOptions;

/**
 * Class to encapsulate various input parameters required for each test script
 */
public class TestParameters {

	private String currentScenario;

	/**
	 * Function to get the current test scenario/module
	 * 
	 * @return The current test scenario/module
	 */
	public String getCurrentScenario() {
		return currentScenario;
	}

	/**
	 * Function to set the current test scenario/module
	 * 
	 * @param currentScenario
	 *            The current test scenario/module
	 */
	public void setCurrentScenario(String currentScenario) {
		this.currentScenario = currentScenario;
	}

	private String currentTestcase;

	/**
	 * Function to get the current test case
	 * 
	 * @return The current test case
	 */
	public String getCurrentTestcase() {
		return currentTestcase;
	}

	/**
	 * Function to set the current test case
	 * 
	 * @param currentTestcase
	 *            The current test case
	 */
	public void setCurrentTestcase(String currentTestcase) {
		this.currentTestcase = currentTestcase;
	}

	private String currentTestDescription;

	/**
	 * Function to get the description of the current test case
	 * 
	 * @return The description of the current test case
	 */
	public String getCurrentTestDescription() {
		return currentTestDescription;
	}

	/**
	 * Function to set the description of the current test case
	 * 
	 * @param currentTestDescription
	 *            The description of the current test case
	 */
	public void setCurrentTestDescription(String currentTestDescription) {
		this.currentTestDescription = currentTestDescription;
	}

	private IterationOptions iterationMode;

	/**
	 * Function to get the iteration mode
	 * 
	 * @return The iteration mode
	 * @see IterationOptions
	 */
	public IterationOptions getIterationMode() {
		return iterationMode;
	}

	/**
	 * Function to set the iteration mode
	 * 
	 * @param iterationMode
	 *            The iteration mode
	 * @see IterationOptions
	 */
	public void setIterationMode(IterationOptions iterationMode) {
		this.iterationMode = iterationMode;
	}

	private int startIteration = 1;

	/**
	 * Function to get the start iteration
	 * 
	 * @return The start iteration
	 */
	public int getStartIteration() {
		return startIteration;
	}

	/**
	 * Function to set the start iteration
	 * 
	 * @param startIteration
	 *            The start iteration (defaults to 1 if the input is <=0)
	 */
	public void setStartIteration(int startIteration) {
		if (startIteration > 0) {
			this.startIteration = startIteration;
		}
	}

	private int endIteration = 1;

	/**
	 * Function to get the end iteration
	 * 
	 * @return The end iteration
	 */
	public int getEndIteration() {
		return endIteration;
	}

	/**
	 * Function to set the end iteration
	 * 
	 * @param endIteration
	 *            The end iteration (defaults to 1 if the input is <=0)
	 */
	public void setEndIteration(int endIteration) {
		if (endIteration > 0) {
			this.endIteration = endIteration;
		}
	}

	private String browser;

	/**
	 * Function to get the browser for a specific test
	 * 
	 * @return The browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * Function to set the browser for a specific test
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	private String browserVersion;

	/**
	 * Function to get the browserVersion for a specific test
	 * 
	 * @return The browserVersion
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Function to set the browserVersion for a specific test
	 */
	public void setBrowserVersion(String version) {
		this.browserVersion = version;
	}

	private Platform platform;

	/**
	 * Function to get the platform for a specific test
	 * 
	 * @return The platform
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * Function to set the platform for a specific test
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
}