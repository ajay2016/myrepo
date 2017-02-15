package com.qtpselenium.core.hybrid;

import java.util.Hashtable;

import com.qtpselenium.core.hybrid.util.Constants;
import com.qtpselenium.core.hybrid.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords {
	ExtentTest test;
	GenericKeywords app;
	
	//Constructor to report on every keyword
	public Keywords(ExtentTest test) {
		this.test = test;
		
	}

	public  void executeKeywords(String testUnderExecution, Xls_Reader xls,Hashtable<String,String> testData) throws InterruptedException{
		/*
		GenericKeywords app = new GenericKeywords();
		
		app.openBrowser("Mozilla");
		app.navigate("url");
		app.click("gmailLink_xpath");
		try {
			app.input("email_xpath","ajay.skiva@gmail.com");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		app.click("nextButton_xpath");
		try {
			app.input("password_xpath", "bijana94");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		app.click("signinButton_xpath");*/
		 
		//Create object of Generic Keywords
		
		 app = new GenericKeywords(test);
		//Xls_Reader xls = new Xls_Reader(Constants.SuiteA_XLS);
		//total rows in xls file
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		for(int rNum=2; rNum<=rows; rNum++){
			//get all data from TCID,Keywords,object data
			String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum); 
			if(tcid.equals(testUnderExecution)){
			String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORDS_COL, rNum); 
			String object = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum); 
			//Data column is storing key of Hashtable
			String key = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum); 
			//getting data corresponding to the key
			String data = testData.get(key);
			//test.log(LogStatus.INFO, "Opening Browser");
			if(keyword.equals("openBrowser"))
				app.openBrowser(data);
			else if (keyword.equals("navigate"))
				app.navigate(object);	
			else if(keyword.equals("click"))
				app.click(object);
			else if (keyword.equals("input")){			
					app.input(object, data);}								
			else if(keyword.equals("closeBrowser"))				
					app.closeBrowser();	
			//Instead of system.out.println we use test.log
			//this test object is initialized when keywords.java is created
			test.log(LogStatus.INFO, tcid +"*****"+keyword+"*****"+object+"*****"+data);
			}
			
			
			
			
			
		}
	

}
	public GenericKeywords getGenericKeyWords(){
		return app;
	}
}


