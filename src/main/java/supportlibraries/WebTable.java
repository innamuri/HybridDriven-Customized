package supportlibraries;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

public class WebTable {

	private final WebElement element;
	private final Integer RowCount;
	private final Boolean hasHeader;
	private final Boolean hasBody;
	private final WebElement TableHeader;
	private final WebElement TableBody;

	public WebTable(WebElement element) {
		String tagName = element.getTagName();

		if ((tagName == null) || (!"table".equals(tagName.toLowerCase()))) {
			throw new UnexpectedTagNameException("table", tagName);
		}
		this.element = element;

		List<WebElement> tblHeaders = this.element.findElements(By
				.tagName("thead"));
		if (tblHeaders.size() > 0) {
			this.hasHeader = true;
			this.TableHeader = (WebElement) tblHeaders.get(0);
		} else {
			this.hasHeader = false;
			this.TableHeader = null;
		}

		List<WebElement> tblBodies = this.element.findElements(By
				.tagName("tbody"));
		if (tblBodies.size() > 0) {
			this.hasBody = true;
			this.TableBody = (WebElement) tblBodies.get(0);
			List<WebElement> tblRows = this.TableBody.findElements(By
					.tagName("tr"));
			this.RowCount = tblRows.size();
		} else {
			this.hasBody = false;
			this.TableBody = null;
			List<WebElement> tblRows = this.element.findElements(By
					.tagName("tr"));
			this.RowCount = tblRows.size();
		}

	}

	public String ChildItem(Integer RowIndex, Integer ColumnIndex,
			String element) {
		return null;

	}

	public String GetHeaderColumnName(Integer ColumnIndex) {

		if (this.hasHeader == true) {

			List<WebElement> tblTHColumns = this.TableHeader.findElements(By
					.tagName("th"));
			if (tblTHColumns.size() > 0) {
				WebElement tblHeaderColumn = (WebElement) tblTHColumns
						.get(ColumnIndex);
				return tblHeaderColumn.getText();
			} else {
				List<WebElement> tblTDColumns = this.TableHeader
						.findElements(By.tagName("td"));
				if (tblTDColumns.size() > 0) {
					WebElement tblHeaderColumn = (WebElement) tblTDColumns
							.get(ColumnIndex);
					return tblHeaderColumn.getText();
				} else {
					return null;
				}
			}
		} else {
			List<WebElement> tblRows = this.element.findElements(By
					.tagName("tr"));
			if (tblRows.size() > 0) {
				WebElement curHeader = (WebElement) tblRows.get(0);
				List<WebElement> tblTDColumns = curHeader.findElements(By
						.tagName("td"));
				if (tblTDColumns.size() > 0) {
					WebElement tblHeaderColumn = (WebElement) tblTDColumns
							.get(ColumnIndex);
					return tblHeaderColumn.getText();
				} else {
					return null;
				}
			} else {
				return null;
			}

		}

	}

	public Integer GetRowCount() {
		return this.RowCount;
	}

	public String GetCellData(Integer RowIndex, Integer ColumnIndex) {

		List<?> tblRows;

		if (this.hasBody == true) {
			tblRows = this.TableBody.findElements(By.tagName("tr"));
		} else {
			tblRows = this.element.findElements(By.tagName("tr"));
		}

		if (tblRows.size() > 0 && RowIndex <= tblRows.size()) {
			WebElement curRow = (WebElement) tblRows.get(RowIndex - 1);
			List<WebElement> tblColumns = curRow.findElements(By.tagName("td"));
			if (tblColumns.size() > 0 && ColumnIndex <= tblColumns.size()) {
				WebElement curColumn = (WebElement) tblColumns.get(ColumnIndex);
				return curColumn.getText();
			} else {
				return "Column not found";
			}
		} else {
			return "Row not found";
		}

	}

}
