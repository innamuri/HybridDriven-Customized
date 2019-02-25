package supportlibraries;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Factory for creating the Driver object based on the required browser
 */
public class WebDriverFactory {
	/**
	 * Function to return the appropriate {@link RemoteWebDriver} object based
	 * on the browser name passed
	 * 
	 * @param browser
	 *            The name of the browser
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static RemoteWebDriver getDriver(String browser) {
		WebDriver driver = null;

		if (browser.equalsIgnoreCase("Firefox"))
		{
//			driver = new FirefoxDriver();
			FirefoxBinary binary = new FirefoxBinary(new File("C:/Firefox ESR/firefox.exe"));
			FirefoxProfile profile = new FirefoxProfile();
			// new FirefoxDriver(binary, profile);
			driver = new FirefoxDriver(binary, profile);

		}else if (browser.equalsIgnoreCase("InternetExplorer"))
		{
			driver = new InternetExplorerDriver();
		}
		else if (browser.equalsIgnoreCase("Chrome"))
		{
			driver = new ChromeDriver();
		}

		return (RemoteWebDriver) driver;
	}

	public static Selenium getSelenium(String browser) {
		Selenium selenium = null;

		if (browser.equalsIgnoreCase("Firefox"))
			selenium = new DefaultSelenium("localhost", 4444, "*firefox",
					"http://");
		else if (browser.equalsIgnoreCase("InternetExplorer"))
			selenium = new DefaultSelenium("localhost", 4444, "*iexplorer",
					"http://");
		else if (browser.equalsIgnoreCase("Chrome"))
			selenium = new DefaultSelenium("localhost", 4444, "*chrome",
					"http://");

		return selenium;
	}

	public static String getBrowser(String browser) {
		String gbrowser = null;
		if (browser.equalsIgnoreCase("InternetExplorer")) {
			gbrowser = "iexplore";
		} else if (browser.equalsIgnoreCase("Firefox")) {
			gbrowser = "firefox";
		}
		return gbrowser;

	}
}