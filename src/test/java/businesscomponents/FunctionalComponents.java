package businesscomponents;

import supportlibraries.*;
import uimap.AccountPage;
import uimap.DryRunLoginPage;

import com.fm.craft.*;
import componentgroups.Asserts;
import componentgroups.Asserts.CurMethod;
//import com.fm.craft.Report.Status;

import org.openqa.selenium.*;

/**
 * Functional Components class
 */
public class FunctionalComponents extends ReusableLibrary {
	private Asserts asserts = new Asserts();

	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public FunctionalComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void login() {
		String userName = dataTable.getData("General_Data", "Username");
		String password = dataTable.getData("General_Data", "Password");

		driver.findElement(By.name("userName")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).click();

		report.updateTestLog("Login", "Enter login credentials: "
				+ "Username = " + userName + ", " + "Password = " + password,
				Status.DONE);
	}

	public void registerUser() {
		driver.findElement(By.linkText("REGISTER")).click();
		driver.findElement(By.name("firstName")).sendKeys(
				dataTable.getData("RegisterUser_Data", "FirstName"));
		driver.findElement(By.name("lastName")).sendKeys(
				dataTable.getData("RegisterUser_Data", "LastName"));
		driver.findElement(By.name("phone")).sendKeys(
				dataTable.getData("RegisterUser_Data", "Phone"));
		driver.findElement(By.name("userName")).sendKeys(
				dataTable.getData("RegisterUser_Data", "Email"));
		driver.findElement(By.name("address1")).sendKeys(
				dataTable.getData("RegisterUser_Data", "Address"));
		driver.findElement(By.name("city")).sendKeys(
				dataTable.getData("RegisterUser_Data", "City"));
		driver.findElement(By.name("state")).sendKeys(
				dataTable.getData("RegisterUser_Data", "State"));
		driver.findElement(By.name("postalCode")).sendKeys(
				dataTable.getData("RegisterUser_Data", "PostalCode"));
		driver.findElement(By.name("email")).sendKeys(
				dataTable.getData("General_Data", "Username"));
		String password = dataTable.getData("General_Data", "Password");
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("confirmPassword")).sendKeys(password);
		driver.findElement(By.name("register")).click();
		report.updateTestLog("Registration",
				"Enter user details for registration", Status.DONE);
	}

	public void clickSignIn() {
		driver.findElement(By.linkText("sign-in")).click();
		report.updateTestLog("Click sign-in", "Click the sign-in link",
				Status.DONE);
	}

	public void findFlights() {
		driver.findElement(By.name("passCount")).sendKeys(
				(dataTable.getData("Passenger_Data", "PassengerCount")));
		driver.findElement(By.name("fromPort")).sendKeys(
				(dataTable.getData("Flights_Data", "FromPort")));
		driver.findElement(By.name("fromMonth")).sendKeys(
				(dataTable.getData("Flights_Data", "FromMonth")));
		driver.findElement(By.name("fromDay")).sendKeys(
				(dataTable.getData("Flights_Data", "FromDay")));
		driver.findElement(By.name("toPort")).sendKeys(
				(dataTable.getData("Flights_Data", "ToPort")));
		driver.findElement(By.name("toMonth")).sendKeys(
				(dataTable.getData("Flights_Data", "ToMonth")));
		driver.findElement(By.name("toDay")).sendKeys(
				(dataTable.getData("Flights_Data", "ToDay")));
		driver.findElement(By.name("airline")).sendKeys(
				(dataTable.getData("Flights_Data", "Airline")));
		driver.findElement(By.name("findFlights")).click();
		report.updateTestLog("Find Flights",
				"Search for flights using given test data", Status.DONE);
	}

	public void selectFlights() {
		driver.findElement(By.name("reserveFlights")).click();
		report.updateTestLog("Select Flights",
				"Select the first available flights", Status.DONE);
	}

	public void bookFlights() {
		String[] passengerFirstNames = dataTable.getData("Passenger_Data",
				"PassengerFirstNames").split(",");
		String[] passengerLastNames = dataTable.getData("Passenger_Data",
				"PassengerLastNames").split(",");
		int passengerCount = Integer.parseInt(dataTable.getData(
				"Passenger_Data", "PassengerCount"));

		for (int i = 0; i < passengerCount; i++) {
			driver.findElement(By.name("passFirst" + i)).sendKeys(
					passengerFirstNames[i]);
			driver.findElement(By.name("passLast" + i)).sendKeys(
					passengerLastNames[i]);
		}
		driver.findElement(By.name("creditCard")).sendKeys(
				dataTable.getData("Passenger_Data", "CreditCard"));
		driver.findElement(By.name("creditnumber")).sendKeys(
				dataTable.getData("Passenger_Data", "CreditNumber"));

		report.updateTestLog("Book Tickets",
				"Enter passenger details and book tickets", Status.SCREENSHOT);

		driver.findElement(By.name("buyFlights")).click();
	}

	public void backToFlights() {
		driver.findElement(By.xpath("//a/img")).click();
	}

	public void logout() {
		driver.findElement(By.linkText("SIGN-OFF")).click();
		report.updateTestLog("Logout", "Click the sign-off link", Status.DONE);
	}

	public void loginPas() throws InterruptedException {
		String userName = dataTable.getData("CreateAccount_Data", "Username");
		String password = dataTable.getData("CreateAccount_Data", "Password");

		driver.findElement(By.name(DryRunLoginPage.txtUserId)).sendKeys(
				userName);
		driver.findElement(By.name(DryRunLoginPage.txtPassword)).sendKeys(
				password);
		driver.findElement(By.name(DryRunLoginPage.btnLogin)).click();
		report.updateTestLog("Login", "Enter login credentials: "
				+ "Username = " + userName + ", " + "Password = " + password,
				Status.DONE);
	}

	public void quickSearch() {
		if (asserts.isTextPresent(driver, "topQuickSearchForm:searchLink")) {
			driver.findElement(By.id("topQuickSearchForm:searchLink")).click();
			// asserts.ClickElementByPartialName(driver,"searchLink");
			report.updateTestLog("Quick Search", "Click the Search Link",
					Status.DONE);
		} else {
			driver.findElement(By.id("tabForm:topTabsBarList:1:link")).click();
			report.updateTestLog("Account Tab Search",
					"Click the Account Tab for Search", Status.DONE);
		}
	}

	public void createLink() {
		if ("Create Account".equalsIgnoreCase(driver.findElement(
				By.id("searchForm:createAccountBtnAlway"))
				.getAttribute("value"))) {
			// driver.findElement(By.id("searchForm:createAccountBtnAlway")).click();
			asserts.ClickElementByPartialName(driver,
					AccountPage.btnCreateAccount);
			report.updateTestLog("Create Link", "Click Create Link",
					Status.DONE);
		}
	}

	public void createAccount() {
		generalInfo();
		addressInfo();
		agencyAssgnt();
		phoneInfo();
		emailInfo();
		chatInfo();
		socialInfo();
		webURLInfo();
		OptOutInfo();
		// relationShipInfo();
		// driver.findElement(By.id("crmForm:okBtn")).click();
		report.updateTestLog("Create Account", "Created the account",
				Status.DONE);
	}

	public void generalInfo() {
		String firstName = dataTable.getData("CreateAccount_Data", "FirstName");
		String middleName = dataTable.getData("CreateAccount_Data",
				"MiddleName");
		String lastName = dataTable.getData("CreateAccount_Data", "LastName");
		String ssn = dataTable.getData("CreateAccount_Data", "SSN");
		String dob = dataTable.getData("CreateAccount_Data", "DateOfBirth");
		String maritalStatus = dataTable.getData("CreateAccount_Data",
				"MaritalStatus");
		// String salutation = dataTable.getData("Salutation");
		// String suffix = dataTable.getData("Suffix");
		// String gender = dataTable.getData("Gender");
		// String legalId = dataTable.getData("Gender");

		// asserts.SelectValueByPartialName(driver,
		// AccountPage.rdbtnSalutation,salutation);
		asserts.SetValueByPartialName(driver, AccountPage.txtFirstName,
				firstName);
		asserts.SetValueByPartialName(driver, AccountPage.txtMiddleName,
				middleName);
		asserts.SetValueByPartialName(driver, AccountPage.txtLastName, lastName);
		// asserts.SelectValueByPartialName(driver,
		// AccountPage.cmbSuffix,suffix);
		asserts.SetValueByPartialName(driver, AccountPage.txtDOB, dob);
		// asserts.SelectValueByPartialName(driver,
		// AccountPage.cmbGender,gender);
		asserts.SelectValueByPartialName(driver, AccountPage.cmbMaritalStatus,
				maritalStatus);
		// asserts.SetValueByPartialName(driver, AccountPage.txtSSN, legalId);
		asserts.SetValueByPartialName(driver, AccountPage.txtSSN, ssn);

		report.updateTestLog("Create Account", "General Info", Status.DONE);
	}

	public void addressInfo() {
		String country = dataTable.getData("CreateAccount_Data", "Country");
		String zipCode = dataTable.getData("CreateAccount_Data", "ZipCode");
		String address1 = dataTable.getData("CreateAccount_Data", "Address1");
		String address2 = dataTable.getData("CreateAccount_Data", "Address2");
		String addressType = dataTable
				.getData("CreateAccount_Data", "Address2");

		// asserts.SelectValueByPartialName(driver,
		// AccountPage.cmbaddressType,addressType);
		// asserts.AjaxSync(driver);
		asserts.SelectValueByPartialName(driver, AccountPage.cmbCountry,
				country);
		asserts.AjaxSync(driver);
		asserts.SetValueByPartialName(driver, AccountPage.txtZipCode, zipCode);
		driver.findElement(
				By.xpath("//input[contains(@name,'" + AccountPage.txtAddress1
						+ "')]")).click();
		asserts.AjaxSync(driver);
		driver.findElement(
				By.xpath("//input[contains(@name,'" + AccountPage.txtAddress1
						+ "')]")).click();
		asserts.SetValueByPartialName(driver, AccountPage.txtAddress1, address1);
		asserts.SetValueByPartialName(driver, AccountPage.txtAddress2, address2);
		report.updateTestLog("Create Account", "Address Info", Status.DONE);
		// asserts.SetValueByPartialName(driver, AccountPage.txtCity, city);
	}

	public void agencyAssgnt() {
		String agencyName = dataTable.getData("CreateAccount_Data",
				"AgencyName");
		driver.findElement(By.id("crmForm:changeCustomerProducerCdLink"))
				.click();
		asserts.SetValueByPartialName(driver, AccountPage.txtAgencyName, "CA");
		// driver.findElement(By.id("brokerSearchFromcrmCustomerBrokerCd:searchBtn")).click();
		asserts.ClickElementByPartialName(driver, "CustomerBrokerCd:searchBtn");
		// asserts.AjaxSync(driver);
		// System.out.println("1:: "+agencyName);
		System.out.println(asserts.isPresent(driver, CurMethod.LINKTEXT,
				agencyName));
		if (asserts.isPresent(driver, CurMethod.LINKTEXT, agencyName)) {
			System.out.println(agencyName);
			driver.findElement(By.linkText(agencyName)).click();
		}
		// asserts.AjaxSync(driver);
		report.updateTestLog("Create Account", "Agency Info", Status.DONE);
		// driver.findElement(By.id("brokerSearchFromcrmCustomerBrokerCd:searchBtn")).click();
	}

	public void phoneInfo() {
		String phoneType = dataTable.getData("CreateAccount_Data", "PhoneType");
		String phoneNo = dataTable.getData("CreateAccount_Data", "PhoneNumber");
		// asserts.ClickElementByPartialName(driver,"PhoneInfoTogglePanel_header");
		driver.findElement(By.id("crmForm:customerPhoneInfoTogglePanel_header"))
				.click();
		asserts.SelectValueByPartialName(driver, AccountPage.cmbphoneType,
				phoneType);
		asserts.AjaxSync(driver);
		asserts.SetValueByPartialName(driver, AccountPage.txtPhoneNo, phoneNo);
		driver.findElement(By.id("crmForm:addPhoneButton")).click();
		asserts.AjaxSync(driver);
		String preferPhone = phoneType + "-" + phoneNo;
		asserts.SelectValueByPartialName(driver, AccountPage.cmbprefPhone,
				preferPhone);
		report.updateTestLog("Create Account", "Phone Info", Status.DONE);
	}

	public void emailInfo() {
		String emailType = dataTable
				.getData("CreateAccount_Data", "Email Type");
		String eAddress = dataTable.getData("CreateAccount_Data", "Email");
		// asserts.ClickElementByPartialName(driver,"PhoneInfoTogglePanel_header");
		driver.findElement(By.id("crmForm:customerEmailInfoTogglePanel_header"))
				.click();
		asserts.SelectValueByPartialName(driver, AccountPage.cmbemailType,
				emailType);
		asserts.AjaxSync(driver);
		asserts.SetValueByPartialName(driver, AccountPage.txteAddress, eAddress);
		driver.findElement(By.id("crmForm:addEmailButton")).click();
		asserts.AjaxSync(driver);
		String prefermail = emailType + "-" + eAddress;// phoneType + "-" +
														// phoneNo;
		asserts.SelectValueByPartialName(driver, AccountPage.cmbprefEmail,
				prefermail);
		report.updateTestLog("Create Account", "Email Info", Status.DONE);
	}

	public void chatInfo() {
		String chatType = dataTable.getData("CreateAccount_Data", "Chat Type");
		String chatId = dataTable.getData("CreateAccount_Data", "Chat ID");
		driver.findElement(By.id("crmForm:customerChatInfoTogglePanel_header"))
				.click();
		asserts.SelectValueByPartialName(driver, AccountPage.cmbchatType,
				chatType);
		asserts.AjaxSync(driver);
		asserts.SetValueByPartialName(driver, AccountPage.txtchatID, chatId);
		driver.findElement(By.id("crmForm:addEmailButton")).click();
		asserts.AjaxSync(driver);
		String preferchat = chatType + "-" + chatId;
		asserts.SelectValueByPartialName(driver, AccountPage.cmbprefChat,
				preferchat);
		report.updateTestLog("Create Account", "Chat Info", Status.DONE);
	}

	public void socialInfo() {
		String socNetType = dataTable.getData("CreateAccount_Data",
				"SocNetType");
		String socNetId = dataTable.getData("CreateAccount_Data", "SocNetID");
		driver.findElement(
				By.id("crmForm:customerSocialNetInfoTogglePanel_header"))
				.click();
		asserts.SelectValueByPartialName(driver, AccountPage.cmbsocNetType,
				socNetType);
		asserts.AjaxSync(driver);
		asserts.SetValueByPartialName(driver, AccountPage.txtsocNetId, socNetId);
		driver.findElement(By.id("crmForm:addEmailButton")).click();
		asserts.AjaxSync(driver);
		String prefersocNet = socNetType + "-" + socNetId;
		asserts.SelectValueByPartialName(driver, AccountPage.cmbpreferSocNet,
				prefersocNet);
		report.updateTestLog("Create Account", "Social Info", Status.DONE);
	}

	public void webURLInfo() {
		String weburlType = dataTable.getData("CreateAccount_Data",
				"WebURLType");
		String webURL = dataTable.getData("CreateAccount_Data", "WebURL");
		driver.findElement(
				By.id("crmForm:customerWebAddressInfoTogglePanel_header"))
				.click();
		asserts.SelectValueByPartialName(driver, AccountPage.cmbweburlType,
				weburlType);
		asserts.AjaxSync(driver);
		asserts.SetValueByPartialName(driver, AccountPage.txtwebURL, webURL);
		driver.findElement(By.id("crmForm:addWebUrlButton")).click();
		asserts.AjaxSync(driver);
		report.updateTestLog("Create Account", "Web URL Info", Status.DONE);
	}

	public void OptOutInfo() {
		String optOutType = dataTable.getData("CreateAccount_Data",
				"OptoutType");
		// driver.findElement(By.id("crmForm:customerOptOutTogglePanel_header")).click();
		asserts.SelectValueByPartialName(driver, AccountPage.cmboptOutType,
				optOutType);
		asserts.AjaxSync(driver);
		driver.findElement(By.id("crmForm:addRestrictionBtn")).click();
		asserts.AjaxSync(driver);
		report.updateTestLog("Create Account", "Opt Out Info", Status.DONE);
	}

	public void relationShipInfo() {
		// String optOutType = dataTable.getData("WebURLType");
		// String webURL = dataTable.getData("WebURL");
		// driver.findElement(By.id("crmForm:customerRelationshipsTogglePanel_header")).click();
		// asserts.AjaxSync(driver);
		// asserts.SetValueByPartialName(driver, AccountPage.txtwebURL, webURL);
		asserts.AjaxSync(driver);
		driver.findElement(By.id("crmForm:addRelationshipBtn")).click();
		driver.findElement(By.id("crmForm:addRelationshipBtn")).click();

		driver.findElement(By.linkText("Select Customer")).click();
		asserts.AjaxSync(driver);
		// driver.findElement(By.id("crmForm:callCustomerSearchButtonId")).click();
		driver.findElement(By.id("customerSearch:customerSearchInfo_firstName"))
				.sendKeys("Robert");
		driver.findElement(By.id("customerSearch:searchBtn")).click();
		driver.findElement(
				By.id("customerSearch:body_customerSearchResults:0:customerName"))
				.click();
		driver.findElement(By.id("crmForm:stopCustomerSearchButtonId")).click();
		driver.findElement(By.id("selectCustomerButton")).click();
		driver.findElement(By.id("customerSearch:voidActionBtn2")).click();
		driver.findElement(By.id("cancellCustomerSearchButtonId")).click();
		driver.findElement(By.id("crmForm:relship_0_relationshipType")).clear();
		driver.findElement(By.id("crmForm:relship_0_relationshipType"))
				.sendKeys("Son");
		driver.findElement(By.id("crmForm:addRelationshipBtn")).click();
		// String prefersocNet = socNetType +"-"+socNetId;
		// asserts.SelectValueByPartialName(driver,
		// AccountPage.cmbpreferSocNet,prefersocNet);
		report.updateTestLog("Create Account", "Relation Ship Info",
				Status.DONE);
	}
}