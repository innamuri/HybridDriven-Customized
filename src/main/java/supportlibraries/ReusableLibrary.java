package supportlibraries;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.fm.craft.*;
import com.thoughtworks.selenium.Selenium;

/**
 * Abstract base class for reusable libraries created by the user
 */
public abstract class ReusableLibrary {
	/**
	 * The {@link DataTable} object (passed from the test script)
	 */
	protected DataTable dataTable;
	/**
	 * The {@link Report} object (passed from the test script)
	 */
	protected Report report;
	/**
	 * The {@link WebDriver} object
	 */
	protected WebDriver driver;
	protected Selenium selenium;
	/**
	 * The {@link ScriptHelper} object (required for calling one reusable
	 * library from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties = Settings.getInstance();
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters = FrameworkParameters
			.getInstance();

	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object
	 * @param driver
	 *            The {@link WebDriver} object
	 */
	public ReusableLibrary(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;

		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		this.driver = scriptHelper.getDriver();
		this.selenium = scriptHelper.getSelenium();
	}
}