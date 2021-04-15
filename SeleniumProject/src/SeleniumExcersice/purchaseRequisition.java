package SeleniumExcersice;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.sun.media.sound.InvalidFormatException;

public class purchaseRequisition {
	public static void main(String arg[]) throws InvalidFormatException, InterruptedException
	{
	String driverPath=System.getProperty("user.dir")+"/DriverFiles/chromedriver.exe";
	System.setProperty("webdriver.chrome.driver", driverPath);
	WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

	ReadExcelFile xllib= new ReadExcelFile();
	String url = xllib.getExcelData("Login",2,0);
	driver.get(url); //Enter the url in the browser;

	String userName = xllib.getExcelData("Login",2,1);
	driver.findElement(By.id("userName")).sendKeys(userName); //Enter org admin username;

	String password = xllib.getExcelData("Login",2,2);
	driver.findElement(By.id("password")).sendKeys(password); //Enter org admin password;

	driver.findElement(By.id("submit")).click(); //Click on login button;

	driver.findElement(By.xpath("//span[contains(text(),'Purchase Order')]")).click(); //Click on purchase order tab;

	driver.findElement(By.xpath("//i[@class='icon-add']")).click(); //Click on add purchase icon;

	addRequisition Requisitiondetails = new addRequisition();
	String Potype = xllib.getExcelData("Requisition",1,0);
	Requisitiondetails.selectPOType(Potype, driver, xllib);  //clicking on PO type

	addRequisition EnterSA = new addRequisition();
	String ESA = xllib.getExcelData("Requisition",1,8);
	EnterSA.shippingaddress(ESA, driver, xllib);       //Enter shipping Address


	driver.findElement(By.id("poItemSearch")).click();  //clicking on Item search button

	String itemnum = xllib.getExcelData("Requisition",1,20);
	String[] items = itemnum.split(",");

	int pagenum = Integer.parseInt(driver.findElement(By.xpath("//span[@ref='lbTotal']")).getText());
	for(String itemnumber : items){
	boolean isBreak=false;
	pageLoop:for(int i=0;i<pagenum;i++){
	JavascriptExecutor js = (JavascriptExecutor) driver;
	WebElement itemno = driver.findElement(By.xpath("//div[@class='ag-body-viewport-wrapper']//preceding::div[contains(text(),'"+itemnumber+"')]"));
	js.executeScript("arguments[0].scrollIntoView(true);", itemno);
	String itemtxt = itemno.getText();
	if(itemtxt.equals(itemnumber)){
	WebElement parentele = driver.findElement(By.xpath("//div[contains(text(),'"+itemtxt+"')]/parent::div"));
	String rowId = parentele.getAttribute("row-id");

	WebElement chkbox = driver.findElement(By.xpath("//div[@class='ag-pinned-left-cols-container']/div[@row-id='"+rowId+"']/div/span/span"));
	chkbox.click();
	isBreak=true;
	}
	if (isBreak) {
	break pageLoop;
	}
	driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
	}
	}
	}
	   
	    public void selectPOType(String Potype, WebDriver driver, ReadExcelFile xllib) throws InvalidFormatException, InterruptedException
	{
	addRequisition purdetail =new addRequisition();
	if(Potype.equalsIgnoreCase("PO"))
	{
	purdetail.Purchasedetails(Potype, driver, xllib);   //In excel if PO type is given as PO then it should select that
	}
	else if (Potype.equalsIgnoreCase("Work Order With Inventory"))
	{

	driver.findElement(By.xpath("//label[contains(text(),'Work Order With Inventory')]")).click();
	purdetail.Purchasedetails(Potype, driver, xllib);
	}
	else
	{
	Potype.equalsIgnoreCase("Work Order");
	driver.findElement(By.xpath("//label[@for='poFromWork Order']")).click();  
	purdetail.Purchasedetails(Potype, driver, xllib);
	}

	}
	public void Purchasedetails(String Potype, WebDriver driver, ReadExcelFile xllib) throws InvalidFormatException, InterruptedException
	{

	driver.findElement(By.id("startDate")).click();

	addRequisition selectdate = new addRequisition();
	String startDay = xllib.getExcelData("Requisition",1,15);
	String startYear = xllib.getExcelData("Requisition",1,13);
	String startMonth = xllib.getExcelData("Requisition",1,14);
	selectdate.datepicker(startDay,startMonth, startYear, driver);

	driver.findElement(By.id("endDate")).click();

	addRequisition selectenddate = new addRequisition();
	String endDay = xllib.getExcelData("Requisition",1,18);
	String endYear = xllib.getExcelData("Requisition",1,16);
	String endMonth = xllib.getExcelData("Requisition",1,17);
	selectenddate.datepicker(endDay, endMonth, endYear, driver);

	String entityname = xllib.getExcelData("Requisition",1,1);
	Select entity = new Select(driver.findElement(By.id("entityId")));
	entity.selectByVisibleText(entityname);

	String View = xllib.getExcelData("Requisition",1,2);
	Select Viewname = new Select(driver.findElement(By.id("viewId")));
	Viewname.selectByVisibleText(View);

	String workflow = xllib.getExcelData("Requisition",1,3);
	Select WFname = new Select(driver.findElement(By.id("workflowId")));
	WFname.selectByVisibleText(workflow);

	String supplier = xllib.getExcelData("Requisition",1,4);
	Select Sup = new Select(driver.findElement(By.id("supplierId")));
	Sup.selectByVisibleText(supplier);

	String Currency = xllib.getExcelData("Requisition",1,5);
	Select Cur = new Select(driver.findElement(By.id("currency")));
	Cur.selectByVisibleText(Currency);

	String billadd = xllib.getExcelData("Requisition",1,6);
	Select billadds = new Select(driver.findElement(By.id("billingAddressId")));
	billadds.selectByVisibleText(billadd);

	String shipadd = xllib.getExcelData("Requisition",1,7);
	if (!shipadd.equals(""))
	{
	Select SA = new Select(driver.findElement(By.id("shippingAddressId")));
	SA.selectByVisibleText(shipadd);
	}

	if (Potype.equalsIgnoreCase("Work Order With Inventory"))
	{
	String woInv = xllib.getExcelData("Requisition",1,19);
	Select SA = new Select(driver.findElement(By.id("wareHouse")));
	SA.selectByVisibleText(woInv);
	}
	}

	public void shippingaddress(String ESA, WebDriver driver, ReadExcelFile xllib) throws InvalidFormatException
	{

	if(ESA.equalsIgnoreCase("Yes"))
	{

	driver.findElement(By.xpath("//label[@class='control-label addressChkBox']")).click();

	String DeliveryAddress = xllib.getExcelData("Requisition",1,9);
	driver.findElement(By.id("deliveryAddress")).sendKeys(DeliveryAddress);

	String ConNum = xllib.getExcelData("Requisition",1,10);
	driver.findElement(By.id("contactNo")).sendKeys(ConNum);

	String GSTIN = xllib.getExcelData("Requisition",1,11);
	if (!GSTIN.equals(""))
	{
	driver.findElement(By.id("tinNo")).sendKeys(GSTIN);
	}

	String VAT = xllib.getExcelData("Requisition",1,12);
	if (!VAT.equals(""))
	{
	driver.findElement(By.id("vatNo")).sendKeys(VAT);
	}
	}

	}

	public void datepicker(String day, String month, String year, WebDriver driver) throws InvalidFormatException
	{
	WebElement datePickerDiv = driver.findElement(By.id("ui-datepicker-div"));

	Select xlyr = new Select(datePickerDiv.findElement(By.className("ui-datepicker-year")));
	xlyr.selectByVisibleText(year);

	Select monthSel = new Select(datePickerDiv.findElement(By.className("ui-datepicker-month")));
	monthSel.selectByVisibleText(month);

	WebElement calTable= datePickerDiv.findElement(By.className("ui-datepicker-calendar"));
	calTable.findElement(By.linkText(String.valueOf(day))).click();
	   }
	}
}
