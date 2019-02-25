package componentgroups;

import supportlibraries.*;
import businesscomponents.*;

/**
 * Common Groups class
 */
public class CommonGroups extends ReusableLibrary {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public CommonGroups(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void findAndBookFlights() {
		FunctionalComponents functionalComponents = new FunctionalComponents(
				scriptHelper);
		VerificationComponents verificationComponents = new VerificationComponents(
				scriptHelper);

		functionalComponents.findFlights();
		functionalComponents.selectFlights();
		functionalComponents.bookFlights();

		verificationComponents.verifyBooking();

		functionalComponents.backToFlights();
	}
}