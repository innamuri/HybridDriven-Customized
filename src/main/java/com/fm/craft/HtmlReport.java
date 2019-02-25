/*     */package com.fm.craft;

/*     */
/*     */

import java.io.BufferedWriter;
/*     */
import java.io.File;
/*     */
import java.io.FileNotFoundException;
/*     */
import java.io.FileOutputStream;
/*     */
import java.io.FileWriter;
/*     */
import java.io.IOException;
/*     */
import java.io.PrintStream;
import java.util.Properties;

/*     */
/*     */public class HtmlReport implements ReportType
/*     */{
	/*     */private String testLogPath;
	/*     */private String resultSummaryPath;
	/*     */private static String headingColor;
	/*     */private static String subHeadingColor;
	/*     */private static String settingColor;
	/*     */private static String bodyColor;
	/* 38 */private Properties properties = Settings.getInstance();

	/*     */
	/*     */public HtmlReport(String reportPath, String reportName)
	/*     */{
		/* 48 */FrameworkParameters frameworkParameters = FrameworkParameters
				.getInstance();
		/*     */
		/* 50 */this.testLogPath =
		/* 51 */(reportPath + frameworkParameters.fileSeparator
				+ "HTML Results" +
				/* 51 */frameworkParameters.fileSeparator + reportName + ".html");
		/*     */
		/* 53 */this.resultSummaryPath =
		/* 54 */(reportPath + frameworkParameters.fileSeparator
				+ "HTML Results" +
				/* 54 */frameworkParameters.fileSeparator + "Summary" + ".html");
		/*     */
		/* 56 */setReportsTheme();
		/*     */}

	/*     */
	/*     */private void setReportsTheme()
	/*     */{
		/* 61 */Theme reportTheme = Theme.valueOf(this.properties
				.getProperty("ReportsTheme"));
		/*     */
		/* 63 */switch (reportTheme) {
		/*     */case AUTUMN:
			/* 65 */headingColor = "#7E5D56";
			/* 66 */subHeadingColor = "#A6A390";
			/* 67 */settingColor = "#EDE9CE";
			/* 68 */bodyColor = "#F6F3E4";
			/* 69 */break;
		/*     */case MYSTIC:
			/* 72 */headingColor = "#687C7D";
			/* 73 */subHeadingColor = "#8B9292";
			/* 74 */settingColor = "#C6D0D1";
			/* 75 */bodyColor = "#EDEEF0";
			/* 76 */break;
		/*     */case REBEL:
			/* 79 */headingColor = "#4D7C7B";
			/* 80 */subHeadingColor = "#B2B27A";
			/* 81 */settingColor = "#FFFFAE";
			/* 82 */bodyColor = "#FAFAC5";
			/* 83 */break;
		/*     */case CLASSIC:
			/* 86 */headingColor = "#686145";
			/* 87 */subHeadingColor = "#A6A390";
			/* 88 */settingColor = "#EDE9CE";
			/* 89 */bodyColor = "#E8DEBA";
			/* 90 */break;
		/*     */case SERENE:
			/* 93 */headingColor = "#953735";
			/* 94 */subHeadingColor = "#747474";
			/* 95 */settingColor = "#A6A6A6";
			/* 96 */bodyColor = "#D9D9D9";
			/* 97 */break;
		/*     */case OLIVE:
			/* 100 */headingColor = "#CE824E";
			/* 101 */subHeadingColor = "#AA9B7C";
			/* 102 */settingColor = "#F3DEB1";
			/* 103 */bodyColor = "#F8F1E7";
			/* 104 */break;
		/*     */case RETRO:
			/* 107 */headingColor = "#7B597A";
			/* 108 */subHeadingColor = "#799DB2";
			/* 109 */settingColor = "#ADE0FF";
			/* 110 */bodyColor = "#C5AFC6";
			/* 111 */break;
		/*     */default:
			/* 114 */headingColor = "#B3D9FF";
			/* 115 */subHeadingColor = "#8F98B2";
			/* 116 */settingColor = "#CCD9FF";
			/* 117 */bodyColor = "#8A4117";
			/*     */}
		/*     */}

	/*     */
	/*     */public void createTestLogHeader(String reportName,
			String iterationMode, int startIteration, int endIteration)
	/*     */{
		/* 126 */File testLogFile = new File(this.testLogPath);
		FileOutputStream outputStream;
		/*     */try {
			/* 128 */testLogFile.createNewFile();
			/*     */} catch (IOException e) {
			/* 130 */e.printStackTrace();
			/* 131 */throw new FrameworkException(
					"Error while creating HTML test log file");
			/*     */}
		/*     */
		/*     */try
		/*     */{
			/* 136 */outputStream = new FileOutputStream(testLogFile);
			/*     */}
		/*     */catch (FileNotFoundException e)
		/*     */{
			/*     */
			/* 138 */e.printStackTrace();
			/* 139 */throw new FrameworkException(
					"Cannot find HTML test log file");
			/*     */}
		/*     */
		/* 141 */PrintStream printStream = new PrintStream(outputStream);
		/*     */
		/* 143 */String projectName = this.properties
				.getProperty("ProjectName");
		/* 144 */iterationMode = iterationMode.replaceAll("<", "<i>");
		/* 145 */iterationMode = iterationMode.replaceAll("Iteration>",
				"Iteration</i>");
		/*     */
		/* 148 */String testLogHeader = "<html> \n\t <head> \n\t\t <title>"
				+
				/* 151 */projectName
				+ " - "
				+ reportName
				+ " Automation Execution Results"
				+
				/* 152 */"</title> \n"
				+
				/* 153 */"\t </head> \n\n"
				+
				/* 155 */"\t <body> \n"
				+
				/* 156 */"\t\t <p align='center'> \n"
				+
				/* 157 */"\t\t\t <table border='2' bordercolor='#000000' bordercolorlight='#000000' cellspacing='0' id='table1' width='1000' height='100'> \n"
				+
				/* 158 */"\t\t\t\t <tr bgcolor='"
				+ headingColor
				+ "'> \n"
				+
				/* 159 */"\t\t\t\t\t <td colspan='5'> \n"
				+
				/* 160 */"\t\t\t\t\t\t <p align='center'><font color='"
				+ bodyColor
				+ "' size='4' face='Copperplate Gothic Bold'>"
				+
				/* 161 */projectName
				+ " - "
				+ reportName
				+ " Automation Execution Results"
				+
				/* 162 */"</font></p> \n"
				+
				/* 163 */"\t\t\t\t\t </td> \n"
				+
				/* 164 */"\t\t\t\t </tr>\n"
				+
				/* 166 */"\t\t\t\t <tr bgcolor='"
				+ settingColor
				+ "'> \n"
				+
				/* 167 */"\t\t\t\t\t <td colspan='3'> \n"
				+
				/* 168 */"\t\t\t\t\t\t <p align='justify'><b><font color='"
				+ headingColor
				+ "' size='2' face='Verdana'>"
				+
				/* 169 */"&nbsp;Date & Time: "
				+ Util.getCurrentFormattedTime()
				+
				/* 170 */"</font></b></p> \n"
				+
				/* 171 */"\t\t\t\t\t </td> \n"
				+
				/* 172 */"\t\t\t\t\t <td colspan='2'> \n"
				+
				/* 173 */"\t\t\t\t\t\t <p align='justify'><b><font color='"
				+ headingColor
				+ "' size='2' face='Verdana'>"
				+
				/* 174 */"&nbsp;Iteration Mode: "
				+ iterationMode
				+
				/* 175 */"</font></b></p> \n"
				+
				/* 176 */"\t\t\t\t\t </td> \n"
				+
				/* 177 */"\t\t\t\t </tr> \n"
				+
				/* 179 */"\t\t\t\t <tr bgcolor='"
				+ settingColor
				+ "'> \n"
				+
				/* 180 */"\t\t\t\t\t <td colspan='3'> \n"
				+
				/* 181 */"\t\t\t\t\t\t <p align='justify'><b><font color='"
				+ headingColor
				+ "' size='2' face='Verdana'>"
				+
				/* 182 */"&nbsp;Start Iteration: "
				+ startIteration
				+
				/* 183 */"</font></b></p> \n"
				+
				/* 184 */"\t\t\t\t\t </td> \n"
				+
				/* 185 */"\t\t\t\t\t <td colspan='2'> \n"
				+
				/* 186 */"\t\t\t\t\t\t <p align='justify'><b><font color='"
				+ headingColor
				+ "' size='2' face='Verdana'>"
				+
				/* 187 */"&nbsp;End Iteration: "
				+ endIteration
				+
				/* 188 */"</font></b></p> \n"
				+
				/* 189 */"\t\t\t\t\t </td> \n"
				+
				/* 190 */"\t\t\t\t </tr> \n"
				+
				/* 192 */"\t\t\t\t <tr bgcolor='"
				+ headingColor
				+ "'> \n"
				+
				/* 193 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Step_No</font></center></b></td> \n"
				+
				/* 194 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Step_Name</font></center></b></td> \n"
				+
				/* 195 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Description</font></center></b></td> \n"
				+
				/* 196 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Status</font></center></b></td> \n"
				+
				/* 197 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Step_Time</font></center></b></td> \n"
				+
				/* 198 */"\t\t\t\t </tr> \n";
		/*     */
		/* 200 */printStream.println(testLogHeader);
		/* 201 */printStream.close();
		/*     */}

	/*     */
	/*     */public void createResultSummaryHeader()
	/*     */{
		/* 207 */File resultSummaryFile = new File(this.resultSummaryPath);
		FileOutputStream outputStream;
		/*     */try
		/*     */{
			/* 210 */resultSummaryFile.createNewFile();
			/*     */} catch (IOException e) {
			/* 212 */e.printStackTrace();
			/* 213 */throw new FrameworkException(
					"Error while creating HTML result summary file");
			/*     */}
		/*     */
		/*     */try
		/*     */{
			/* 218 */outputStream = new FileOutputStream(resultSummaryFile);
			/*     */}
		/*     */catch (FileNotFoundException e)
		/*     */{
			/*     */
			/* 220 */e.printStackTrace();
			/* 221 */throw new FrameworkException(
					"Cannot find HTML result summary file");
			/*     */}
		/*     */
		/* 223 */PrintStream printStream = new PrintStream(outputStream);
		/*     */
		/* 225 */String projectName = this.properties
				.getProperty("ProjectName");
		/*     */
		/* 227 */String resultSummaryHeader = "<html> \n\t <head> \n\t\t <title>"
				+
				/* 229 */projectName
				+ " - Automation Execution Results Summary</title> \n"
				+
				/* 230 */"\t </head> \n\n"
				+
				/* 232 */"\t <body> \n"
				+
				/* 233 */"\t\t <p align='center'> \n"
				+
				/* 234 */"\t\t\t <table border='2' bordercolor='#000000' bordercolorlight='#000000' cellspacing='0' id='table1' width='900' height='31' bordercolorlight='#000000'> \n"
				+
				/* 235 */"\t\t\t\t <tr bgcolor='"
				+ headingColor
				+ "'> \n"
				+
				/* 236 */"\t\t\t\t\t <td colspan='5'> \n"
				+
				/* 237 */"\t\t\t\t\t\t <p align='center'><font color='"
				+ bodyColor
				+ "' size='4' face='Copperplate Gothic Bold'>"
				+
				/* 238 */projectName
				+ " - Automation Execution Results Summary"
				+
				/* 239 */"</font></p> \n"
				+
				/* 240 */"\t\t\t\t\t </td> \n"
				+
				/* 241 */"\t\t\t\t </tr> \n"
				+
				/* 243 */"\t\t\t\t <tr bgcolor='"
				+ settingColor
				+ "'> \n"
				+
				/* 244 */"\t\t\t\t\t <td colspan='3'> \n"
				+
				/* 245 */"\t\t\t\t\t\t <p align='justify'><b><font color='"
				+ headingColor
				+ "' size='2' face='Verdana'>"
				+
				/* 246 */"&nbsp;Date & Time: "
				+ Util.getCurrentFormattedTime()
				+
				/* 247 */"</font></b></p> \n"
				+
				/* 248 */"\t\t\t\t\t </td> \n"
				+
				/* 249 */"\t\t\t\t\t <td colspan='2'> \n"
				+
				/* 250 */"\t\t\t\t\t\t <p align='justify'><b><font color='"
				+ headingColor
				+ "' size='2' face='Verdana'>"
				+
				/* 251 */"&nbsp;OnError: "
				+ this.properties.getProperty("OnError")
				+
				/* 252 */"</font></b></p> \n"
				+
				/* 253 */"\t\t\t\t\t </td> \n"
				+
				/* 254 */"\t\t\t\t </tr> \n"
				+
				/* 256 */"\t\t\t\t <tr bgcolor='"
				+ headingColor
				+ "'> \n"
				+
				/* 257 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Test_Scenario</font></center></b></td> \n"
				+
				/* 258 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Test_Case</font></center></b></td> \n"
				+
				/* 259 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Description</font></center></b></td> \n"
				+
				/* 260 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Execution_Time</font></center></b></td> \n"
				+
				/* 261 */"\t\t\t\t\t <td><b><center><font color='"
				+ bodyColor
				+ "' size='2' face='Verdana'>Status</font></center></b></td> \n"
				+
				/* 262 */"\t\t\t\t </tr> \n";
		/*     */
		/* 264 */printStream.println(resultSummaryHeader);
		/* 265 */printStream.close();
		/*     */}

	/*     */
	/*     */public void createIterationHeader(int currentIteration)
	/*     */{
		/*     */try
		/*     */{
			/* 273 */BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(this.testLogPath, true));
			/*     */
			/* 275 */String iterationHeader = "\t\t\t\t <tr bgcolor='"
					+ subHeadingColor + "'> \n" +
					/* 276 */"\t\t\t\t\t <td colspan='5'><center><b>" +
					/* 277 */"Iteration: " + currentIteration +
					/* 278 */"</b></center></td> \n" +
					/* 279 */"\t\t\t\t </tr> \n";
			/* 280 */bufferedWriter.write(iterationHeader);
			/* 281 */bufferedWriter.close();
			/*     */} catch (IOException e) {
			/* 283 */e.printStackTrace();
			/* 284 */throw new FrameworkException(
					"Error while adding iteration header to HTML test log");
			/*     */}
		/*     */
		/*     */}

	/*     */
	/*     */public void createSectionHeader(String sectionName)
	/*     */{
		/*     */try {
			/* 293 */BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(this.testLogPath, true));
			/*     */
			/* 295 */String iterationHeader = "\t\t\t\t <tr bgcolor='"
					+ settingColor + "'> \n" +
					/* 296 */"\t\t\t\t\t <td colspan='5'><font color='"
					+ headingColor +
					/* 297 */"' size='2' face='Verdana'><b>&nbsp;"
					+ sectionName +
					/* 298 */"</b></font></td> \n" +
					/* 299 */"\t\t\t\t </tr> \n";
			/* 300 */bufferedWriter.write(iterationHeader);
			/* 301 */bufferedWriter.close();
			/*     */} catch (IOException e) {
			/* 303 */e.printStackTrace();
			/* 304 */throw new FrameworkException(
					"Error while adding iteration header to HTML test log");
			/*     */}
		/*     */
		/*     */}

	/*     */
	/*     */public void updateTestLog(String stepNumber, String stepName,
			String stepDescription, Status stepStatus, String screenShotName) {
		/*     */try {
			/* 312 */BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(this.testLogPath, true));
			/*     */
			/* 314 */String testStepRow = "\t\t\t\t <tr bgcolor='" + bodyColor
					+ "'> \n" +
					/* 315 */"\t\t\t\t\t <td><center>" + stepNumber
					+ "</center></td> \n" +
					/* 316 */"\t\t\t\t\t <td>" + stepName + "</td> \n" +
					/* 317 */"\t\t\t\t\t <td>" + stepDescription + "</td> \n";
			/*     */
			/* 319 */if (stepStatus.equals(Status.FAIL)) {
				/* 320 */Boolean takeScreenshotFailedStep = Boolean
						.valueOf(Boolean.parseBoolean(this.properties
								.getProperty("TakeScreenshotFailedStep")));
				/* 321 */if (takeScreenshotFailedStep.booleanValue()) {
					/* 322 */testStepRow = testStepRow
							+ "\t\t\t\t\t <td><a href='..\\Screenshots\\" +
							/* 323 */screenShotName + "'>" +
							/* 324 */"<font color='red'><b><center>"
							+ stepStatus + "</center></b></font>" +
							/* 325 */"</a>" +
							/* 326 */"</td> \n";
					/*     */}
				/*     */else {
					/* 329 */testStepRow = testStepRow
							+ "\t\t\t\t\t <td><font color='red'><b><center>" +
							/* 330 */stepStatus + "</center></b></font>" +
							/* 331 */"</td> \n";
					/*     */}
				/*     */}
			/* 334 */else if (stepStatus.equals(Status.PASS)) {
				/* 335 */Boolean takeScreenshotPassedStep = Boolean
						.valueOf(Boolean.parseBoolean(this.properties
								.getProperty("TakeScreenshotPassedStep")));
				/* 336 */if (takeScreenshotPassedStep.booleanValue()) {
					/* 337 */testStepRow = testStepRow
							+ "\t\t\t\t\t <td><a href='..\\Screenshots\\" +
							/* 338 */screenShotName + "'>" +
							/* 339 */"<font color='green'><b><center>"
							+ stepStatus + "</center></b></font>" +
							/* 340 */"</a>" +
							/* 341 */"</td> \n";
					/*     */}
				/*     */else {
					/* 344 */testStepRow = testStepRow
							+ "\t\t\t\t\t <td><font color='green'><b><center>" +
							/* 345 */stepStatus + "</center></b></font>" +
							/* 346 */"</td> \n";
					/*     */}
				/*     */}
			/* 349 */else if (stepStatus.equals(Status.SCREENSHOT))
			/*     */{
				/* 351 */testStepRow = testStepRow
						+ "\t\t\t\t\t <td><a href='..\\Screenshots\\" +
						/* 352 */screenShotName + "'>" +
						/* 353 */"<font color='blue'><b><center>" + stepStatus
						+ "</center></b></font>" +
						/* 354 */"</a>" +
						/* 355 */"</td> \n";
				/*     */}
			/*     */else {
				/* 358 */testStepRow = testStepRow
						+ "\t\t\t\t\t <td><b><center>" +
						/* 359 */stepStatus + "</center></b>" +
						/* 360 */"</td> \n";
				/*     */}
			/*     */
			/* 363 */testStepRow = testStepRow + "\t\t\t\t\t <td><center>" +
			/* 364 */Util.getCurrentFormattedTime() + "</center>" +
			/* 365 */"</td> \n" +
			/* 366 */"\t\t\t\t </tr> \n";
			/*     */
			/* 368 */bufferedWriter.write(testStepRow);
			/* 369 */bufferedWriter.close();
			/*     */} catch (IOException e) {
			/* 371 */e.printStackTrace();
			/* 372 */throw new FrameworkException(
					"Error while updating HTML test log");
			/*     */}
		/*     */}

	/*     */
	/*     */public void updateResultSummary(String scenarioName,
			String testcaseName, String testcaseDescription,
			String executionTime, String testStatus)
	/*     */{
		/*     */try
		/*     */{
			/* 380 */BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(this.resultSummaryPath, true));
			/*     */
			/* 382 */String testcaseRow = "\t\t\t\t <tr bgcolor='" + bodyColor
					+ "'> \n" +
					/* 383 */"\t\t\t\t\t <td>" + scenarioName + "</td> \n" +
					/* 384 */"\t\t\t\t\t <td><a href='" + scenarioName + "_"
					+ testcaseName + ".html' " +
					/* 385 */"target='about_blank'>" + testcaseName +
					/* 386 */"</a></td> \n" +
					/* 387 */"\t\t\t\t\t <td>" + testcaseDescription
					+ "</td> \n" +
					/* 388 */"\t\t\t\t\t <td><center>" + executionTime
					+ "</center></td> \n";
			/*     */
			/* 390 */if (testStatus.equalsIgnoreCase("passed")) {
				/* 391 */testcaseRow = testcaseRow
						+ "\t\t\t\t\t <td><font color='green'><b><center>" +
						/* 392 */testStatus + "</center></b></font>" +
						/* 393 */"</td> \n" +
						/* 394 */"\t\t\t\t </tr> \n";
				/*     */}
			/*     */else {
				/* 397 */testcaseRow = testcaseRow
						+ "\t\t\t\t\t <td><font color='red'><b><center>" +
						/* 398 */testStatus + "</center></b></font>" +
						/* 399 */"</td> \n" +
						/* 400 */"\t\t\t\t </tr> \n";
				/*     */}
			/*     */
			/* 403 */bufferedWriter.write(testcaseRow);
			/* 404 */bufferedWriter.close();
			/*     */} catch (IOException e) {
			/* 406 */e.printStackTrace();
			/* 407 */throw new FrameworkException(
					"Error while updating HTML result summary");
			/*     */}
		/*     */}

	/*     */
	/*     */public void createTestLogFooter(String executionTime,
			int nStepsPassed, int nStepsFailed)
	/*     */{
		/*     */try
		/*     */{
			/* 415 */BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(this.testLogPath, true));
			/*     */
			/* 417 */String testLogFooter = "\t\t\t\t <tr bgcolor='"
					+ settingColor
					+ "'> \n"
					+
					/* 418 */"\t\t\t\t\t <td colspan='5'> \n"
					+
					/* 419 */"\t\t\t\t\t\t <center><b><font color='"
					+ headingColor
					+ "' size='2' face='Verdana'>"
					+
					/* 420 */"Execution Duration: "
					+ executionTime
					+ "</font></b></center> \n"
					+
					/* 421 */"\t\t\t\t\t </td> \n"
					+
					/* 422 */"\t\t\t\t </tr> \n"
					+
					/* 423 */"\t\t\t\t <tr bgcolor='"
					+ settingColor
					+ "'> \n"
					+
					/* 424 */"\t\t\t\t\t <td colspan='3'> \n"
					+
					/* 425 */"\t\t\t\t\t\t <b><font color='green'>&nbsp;Steps passed: "
					+ nStepsPassed
					+ "</font></b> \n"
					+
					/* 426 */"\t\t\t\t\t </td> \n"
					+
					/* 427 */"\t\t\t\t\t <td colspan=2> \n"
					+
					/* 428 */"\t\t\t\t\t\t <b><font color='red'>&nbsp;Steps failed: "
					+ nStepsFailed + "</font></b> \n" +
					/* 429 */"\t\t\t\t\t </td> \n" +
					/* 430 */"\t\t\t\t </tr> \n" +
					/* 431 */"\t\t\t </table> \n" +
					/* 432 */"\t\t </p> \n" +
					/* 433 */"\t </body> \n" +
					/* 434 */"</html>";
			/*     */
			/* 436 */bufferedWriter.write(testLogFooter);
			/* 437 */bufferedWriter.close();
			/*     */} catch (IOException e) {
			/* 439 */e.printStackTrace();
			/* 440 */throw new FrameworkException(
					"Error while adding footer to HTML test log");
			/*     */}
		/*     */}

	/*     */
	/*     */public void createResultSummaryFooter(String totalExecutionTime,
			int nTestsPassed, int nTestsFailed)
	/*     */{
		/*     */try
		/*     */{
			/* 448 */BufferedWriter bufferWriter = new BufferedWriter(
					new FileWriter(this.resultSummaryPath, true));
			/* 449 */String resultSummaryFooter = "\t\t\t\t <tr bgcolor='"
					+ settingColor
					+ "'> \n"
					+
					/* 450 */"\t\t\t\t\t <td colspan='5'> \n"
					+
					/* 451 */"\t\t\t\t\t\t <center><b><font color='"
					+ headingColor
					+ "' size='2' face='Verdana'>"
					+
					/* 452 */"Total Duration: "
					+ totalExecutionTime
					+ "</font></b></center> \n"
					+
					/* 453 */"\t\t\t\t\t </td> \n"
					+
					/* 454 */"\t\t\t\t </tr> \n"
					+
					/* 455 */"\t\t\t\t <tr bgcolor='"
					+ settingColor
					+ "'><center><b> \n"
					+
					/* 456 */"\t\t\t\t\t <td colspan='3'> \n"
					+
					/* 457 */"\t\t\t\t\t\t <font color='green'><b>&nbsp;Tests passed: "
					+ nTestsPassed
					+ "</b></font> \n"
					+
					/* 458 */"\t\t\t\t\t </td> \n"
					+
					/* 459 */"\t\t\t\t\t <td colspan='2'> \n"
					+
					/* 460 */"\t\t\t\t\t\t <font color='red'><b>&nbsp;Tests failed: "
					+ nTestsFailed + "</b></font> \n" +
					/* 461 */"\t\t\t\t\t </td> \n" +
					/* 462 */"\t\t\t\t </b></center></tr> \n" +
					/* 463 */"\t\t\t </table> \n" +
					/* 464 */"\t\t </p> \n" +
					/* 465 */"\t </body> \n" +
					/* 466 */"</html>";
			/*     */
			/* 468 */bufferWriter.write(resultSummaryFooter);
			/* 469 */bufferWriter.close();
			/*     */} catch (IOException e) {
			/* 471 */e.printStackTrace();
			/* 472 */throw new FrameworkException(
					"Error while adding footer to HTML result summary");
			/*     */}
		/*     */}

	/*     */
	/*     */private static enum Theme
	/*     */{
		/* 35 */AUTUMN, OLIVE, CLASSIC, RETRO, MYSTIC, SERENE, REBEL;
		/*     */
	}
	/*     */
}
