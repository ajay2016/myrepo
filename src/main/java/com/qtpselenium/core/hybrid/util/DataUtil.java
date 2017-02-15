package com.qtpselenium.core.hybrid.util;

import java.util.Hashtable;

public class DataUtil {
	
	public static Object[][]getData(Xls_Reader xls, String testCaseName){
    String sheetname = "Data";
		
		//only TestA1 is used
		//find out from where the test starts i.e test is on row1
		//from the 0 column of the row
	int testStartRowNum = 1;;
	while(!xls.getCellData(sheetname, 0, testStartRowNum).equals(testCaseName)){
		testStartRowNum++;
		
	}
		System.out.println("Test starts from the row "+testStartRowNum);
		//Column starts after the testname and after that data starts
		int colStartRowNum = testStartRowNum+1;
		int dataStartRowNum = testStartRowNum+2;
		//to read data no of col and row should be known
		//calculate rows
		int rows =0;
		while(!xls.getCellData(sheetname, 0, dataStartRowNum+rows).equals("")){
			rows++;
		}
		System.out.println("Total rows are  "+rows);
		
		//calculate cols
		//cols remain constants so no +
				int cols =0;
				while(!xls.getCellData(sheetname, cols, colStartRowNum).equals("")){
					cols++;
				}
				//there will only be 1 column in hashtable
				Object[][] data = new Object[rows][1];
				System.out.println("Total cols are  "+cols);
				//read the data
				int dataRow=0;
				Hashtable<String,String> table = null;
				for(int rNum=dataStartRowNum; rNum<dataStartRowNum+rows; rNum++){
					//every row has hashtable
					table = new Hashtable<String,String>();
					for(int cNum = 0; cNum<cols; cNum++){
						//to get the key and value
						//key is the col name
						String key = xls.getCellData(sheetname, cNum, colStartRowNum);
						String value = xls.getCellData(sheetname, cNum, rNum);
						//putting key value into the table
						table.put(key, value);
						
						
					}
					//put the value in the 2 dim object array which has only one column so column =0;
					//table assigned to the data
					data[dataRow][0]=table;
					dataRow++;		
	}
				return data;
				
				
				
		
	}
	
	//return true or false depending on runmode	

	public static boolean isSkip(Xls_Reader xls, String testCaseName) {
     int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		
		for(int rNum=2; rNum<=rows; rNum++){
			String tcid = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, rNum);
			if(tcid.equals(testCaseName)){
				String runmode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
				if(runmode.equals("Y"))
					return false;//do not skip
				else return true;//skip the test
				
			}			
		}
		return true;
	}

}
