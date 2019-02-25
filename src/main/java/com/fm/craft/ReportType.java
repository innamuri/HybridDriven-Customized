package com.fm.craft;

public abstract interface ReportType {
	public abstract void createTestLogHeader(String paramString1,
			String paramString2, int paramInt1, int paramInt2);

	public abstract void createResultSummaryHeader();

	public abstract void createIterationHeader(int paramInt);

	public abstract void createSectionHeader(String paramString);

	public abstract void updateTestLog(String paramString1,
			String paramString2, String paramString3, Status paramStatus,
			String paramString4);

	public abstract void updateResultSummary(String paramString1,
			String paramString2, String paramString3, String paramString4,
			String paramString5);

	public abstract void createTestLogFooter(String paramString, int paramInt1,
			int paramInt2);

	public abstract void createResultSummaryFooter(String paramString,
			int paramInt1, int paramInt2);
}
