/*     */package com.fm.craft;

/*     */
/*     */import java.util.Properties;

/*     */
/*     */public class DataTable
/*     */{
	/*     */private String datatablePath;
	/*     */private String datatableName;
	/*     */private String currentTestcase;
	/* 17 */private int currentIteration = 0;
	private int currentSubIteration = 0;
	/*     */
	/* 19 */private Properties properties = Settings.getInstance();

	/*     */
	/*     */public DataTable(String datatablePath, String datatableName)
	/*     */{
		/* 29 */this.datatablePath = datatablePath;
		/* 30 */this.datatableName = datatableName;
		/*     */}

	/*     */
	/*     */public void setCurrentRow(String currentTestcase,
			int currentIteration, int currentSubIteration)
	/*     */{
		/* 41 */this.currentTestcase = currentTestcase;
		/* 42 */this.currentIteration = currentIteration;
		/* 43 */this.currentSubIteration = currentSubIteration;
		/*     */}

	/*     */
	/*     */private void checkPreRequisites()
	/*     */{
		/* 48 */if (this.currentTestcase == null) {
			/* 49 */throw new FrameworkException(
					"DataTable.currentTestCase is not set!");
			/*     */}
		/* 51 */if (this.currentIteration == 0) {
			/* 52 */throw new FrameworkException(
					"DataTable.currentIteration is not set!");
			/*     */}
		/* 54 */if (this.currentSubIteration == 0)
			/* 55 */throw new FrameworkException(
					"DataTable.currentSubIteration is not set!");
		/*     */}

	/*     */
	/*     */public synchronized String getData(String datasheetName,
			String fieldName)
	/*     */{
		/* 69 */checkPreRequisites();
		/*     */
		/* 71 */ExcelDataAccess testDataAccess = new ExcelDataAccess(
				this.datatablePath, this.datatableName);
		/* 72 */testDataAccess.setDatasheetName(datasheetName);
		/*     */
		/* 74 */int rowNum = testDataAccess.getRowNum(this.currentTestcase, 0);
		/* 75 */if (rowNum == -1) {
			/* 76 */throw new FrameworkException("The test case \""
					+ this.currentTestcase
					+ "\" is not found in the test data sheet \""
					+ datasheetName + "\"!");
			/*     */}
		/* 78 */rowNum = testDataAccess.getRowNum(
				Integer.toString(this.currentIteration), 1, rowNum);
		/* 79 */if (rowNum == -1) {
			/* 80 */throw new FrameworkException("The iteration number \""
					+ this.currentIteration + "\" of the test case \""
					+ this.currentTestcase
					+ "\" is not found in the test data sheet \""
					+ datasheetName + "\"!");
			/*     */}
		/* 82 */rowNum = testDataAccess.getRowNum(
				Integer.toString(this.currentSubIteration), 2, rowNum);
		/* 83 */if (rowNum == -1) {
			/* 84 */throw new FrameworkException("The sub iteration number \""
					+ this.currentSubIteration + "\" under iteration number \""
					+ this.currentIteration + "\" of the test case \""
					+ this.currentTestcase
					+ "\" is not found in the test data sheet \""
					+ datasheetName + "\"!");
			/*     */}
		/*     */
		/* 89 */String dataValue = testDataAccess.getValue(rowNum, fieldName);
		/*     */
		/* 91 */String dataReferenceIdentifier = this.properties
				.getProperty("DataReferenceIdentifier");
		/* 92 */if (dataValue.startsWith(dataReferenceIdentifier)) {
			/* 93 */dataValue = getCommonData(fieldName, dataValue);
			/*     */}
		/*     */
		/* 96 */return dataValue;
		/*     */}

	/*     */
	/*     */private String getCommonData(String fieldName, String dataValue)
	/*     */{
		/* 101 */ExcelDataAccess commonDataAccess = new ExcelDataAccess(
				this.datatablePath, "Common Testdata");
		/* 102 */commonDataAccess.setDatasheetName("Common_Testdata");
		/*     */
		/* 104 */String dataReferenceIdentifier = this.properties
				.getProperty("DataReferenceIdentifier");
		/* 105 */String dataReferenceId = dataValue
				.split(dataReferenceIdentifier)[1];
		/*     */
		/* 107 */int rowNum = commonDataAccess.getRowNum(dataReferenceId, 0);
		/* 108 */if (rowNum == -1) {
			/* 109 */throw new FrameworkException(
					"The common test data row identified by \""
							+ dataReferenceId
							+ "\" is not found in the common test data sheet!");
			/*     */}
		/*     */
		/* 112 */dataValue = commonDataAccess.getValue(rowNum, fieldName);
		/*     */
		/* 114 */return dataValue;
		/*     */}

	/*     */
	/*     */public synchronized void putData(String datasheetName,
			String fieldName, String dataValue)
	/*     */{
		/* 126 */checkPreRequisites();
		/*     */
		/* 128 */ExcelDataAccess testDataAccess = new ExcelDataAccess(
				this.datatablePath, this.datatableName);
		/* 129 */testDataAccess.setDatasheetName(datasheetName);
		/*     */
		/* 131 */int rowNum = testDataAccess.getRowNum(this.currentTestcase, 0);
		/* 132 */if (rowNum == -1) {
			/* 133 */throw new FrameworkException("The test case \""
					+ this.currentTestcase
					+ "\" is not found in the test data sheet \""
					+ datasheetName + "\"!");
			/*     */}
		/* 135 */rowNum = testDataAccess.getRowNum(
				Integer.toString(this.currentIteration), 1, rowNum);
		/* 136 */if (rowNum == -1) {
			/* 137 */throw new FrameworkException("The iteration number \""
					+ this.currentIteration + "\" of the test case \""
					+ this.currentTestcase
					+ "\" is not found in the test data sheet \""
					+ datasheetName + "\"!");
			/*     */}
		/* 139 */rowNum = testDataAccess.getRowNum(
				Integer.toString(this.currentSubIteration), 2, rowNum);
		/* 140 */if (rowNum == -1) {
			/* 141 */throw new FrameworkException("The sub iteration number \""
					+ this.currentSubIteration + "\" under iteration number \""
					+ this.currentIteration + "\" of the test case \""
					+ this.currentTestcase
					+ "\" is not found in the test data sheet \""
					+ datasheetName + "\"!");
			/*     */}
		/*     */
		/* 144 */testDataAccess.setValue(rowNum, fieldName, dataValue);
		/*     */}

	/*     */
	/*     */public synchronized String getExpectedResult(String fieldName)
	/*     */{
		/* 155 */checkPreRequisites();
		/*     */
		/* 157 */ExcelDataAccess expectedResultsAccess = new ExcelDataAccess(
				this.datatablePath, this.datatableName);
		/* 158 */expectedResultsAccess
				.setDatasheetName("Parametrized_Checkpoints");
		/*     */
		/* 160 */int rowNum = expectedResultsAccess.getRowNum(
				this.currentTestcase, 0);
		/* 161 */if (rowNum == -1) {
			/* 162 */throw new FrameworkException("The test case \""
					+ this.currentTestcase
					+ "\" is not found in the parametrized checkpoints sheet!");
			/*     */}
		/* 164 */rowNum = expectedResultsAccess.getRowNum(
				Integer.toString(this.currentIteration), 1, rowNum);
		/* 165 */if (rowNum == -1) {
			/* 166 */throw new FrameworkException("The iteration number \""
					+ this.currentIteration + "\" of the test case \""
					+ this.currentTestcase
					+ "\" is not found in the parametrized checkpoints sheet!");
			/*     */}
		/* 168 */rowNum = expectedResultsAccess.getRowNum(
				Integer.toString(this.currentSubIteration), 2, rowNum);
		/* 169 */if (rowNum == -1) {
			/* 170 */throw new FrameworkException("The sub iteration number \""
					+ this.currentSubIteration + "\" under iteration number \""
					+ this.currentIteration + "\" of the test case \""
					+ this.currentTestcase
					+ "\" is not found in the parametrized checkpoints sheet!");
			/*     */}
		/*     */
		/* 173 */String dataValue = expectedResultsAccess.getValue(rowNum,
				fieldName);
		/*     */
		/* 175 */return dataValue;
		/*     */}
	/*     */
}
