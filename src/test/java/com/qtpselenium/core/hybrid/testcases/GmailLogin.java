package com.qtpselenium.core.hybrid.testcases;


import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qtpselenium.core.hybrid.Keywords;
import com.qtpselenium.core.hybrid.util.Constants;
import com.qtpselenium.core.hybrid.util.DataUtil;
import com.qtpselenium.core.hybrid.util.ExtentManager;
import com.qtpselenium.core.hybrid.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class GmailLogin {
	
	//for extent report. since getInstance is static we can call them through object.method
	ExtentReports rep = ExtentManager.getInstance();
	ExtentTest test;
	//String testname = "GmailTest";
	String testCaseName = "GmailTest";
	Xls_Reader xls = new Xls_Reader(Constants.SuiteA_XLS);
	String sheetname = "Data";

	
	@Test(dataProvider="getData")
	public void doLogin(Hashtable<String,String>data) throws InterruptedException{			
		test = rep.startTest(testCaseName);	
		//to get complete data into the report
		test.log(LogStatus.INFO, data.toString());
		if(DataUtil.isSkip(xls, testCaseName) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Test is skipped as runmode is N");
			throw new SkipException("Test is skipped as runmode is N");
		}
		
		test.log(LogStatus.INFO, "Starting Gmail login Test");	
		Keywords app = new Keywords(test);
		
		test.log(LogStatus.INFO, "Executing Keywords");		
		app.executeKeywords(testCaseName, xls,data);
		test.log(LogStatus.PASS, "Test passed");
		app.getGenericKeyWords().reportFailure("Failed test");
		
		//after finishing test
		rep.endTest(test);
		rep.flush();
		
		
	}
	
	@DataProvider
	public Object[][]getData(){
		//Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"\\data\\SuiteA.xlsx");
		return DataUtil.getData(xls, testCaseName);
				
}
	
	
	@AfterTest
	public void quit(){
		//after finishing test
		if(rep!=null){
				rep.endTest(test);
				rep.flush();
		
	}
}
}
