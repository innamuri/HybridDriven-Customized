/*     */package com.fm.craft;

/*     */
/*     */public class ExcelReport
/*     */implements ReportType
/*     */{
	/*     */private ExcelDataAccess testLogAccess;
	/*     */private ExcelDataAccess resultSummaryAccess;

	/*     */
	/*     */public ExcelReport(String reportPath, String reportName)
	/*     */{
		/* 22 */FrameworkParameters frameworkParameters = FrameworkParameters
				.getInstance();
		/*     */
		/* 24 */this.testLogAccess = new ExcelDataAccess(reportPath
				+ frameworkParameters.fileSeparator + "Excel Results",
				reportName);
		/* 25 */this.resultSummaryAccess = new ExcelDataAccess(reportPath
				+ frameworkParameters.fileSeparator + "Excel Results",
				"Summary");
		/*     */}

	/*     */
	/*     */public void createTestLogHeader(String reportName,
			String iterationMode, int startIteration, int endIteration)
	/*     */{
		/* 32 */this.testLogAccess.createWorkbook();
		/* 33 */this.testLogAccess.addSheet("Test_Log");
		/*     */
		/* 35 */this.testLogAccess.addColumn("Step_No");
		/* 36 */this.testLogAccess.addColumn("Step_Name");
		/* 37 */this.testLogAccess.addColumn("Description");
		/* 38 */this.testLogAccess.addColumn("Status");
		/* 39 */this.testLogAccess.addColumn("Step_Time");
		/*     */}

	/*     */
	/*     */public void createResultSummaryHeader()
	/*     */{
		/* 45 */this.resultSummaryAccess.createWorkbook();
		/* 46 */this.resultSummaryAccess.addSheet("Results_Summary");
		/*     */
		/* 48 */this.resultSummaryAccess.addColumn("Test_Scenario");
		/* 49 */this.resultSummaryAccess.addColumn("Test_Case");
		/* 50 */this.resultSummaryAccess.addColumn("Description");
		/* 51 */this.resultSummaryAccess.addColumn("Execution_Time");
		/* 52 */this.resultSummaryAccess.addColumn("Status");
		/*     */}

	/*     */
	/*     */private void insertIntoTestLog(String stepNumber, String stepName,
			String stepDescription, String stepStatus, String stepTime)
	/*     */{
		/* 59 */int rowNum = this.testLogAccess.addRow();
		/*     */
		/* 61 */this.testLogAccess.setValue(rowNum, "Step_No", stepNumber);
		/* 62 */this.testLogAccess.setValue(rowNum, "Step_Name", stepName);
		/* 63 */this.testLogAccess.setValue(rowNum, "Description",
				stepDescription);
		/* 64 */this.testLogAccess.setValue(rowNum, "Status", stepStatus);
		/* 65 */this.testLogAccess.setValue(rowNum, "Step_Time", stepTime);
		/*     */}

	/*     */
	/*     */private void insertIntoResultSummary(String scenarioName,
			String testcaseName, String testcaseDescription,
			String executionTime, String testStatus)
	/*     */{
		/* 72 */int rowNum = this.resultSummaryAccess.addRow();
		/*     */
		/* 74 */this.resultSummaryAccess.setValue(rowNum, "Test_Scenario",
				scenarioName);
		/* 75 */this.resultSummaryAccess.setValue(rowNum, "Test_Case",
				testcaseName);
		/* 76 */this.resultSummaryAccess.setValue(rowNum, "Description",
				testcaseDescription);
		/* 77 */this.resultSummaryAccess.setValue(rowNum, "Execution_Time",
				executionTime);
		/* 78 */this.resultSummaryAccess.setValue(rowNum, "Status", testStatus);
		/*     */}

	/*     */
	/*     */public void createIterationHeader(int currentIteration)
	/*     */{
		/* 84 */insertIntoTestLog("", "", "Iteration:",
				Integer.toString(currentIteration), "");
		/*     */}

	/*     */
	/*     */public void createSectionHeader(String sectionName)
	/*     */{
		/* 90 */insertIntoTestLog(sectionName, "", "", "", "");
		/*     */}

	/*     */
	/*     */public void updateTestLog(String stepNumber, String stepName,
			String stepDescription, Status stepStatus, String screenShotName)
	/*     */{
		/* 96 */insertIntoTestLog(stepNumber, stepName, stepDescription,
				stepStatus.toString(), Util.getCurrentFormattedTime());
		/*     */}

	/*     */
	/*     */public void updateResultSummary(String scenarioName,
			String testcaseName, String testcaseDescription,
			String executionTime, String testStatus)
	/*     */{
		/* 102 */insertIntoResultSummary(scenarioName, testcaseName,
				testcaseDescription, executionTime, testStatus);
		/*     */}

	/*     */
	/*     */public void createTestLogFooter(String executionTime,
			int nStepsPassed, int nStepsFailed)
	/*     */{
		/* 108 */insertIntoTestLog("", "", "", "", "");
		/* 109 */insertIntoTestLog("", "Execution Duration:", executionTime,
				"", "");
		/* 110 */insertIntoTestLog("", "Steps passed:",
				Integer.toString(nStepsPassed), "Steps failed:",
				Integer.toString(nStepsFailed));
		/*     */}

	/*     */
	/*     */public void createResultSummaryFooter(String totalExecutionTime,
			int nTestsPassed, int nTestsFailed)
	/*     */{
		/* 116 */insertIntoResultSummary("", "", "", "", "");
		/* 117 */insertIntoResultSummary("", "Total Duration:",
				totalExecutionTime, "", "");
		/* 118 */insertIntoResultSummary("", "Tests passed:",
				Integer.toString(nTestsPassed), "Tests failed:",
				Integer.toString(nTestsFailed));
		/*     */}
	/*     */
}
