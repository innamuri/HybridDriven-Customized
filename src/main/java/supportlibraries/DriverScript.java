package supportlibraries;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.fm.craft.*;
import com.thoughtworks.selenium.Selenium;

import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Driver script class which encapsulates the core logic of the CRAFT framework
 */
@SuppressWarnings("deprecation")
public class DriverScript {
	private ArrayList<String> businessFlowData;
	private int currentIteration, currentSubIteration;
	private Date startTime, endTime;
	private String timeStamp;

	private DataTable dataTable;
	private Report report;
	private RemoteWebDriver driver;
	private Selenium selenium;
	private ScriptHelper scriptHelper;

	private Properties properties;
	private String gridMode;
	private FrameworkParameters frameworkParameters = FrameworkParameters
			.getInstance();

	/**
	 * The {@link TestParameters} object
	 */
	protected TestParameters testParameters = new TestParameters();

	/**
	 * Constructor to initialize the DriverScript
	 */
	public DriverScript() {
		setRelativePath();
		properties = Settings.getInstance();
		setDefaultTestParameters();
	}

	private void setRelativePath() {
		String relativePath = new File(System.getProperty("user.dir"))
				.getAbsolutePath();
		if (relativePath.contains("allocator")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		frameworkParameters.setRelativePath(relativePath);
	}

	private void setDefaultTestParameters() {
		testParameters.setCurrentScenario(this.getClass().getSimpleName());
		testParameters.setIterationMode(IterationOptions.RunAllIterations);
		testParameters.setBrowser(properties.getProperty("DefaultBrowser")
				.toLowerCase());
		testParameters.setBrowserVersion(properties
				.getProperty("DefaultBrowserVersion"));
		testParameters.setPlatform(PlatformFactory.getPlatform(properties
				.getProperty("DefaultPlatform")));
	}

	/**
	 * Function to execute the given test case
	 */
	public void driveTestExecution() {
		initializeWebDriver();
		initializeTestScript();
		initializeTestIterations();
		initializeTestReport();

		setUp();
		executeTestIterations();
		tearDown();

		wrapUp();
	}

	private void initializeWebDriver() {
		startTime = Util.getCurrentTime();
		String browser = testParameters.getBrowser();

		gridMode = properties.getProperty("GridMode");
		String method = properties.getProperty("Key");
		if (method.equalsIgnoreCase("WebDriver")) {
			if (gridMode.equalsIgnoreCase("on")) {
				DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

				desiredCapabilities.setBrowserName(WebDriverFactory
						.getBrowser(browser));
				/*
				 * desiredCapabilities.setVersion("8.0");
				 * desiredCapabilities.setPlatform
				 * (testParameters.getPlatform());
				 */
				URL gridHubUrl;
				try {
					gridHubUrl = new URL(properties.getProperty("GridHubUrl"));
				} catch (MalformedURLException e) {
					e.printStackTrace();
					throw new FrameworkException(
							"The specified Selenium Grid Hub URL is malformed");
				}

				driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
			} else {
				driver = WebDriverFactory
						.getDriver(testParameters.getBrowser());
			}
			driver.manage().window().maximize();
		} else if (method.equalsIgnoreCase("seleniumRC")) {
			selenium = WebDriverFactory
					.getSelenium(testParameters.getBrowser());

		} else {

		}

	}

	private void initializeTestScript() {
		String datatablePath = frameworkParameters.getRelativePath()
				+ frameworkParameters.fileSeparator + "Datatables";
		dataTable = new DataTable(datatablePath,
				testParameters.getCurrentScenario());
		report = new Report();
		scriptHelper = new ScriptHelper(dataTable, report, driver, selenium);

		businessFlowData = getBusinessFlow();

		frameworkParameters.setRunConfiguration(properties
				.getProperty("RunConfiguration"));
		timeStamp = TimeStamp.getInstance();
	}

	private synchronized void initializeTestIterations() {
		switch (testParameters.getIterationMode()) {
		case RunAllIterations:
			String datatablePath = frameworkParameters.getRelativePath()
					+ frameworkParameters.fileSeparator + "Datatables";
			ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath,
					testParameters.getCurrentScenario());
			testDataAccess.setDatasheetName(properties
					.getProperty("DefaultDataSheet"));

			int startRowNum = testDataAccess.getRowNum(
					testParameters.getCurrentTestcase(), 0);
			int nTestcaseRows = testDataAccess.getRowCount(
					testParameters.getCurrentTestcase(), 0, startRowNum);
			int nSubIterations = testDataAccess
					.getRowCount("1", 1, startRowNum); // Assumption: Every test
														// case will have at
														// least one iteration
			int nIterations = nTestcaseRows / nSubIterations;
			testParameters.setEndIteration(nIterations);

			currentIteration = 1;
			break;

		case RunOneIterationOnly:
			currentIteration = 1;
			break;

		case RunRangeOfIterations:
			if (testParameters.getStartIteration() > testParameters
					.getEndIteration()) {
				throw new FrameworkException("Error",
						"StartIteration cannot be greater than EndIteration!");
			}
			currentIteration = testParameters.getStartIteration();
			break;
		}
	}

	private void initializeTestReport() {
		String reportPath = frameworkParameters.getRelativePath()
				+ frameworkParameters.fileSeparator + "Results"
				+ frameworkParameters.fileSeparator + timeStamp;

		report.setReportPath(reportPath);
		report.setDriver(driver);
		report.setReportLogLevel(Integer.parseInt(properties
				.getProperty("LogLevel")));
		report.setReportName(testParameters.getCurrentScenario() + "_"
				+ testParameters.getCurrentTestcase());
		report.initialize();
		report.createTestLogHeader(
				testParameters.getIterationMode().toString(),
				testParameters.getStartIteration(),
				testParameters.getEndIteration());
	}

	/**
	 * Function to do required setup activities before starting the test
	 * execution
	 */
	protected void setUp() {
		String method = properties.getProperty("Key");
		String applicationUrl = properties.getProperty("ApplicationUrl");

		if (method.equalsIgnoreCase("WebDriver")) {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(applicationUrl);

			if (gridMode.equalsIgnoreCase("on")) {
				report.updateTestLog(
						"Startup",
						"Browser: " + testParameters.getBrowser() + "<br>"
								+ "Version: "
								+ testParameters.getBrowserVersion() + "<br>"
								+ "Platform: " + testParameters.getPlatform()
								+ "<br>" + "Application URL: " + applicationUrl,
						Status.DONE);
			} else {
				report.updateTestLog("Startup",
						"Browser: " + testParameters.getBrowser() + "<br>"
								+ "Application URL: " + applicationUrl,
						Status.DONE);
			}
		} else if (method.equalsIgnoreCase("seleniumRC")) {
			selenium.start();
		} else {

		}
	}

	private void executeTestIterations() {
		while (currentIteration <= testParameters.getEndIteration()) {
			report.createIterationHeader(currentIteration);

			try {
				executeTestcase(businessFlowData);
			} catch (FrameworkException fx) {
				exceptionHandler(fx, fx.errorName);
			} catch (InvocationTargetException ix) {
				exceptionHandler((Exception) ix.getCause(), "Error");
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			}

			currentIteration++;
		}
	}

	private ArrayList<String> getBusinessFlow() {
		ExcelDataAccess businessFlowAccess = new ExcelDataAccess(
				frameworkParameters.getRelativePath()
						+ frameworkParameters.fileSeparator + "Datatables",
				testParameters.getCurrentScenario());
		businessFlowAccess.setDatasheetName("Business_Flow");

		int rowNum = businessFlowAccess.getRowNum(
				testParameters.getCurrentTestcase(), 0);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \""
					+ testParameters.getCurrentTestcase()
					+ "\" is not found in the Business Flow sheet!");
		}

		String dataValue;
		ArrayList<String> businessFlowData = new ArrayList<String>();
		int currentColumnNum = 1;
		while (true) {
			dataValue = businessFlowAccess.getValue(rowNum, currentColumnNum);
			if (dataValue.equals("")) {
				break;
			}
			businessFlowData.add(dataValue);
			currentColumnNum++;
		}

		if (businessFlowData.size() == 0) {
			throw new FrameworkException(
					"No business flow found against the test case \""
							+ testParameters.getCurrentTestcase() + "\"");
		}

		return businessFlowData;
	}

	private void executeTestcase(ArrayList<String> businessFlowData)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, ClassNotFoundException,
			InstantiationException {
		HashMap<String, Integer> keywordDirectory = new HashMap<String, Integer>();

		for (int currentKeywordNum = 0; currentKeywordNum < businessFlowData
				.size(); currentKeywordNum++) {
			String[] currentFlowData = businessFlowData.get(currentKeywordNum)
					.split(",");
			String currentKeyword = currentFlowData[0];
			System.out
					.println("currentFlowData Size:" + currentFlowData.length);
			System.out.println("currentKeyword: " + currentKeyword);
			int nKeywordIterations;
			if (currentFlowData.length > 1) {
				nKeywordIterations = Integer.parseInt(currentFlowData[1]);
			} else {
				nKeywordIterations = 1;
			}

			for (int currentKeywordIteration = 0; currentKeywordIteration < nKeywordIterations; currentKeywordIteration++) {
				if (keywordDirectory.containsKey(currentKeyword)) {
					keywordDirectory.put(currentKeyword,
							keywordDirectory.get(currentKeyword) + 1);
				} else {
					keywordDirectory.put(currentKeyword, 1);
				}
				currentSubIteration = keywordDirectory.get(currentKeyword);

				dataTable.setCurrentRow(testParameters.getCurrentTestcase(),
						currentIteration, currentSubIteration);

				if (currentSubIteration > 1) {
					report.createSectionHeader(currentKeyword
							+ " (Sub-Iteration: " + currentSubIteration + ")");
				} else {
					report.createSectionHeader(currentKeyword);
				}

				invokeBusinessComponent(currentKeyword);
			}
		}
	}

	private void invokeBusinessComponent(String currentKeyword)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, ClassNotFoundException,
			InstantiationException {
		Boolean isMethodFound = false;
		final String CLASS_FILE_EXTENSION = ".class";
		File[] packageDirectories = {
				new File(frameworkParameters.getRelativePath()
						+ frameworkParameters.fileSeparator + "target"
						+ frameworkParameters.fileSeparator + "test-classes"
						+ frameworkParameters.fileSeparator	+ "businesscomponents"),
				new File(frameworkParameters.getRelativePath()
						+ frameworkParameters.fileSeparator + "target"
						+ frameworkParameters.fileSeparator + "test-classes"
						+ frameworkParameters.fileSeparator + "componentgroups") };

		for (File packageDirectory : packageDirectories) {
			File[] packageFiles = packageDirectory.listFiles();
			String packageName = packageDirectory.getName();

			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName();

				// We only want the .class files
				if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
					// Remove the .class extension to get the class name
					String className = fileName.substring(0, fileName.length()
							- CLASS_FILE_EXTENSION.length());

					Class<?> reusableComponents = Class.forName(packageName
							+ "." + className);
					Method executeComponent;

					try {
						// Convert the first letter of the method to lowercase
						// (in line with java naming conventions)
						currentKeyword = currentKeyword.substring(0, 1)
								.toLowerCase() + currentKeyword.substring(1);
						executeComponent = reusableComponents.getMethod(
								currentKeyword, (Class<?>[]) null);
					} catch (NoSuchMethodException ex) {
						// If the method is not found in this class, search the
						// next class
						continue;
					}

					isMethodFound = true;

					Constructor<?> ctor = reusableComponents
							.getDeclaredConstructors()[0];
					Object businessComponent = ctor.newInstance(scriptHelper);

					executeComponent.invoke(businessComponent, (Object[]) null);

					break;
				}
			}
		}

		if (!isMethodFound) {
			throw new FrameworkException("Keyword " + currentKeyword
					+ " not found within any class "
					+ "inside the businesscomponents package");
		}
	}

	private void exceptionHandler(Exception ex, String exceptionName) {
		// Error reporting
	
		String exceptionDescription = ex.getMessage();
		if (exceptionDescription == null) {
			exceptionDescription = ex.toString();
		}

		if (ex.getCause() != null) {
			report.updateTestLog(exceptionName, exceptionDescription
					+ " <b>Caused by: </b>" + ex.getCause(), Status.FAIL);
		} else {
			report.updateTestLog(exceptionName, exceptionDescription,
					Status.FAIL);
		}
		ex.printStackTrace();

		// Error response
		OnError onError = OnError.valueOf(properties.getProperty("OnError"));
		switch (onError) {
		case NextIteration:
			report.updateTestLog(
					"CRAFTLite Info",
					"Test case iteration terminated by user! Proceeding to next iteration (if applicable)...",
					Status.DONE);
			currentIteration++;
			executeTestIterations();
			break;

		case NextTestCase:
			report.updateTestLog(
					"CRAFTLite Info",
					"Test case terminated by user! Proceeding to next test case (if applicable)...",
					Status.DONE);
			break;

		case Stop:
			frameworkParameters.setStopExecution(true);
			break;
		}

		// Wrap up execution
		tearDown();
		wrapUp();
	}

	/**
	 * Function to do required teardown activities at the end of the test
	 * execution
	 */
	protected void tearDown() {
		driver.quit();
	}

	private void wrapUp() {
		endTime = Util.getCurrentTime();
		closeTestReport();

		if (report.testStatus.equalsIgnoreCase("Failed")) {
			Assert.fail(report.failureDescription);
		}
	}

	private void closeTestReport() {
		String executionTime = Util.getTimeDifference(startTime, endTime);
		report.createTestLogFooter(executionTime);
	}
}