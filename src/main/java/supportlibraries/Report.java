package supportlibraries;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import com.fm.craft.*;
import com.thoughtworks.selenium.Selenium;

/**
 * Class to encapsulate all the reporting features of the framework
 */
public class Report {
	private String reportPath, reportName;
	private int stepNumber;
	private int nStepsPassed = 0, nStepsFailed = 0;
	private int nTestsPassed = 0, nTestsFailed = 0;
	private int logLevel;
	private List<ReportType> reportTypes = new ArrayList<ReportType>();
	private WebDriver driver;
	private Selenium selenium;

	private FrameworkParameters frameworkParameters = FrameworkParameters
			.getInstance();
	private Properties properties = Settings.getInstance();

	/**
	 * The status of the test being executed
	 */
	public String testStatus = "Passed";
	/**
	 * The description of the failure (applicable only if the script fails)
	 */
	public String failureDescription;

	/**
	 * Function to set the path where the reports are stored
	 * 
	 * @param reportPath
	 *            The report path
	 * @see #setReportName(String)
	 */
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	/**
	 * Function to set the report name
	 * 
	 * @param reportName
	 *            The report name
	 * @see #setReportPath(String)
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Function to set the {@link WebDriver} object
	 * 
	 * @param driver
	 *            The {@link WebDriver} object
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setSelenium(Selenium selenium) {
		this.selenium = selenium;
	}

	/**
	 * Function to set the logging level of the reports
	 * 
	 * @param logLevel
	 *            The logging level (varies from 0-minimal to 5-detailed)
	 */
	public void setReportLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Function to initialize the different types of reports
	 */
	public void initialize() {
		/*
		 * if(properties.getProperty("ExcelReport").equals("True")) { new
		 * File(reportPath + frameworkParameters.fileSeparator +
		 * "Excel Results").mkdir(); reportTypes.add(new ExcelReport(reportPath,
		 * reportName)); }
		 */

		if (properties.getProperty("HTMLReport").equals("True")) {
			new File(reportPath + frameworkParameters.fileSeparator
					+ "HTML Results").mkdir();
			reportTypes.add(new HtmlReport(reportPath, reportName));
		}
	}

	/**
	 * Function to create the Test log files and initialize the headers
	 */
	public void createTestLogHeader(String iterationMode, int startIteration,
			int endIteration) {
		for (int i = 0; i < reportTypes.size(); i++) {
			reportTypes.get(i).createTestLogHeader(reportName, iterationMode,
					startIteration, endIteration);
		}
	}

	/**
	 * Function to create the Result Summary files and initialize the headers
	 */
	public void createResultSummaryHeader() {
		for (int i = 0; i < reportTypes.size(); i++) {
			reportTypes.get(i).createResultSummaryHeader();
		}
	}

	/**
	 * Function to add headers representing the beginning of an iteration
	 * 
	 * @param currentIteration
	 *            The current iteration number being executed
	 */
	public void createIterationHeader(int currentIteration) {
		for (int i = 0; i < reportTypes.size(); i++) {
			reportTypes.get(i).createIterationHeader(currentIteration);
		}

		stepNumber = 1;
	}

	/**
	 * Function to add headers representing a section in the test log
	 * 
	 * @param sectionName
	 *            The name of the section to be added
	 */
	public void createSectionHeader(String sectionName) {
		for (int i = 0; i < reportTypes.size(); i++) {
			reportTypes.get(i).createSectionHeader(sectionName);
		}
	}

	/**
	 * Function to update the test log with the details of a particular test
	 * step
	 * 
	 * @param stepName
	 *            The test step name
	 * @param stepDescription
	 *            The description of what the test step does
	 * @param stepStatus
	 *            The status of the test step
	 */
	public void updateTestLog(String stepName, String stepDescription,
			Status stepStatus) {
		if (stepStatus.equals(Status.FAIL)) {
			testStatus = "Failed";

			if (failureDescription == null) {
				failureDescription = stepDescription;
			} else {
				failureDescription = failureDescription + "; "
						+ stepDescription;
			}

			nStepsFailed++;
		}

		if (stepStatus.equals(Status.PASS)) {
			nStepsPassed++;
		}

		if (stepStatus.ordinal() <= logLevel) {
			FrameworkParameters frameworkParameters = FrameworkParameters
					.getInstance();

			String screenshotName = null;

			if (stepStatus.equals(Status.FAIL)) {
				Boolean takeScreenshotFailedStep = Boolean
						.parseBoolean(properties
								.getProperty("TakeScreenshotFailedStep"));
				if (takeScreenshotFailedStep) {
					screenshotName = reportName
							+ "_"
							+ Util.getCurrentFormattedTime().replace(" ", "_")
									.replace(":", "-") + ".png";
					takeScreenshot(reportPath
							+ frameworkParameters.fileSeparator + "Screenshots"
							+ frameworkParameters.fileSeparator
							+ screenshotName);
				}
			}

			if (stepStatus.equals(Status.PASS)) {
				Boolean takeScreenshotPassedStep = Boolean
						.parseBoolean(properties
								.getProperty("TakeScreenshotPassedStep"));
				if (takeScreenshotPassedStep) {
					screenshotName = reportName
							+ "_"
							+ Util.getCurrentFormattedTime().replace(" ", "_")
									.replace(":", "-") + ".png";
					takeScreenshot(reportPath
							+ frameworkParameters.fileSeparator + "Screenshots"
							+ frameworkParameters.fileSeparator
							+ screenshotName);
				}
			}

			if (stepStatus.equals(Status.SCREENSHOT)) {
				screenshotName = reportName
						+ "_"
						+ Util.getCurrentFormattedTime().replace(" ", "_")
								.replace(":", "-") + ".png";
				takeScreenshot(reportPath + frameworkParameters.fileSeparator
						+ "Screenshots" + frameworkParameters.fileSeparator
						+ screenshotName);
			}

			for (int i = 0; i < reportTypes.size(); i++) {
				reportTypes.get(i).updateTestLog(Integer.toString(stepNumber),
						stepName, stepDescription, stepStatus, screenshotName);
			}

			stepNumber++;
		}
	}

	/**
	 * Function to take a screenshot and store in the specified path
	 * 
	 * @param screenshotPath
	 *            Absolute path of the folder where the screenshot is to be
	 *            saved
	 */
	private void takeScreenshot(String screenshotPath) {
		File scrnsht = null;
		if (driver != null) {

			try {
				String Grid_Mode = properties.getProperty("GridMode");
				if (Grid_Mode.equalsIgnoreCase("on")) {/*
					java.net.InetAddress i = java.net.InetAddress
							.getLocalHost();
					System.out.println(i); // name and IP address
					System.out.println(i.getHostName()); // name
					System.out.println(i.getHostAddress()); // IP address only
					screenshotPath = screenshotPath.replace("C:",
											WebDriver augmentedDriver = new Augmenter().augment(driver);
	"//" + i.getHostAddress());
					scrnsht = ((TakesScreenshot) augmentedDriver)
							.getScreenshotAs(OutputType.FILE);
				*/} else {
					scrnsht = ((TakesScreenshot) driver)
							.getScreenshotAs(OutputType.FILE);
				}
			} catch (Exception e) {
				e.toString();
				System.out.println(e.toString());
			}
			try {
				FileUtils.copyFile(scrnsht, new File(screenshotPath));
			} catch (IOException e) {
			}
		} else if (selenium != null) {
			selenium.captureScreenshot(screenshotPath);
		} else {
			throw new FrameworkException(
					"Report.driver and Report.selenium is not initialized!");
		}

	}

	/**
	 * Function to update the results summary with the status of the executed
	 * test case
	 * 
	 * @param testParameters
	 *            The {@link TestParameters} object
	 * @param executionTime
	 *            The time taken to execute the test case
	 * @param testStatus
	 *            The Pass/Fail status of the test case
	 */
	public void updateResultSummary(TestParameters testParameters,
			String executionTime, String testStatus) {
		if (testStatus.equalsIgnoreCase("failed")) {
			nTestsFailed++;
		} else if (testStatus.equalsIgnoreCase("passed")) {
			nTestsPassed++;
		}

		for (int i = 0; i < reportTypes.size(); i++) {
			reportTypes.get(i).updateResultSummary(
					testParameters.getCurrentScenario(),
					testParameters.getCurrentTestcase(),
					testParameters.getCurrentTestDescription(), executionTime,
					testStatus);
		}
	}

	/**
	 * Function to create footers to close the test log files
	 * 
	 * @param executionTime
	 *            The time taken to execute the test case
	 */
	public void createTestLogFooter(String executionTime) {
		for (int i = 0; i < reportTypes.size(); i++) {
			reportTypes.get(i).createTestLogFooter(executionTime, nStepsPassed,
					nStepsFailed);
		}
	}

	/**
	 * Function to create footers to close the results summary files
	 */
	public void createResultSummaryFooter(String totalExecutionTime) {
		for (int i = 0; i < reportTypes.size(); i++) {
			reportTypes.get(i).createResultSummaryFooter(totalExecutionTime,
					nTestsPassed, nTestsFailed);
		}
	}
}