package allocator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import supportlibraries.*;

import com.fm.craft.*;

/**
 * Class to manage the batch execution of test scripts within the framework
 */
public class Allocator {
	private static ArrayList<TestParameters> testInstancesToRun;
	private static Report report;
	public static Properties properties;
	private static FrameworkParameters frameworkParameters = FrameworkParameters
			.getInstance();

	private static Date startTime, endTime;
	private static String timeStamp;
	private static String runConfiguration;
	private static String reportPathWithTimeStamp;

	public static void main(String[] args) throws FileNotFoundException {
		setRelativePath();
		initializeTestBatch();
		setupErrorLog();
		initializeSummaryReport();

		driveBatchExecution();

		wrapUp();
	}

	private static void setRelativePath() {
		String relativePath = new File(System.getProperty("user.dir"))
				.getAbsolutePath();
		if (relativePath.contains("allocator")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		frameworkParameters.setRelativePath(relativePath);
	}

	private static void initializeTestBatch() {
		startTime = Util.getCurrentTime();

		report = new Report();

		properties = Settings.getInstance();
		runConfiguration = properties.getProperty("RunConfiguration");
		frameworkParameters.setRunConfiguration(runConfiguration);
		testInstancesToRun = getRunInfo(runConfiguration);

		timeStamp = TimeStamp.getInstance();

		reportPathWithTimeStamp = frameworkParameters.getRelativePath()
				+ frameworkParameters.fileSeparator + "Results"
				+ frameworkParameters.fileSeparator + timeStamp;
	}

	private static void setupErrorLog() throws FileNotFoundException {
		String errorLogFile = reportPathWithTimeStamp
				+ frameworkParameters.fileSeparator + "ErrorLog.txt";
		System.setErr(new PrintStream(new FileOutputStream(errorLogFile)));
	}

	private static void initializeSummaryReport() {
		report.setReportPath(reportPathWithTimeStamp);
		report.initialize();
		report.createResultSummaryHeader();
	}

	private static void driveBatchExecution() {
		int nThreads = Integer.parseInt(properties
				.getProperty("NumberOfThreads"));
		ExecutorService parallelExecutor = Executors
				.newFixedThreadPool(nThreads);

		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun
				.size(); currentTestInstance++) {
			ParallelRunner testRunner = new ParallelRunner(
					testInstancesToRun.get(currentTestInstance), report);
			parallelExecutor.execute(testRunner);

			if (frameworkParameters.getStopExecution()) {
				break;
			}
		}

		parallelExecutor.shutdown();
		while (!parallelExecutor.isTerminated()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<TestParameters> getRunInfo(String sheetName) {
		ExcelDataAccess runManagerAccess = new ExcelDataAccess(
				frameworkParameters.getRelativePath(), "Run Manager");
		runManagerAccess.setDatasheetName(sheetName);

		int nTestInstances = runManagerAccess.getLastRowNum();
		ArrayList<TestParameters> testInstancesToRun = new ArrayList<TestParameters>();

		for (int currentTestInstance = 1; currentTestInstance <= nTestInstances; currentTestInstance++) {
			String executeFlag = runManagerAccess.getValue(currentTestInstance,
					"Execute");

			if (executeFlag.equalsIgnoreCase("Yes")) {
				TestParameters testParameters = new TestParameters();

				testParameters.setCurrentScenario(runManagerAccess.getValue(
						currentTestInstance, "Test_Scenario"));
				testParameters.setCurrentTestcase(runManagerAccess.getValue(
						currentTestInstance, "Test_Case"));
				testParameters.setCurrentTestDescription(runManagerAccess
						.getValue(currentTestInstance, "Description"));

				testParameters.setIterationMode(IterationOptions
						.valueOf(runManagerAccess.getValue(currentTestInstance,
								"Iteration_Mode")));
				String startIteration = runManagerAccess.getValue(
						currentTestInstance, "Start_Iteration");
				if (!startIteration.equals("")) {
					testParameters.setStartIteration(Integer
							.parseInt(startIteration));
				}
				String endIteration = runManagerAccess.getValue(
						currentTestInstance, "End_Iteration");
				if (!endIteration.equals("")) {
					testParameters.setEndIteration(Integer
							.parseInt(endIteration));
				}

				String browser = runManagerAccess.getValue(currentTestInstance,
						"Browser");
				if (!browser.equals("")) {
					testParameters.setBrowser(browser);
				}
				String browserVersion = runManagerAccess.getValue(
						currentTestInstance, "Browser_Version");
				if (!browserVersion.equals("")) {
					testParameters.setBrowserVersion(browserVersion);
				}
				String platform = runManagerAccess.getValue(
						currentTestInstance, "Platform");
				if (!platform.equals("")) {
					testParameters.setPlatform(PlatformFactory
							.getPlatform(platform));
				}

				testInstancesToRun.add(testParameters);
			}
		}

		return testInstancesToRun;
	}

	private static void wrapUp() {
		endTime = Util.getCurrentTime();
		closeSummaryReport();

		try {
			Runtime.getRuntime().exec(
					"RunDLL32.EXE shell32.dll,ShellExec_RunDLL "
							+ reportPathWithTimeStamp
							+ "\\HTML Results\\Summary.Html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void closeSummaryReport() {
		String totalExecutionTime = Util.getTimeDifference(startTime, endTime);
		report.createResultSummaryFooter(totalExecutionTime);
	}
}