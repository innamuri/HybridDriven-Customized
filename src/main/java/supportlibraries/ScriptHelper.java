package supportlibraries;

import org.openqa.selenium.WebDriver;

import com.fm.craft.*;
import com.thoughtworks.selenium.Selenium;

/**
 * Wrapper class for common framework objects, to be used across the entire test
 * case and dependent libraries
 */
public class ScriptHelper {
	private final DataTable dataTable;
	private final Report report;
	private final WebDriver driver;
	private final Selenium selenium;

	/**
	 * Constructor to initialize all the objects wrapped by the
	 * {@link ScriptHelper} class
	 * 
	 * @param dataTable
	 *            The {@link DataTable} object
	 * @param report
	 *            The {@link Report} object
	 * @param driver
	 *            The {@link WebDriver} object
	 */
	public ScriptHelper(DataTable dataTable, Report report, WebDriver driver,
			Selenium selenium) {
		this.dataTable = dataTable;
		this.report = report;
		this.driver = driver;
		this.selenium = selenium;
	}

	/**
	 * Function to get the {@link DataTable} object of the {@link ScriptHelper}
	 * class
	 * 
	 * @return The {@link DataTable} object
	 */
	public DataTable getDataTable() {
		return dataTable;
	}

	/**
	 * Function to get the {@link Report} object of the {@link ScriptHelper}
	 * class
	 * 
	 * @return The {@link Report} object
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * Function to get the {@link WebDriver} object of the {@link ScriptHelper}
	 * class
	 * 
	 * @return The {@link WebDriver} object
	 */
	public WebDriver getDriver() {
		return driver;
	}

	public Selenium getSelenium() {
		return selenium;
	}
}