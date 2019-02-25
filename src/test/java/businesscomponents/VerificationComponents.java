package businesscomponents;

import supportlibraries.*;

import com.fm.craft.*;
import componentgroups.Asserts;

/**
 * Verification Components class
 */
public class VerificationComponents extends ReusableLibrary {
	private Asserts asserts = new Asserts();

	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public VerificationComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void verifyLoginValidUser() {
		if (asserts.isTextPresent(driver, "SIGN-OFF")) {
			report.updateTestLog("Verify Login",
					"Login succeeded for valid user", Status.PASS);
		} else {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Verify Login",
					"Login failed for valid user");
		}
	}

	public void verifyLoginInvalidUser() {
		if (!asserts.isTextPresent(driver, "SIGN-OFF")) {
			report.updateTestLog("Verify Login",
					"Login failed for invalid user", Status.PASS);
		} else {
			report.updateTestLog("Verify Login",
					"Login succeeded for invalid user", Status.FAIL);
		}
	}

	public void verifyRegistration() {
		String userName = dataTable.getData("General_Data", "Username");

		if (asserts.isTextPresent(driver,
				"Dear " + dataTable.getExpectedResult("FirstName") + " "
						+ dataTable.getExpectedResult("LastName"))) {
			report.updateTestLog("Verify Registration", "User " + userName
					+ " registered successfully", Status.PASS);
		} else {
			throw new FrameworkException("Verify Registration", "User "
					+ userName + " registration failed");
		}
	}

	public void verifyBooking() {
		if (asserts.isTextPresent(driver, "Your itinerary has been booked!")) {
			report.updateTestLog("Verify Booking",
					"Tickets booked successfully", Status.PASS);
		} else {
			report.updateTestLog("Verify Booking", "Tickets booking failed",
					Status.FAIL);
		}
	}
}