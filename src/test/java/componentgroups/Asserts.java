package componentgroups;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

/**
 * Class to provide common assertions/verifications
 */
public class Asserts {
	/**
	 * Function to verify whether a given text is present within the page
	 * 
	 * @param driver
	 *            The {@link WebDriver} object
	 * @param textToVerify
	 *            The text to be verified within the page
	 * @return A boolean value indicating if the searched text is found
	 */
	public boolean isTextPresent(WebDriver driver, String textToVerify) {
		textToVerify = textToVerify.replace(" ", "\\s*");
		String pageSource = driver.getPageSource();
		String[] pageSourceLines = pageSource.trim().split("\\n");
		String pageSourceWithoutNewlines = "";
		for (String pageSourceLine : pageSourceLines) {
			pageSourceWithoutNewlines += pageSourceLine + " ";
		}

		pageSourceWithoutNewlines = pageSourceWithoutNewlines.trim();

		Pattern p = Pattern.compile(textToVerify);
		Matcher m = p.matcher(pageSourceWithoutNewlines);
		if (m.find())
			return true;

		return false;
	}

	public enum CurMethod {
		NAME, ID, LINKTEXT, PARTIALLINKTEXT
	}

	public boolean isPresent(WebDriver driver, String curObjName) {
		try {
			driver.findElement(By.name(curObjName));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean isPresent(WebDriver driver, CurMethod findMethod,
			String curObjName) {

		try {
			switch (findMethod) {
			case NAME:
				driver.findElement(By.name(curObjName));
				return true;
			case ID:
				driver.findElement(By.id(curObjName));
				return true;
			case LINKTEXT:
				driver.findElement(By.linkText(curObjName));
				return true;
			case PARTIALLINKTEXT:
				driver.findElement(By.partialLinkText(curObjName));
				return true;
			default:
				return false;
			}
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public void Sleep(int intSeconds) {
		try {
			Thread.sleep(intSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean AjaxSync(WebDriver driver) {
		Boolean ajaxStatus;

		JavascriptExecutor js = (JavascriptExecutor) driver;

		ajaxStatus = (Boolean) js
				.executeScript("return EXIGEN_IPB.SubmitController.lockVisible");

		while (ajaxStatus == true) {
			try {
				Thread.sleep(1000);
				ajaxStatus = (Boolean) js
						.executeScript("return EXIGEN_IPB.SubmitController.lockVisible");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				ajaxStatus = false;
				e.printStackTrace();
			}
		}
		;
		return true;
	}

	public boolean ClickElementByPartialName(WebDriver driver, String objName) {
		try {
			driver.findElement(
					By.xpath("//input[contains(@name,'" + objName + "')]"))
					.click();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean ClickElementByPartialName(WebDriver driver, String objName,
			Integer objIndex) {
		try {
			List<WebElement> objList = driver.findElements(By
					.xpath("//input[contains(@name,'" + objName + "')]"));
			objList.get(objIndex).click();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean ClickElementByPartialId(WebDriver driver, String objId) {
		try {
			driver.findElement(
					By.xpath("//input[contains(@id,'" + objId + "')]")).click();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean ClickElementByPartialId(WebDriver driver, String objId,
			Integer objIndex) {
		try {
			List<WebElement> objList = driver.findElements(By
					.xpath("//input[contains(@id,'" + objId + "')]"));
			objList.get(objIndex).click();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean SetValueByPartialName(WebDriver driver, String objName,
			String strValueToSet) {
		try {
			driver.findElement(
					By.xpath("//input[contains(@name,'" + objName + "')]"))
					.sendKeys(strValueToSet);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean SetValueByPartialName(WebDriver driver, String objName,
			String strValueToSet, Integer objIndex) {
		try {
			List<WebElement> objList = driver.findElements(By
					.xpath("//input[contains(@name,'" + objName + "')]"));
			objList.get(objIndex).sendKeys(strValueToSet);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean SetValueByPartialId(WebDriver driver, String objId,
			String strValueToSet) {
		try {
			driver.findElement(
					By.xpath("//input[contains(@id,'" + objId + "')]"))
					.sendKeys(strValueToSet);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean SetValueByPartialId(WebDriver driver, String objId,
			String strValueToSet, Integer objIndex) {
		try {
			List<WebElement> objList = driver.findElements(By
					.xpath("//input[contains(@id,'" + objId + "')]"));
			objList.get(objIndex).sendKeys(strValueToSet);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean SelectValueByPartialName(WebDriver driver, String objName,
			String strValueToSelect) {
		try {
			Select cmbObject = new Select(driver.findElement(By
					.xpath("//select[contains(@name,'" + objName + "')]")));
			cmbObject.selectByVisibleText(strValueToSelect);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean SelectValueByPartialName(WebDriver driver, String objName,
			String strValueToSelect, Integer objIndex) {
		try {
			List<WebElement> objList = driver.findElements(By
					.xpath("//select[contains(@name,'" + objName + "')]"));
			objList.get(objIndex).sendKeys(strValueToSelect);

			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean SelectValueByPartialIndex(WebDriver driver, String objName,
			Integer intIndexNum) {
		try {
			Select cmbObject = new Select(driver.findElement(By
					.xpath("//select[contains(@name,'" + objName + "')]")));
			cmbObject.selectByIndex(intIndexNum);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

}