package SeleniumExcersice;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.IllegalFormatException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcelFile {
	public String getExcelData(String sheetName, int rowNum, int cellNum) throws InvalidFormatException
	{
	String retVal=null;
	try
	{
		FileInputStream fis = new FileInputStream("D:\\Vidya\\SeleniumWorkspace\\IdealProject\\IdealTestDatas\\Ideal_ReadExcelFile.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet s = wb.getSheet(sheetName);
		Row r = s.getRow(rowNum);
		Cell c = r.getCell(cellNum);
		//retVal = c.getStringCellValue();
		//if (c.getCellType()== Cell.CELL_TYPE_BLANK )
		if (c==null)
		{
			return "";
           // retVal=c.toString();
		}
		else
		{
		retVal = c.getStringCellValue();
		}
	}
	
	catch(FileNotFoundException e)
	{
		e.printStackTrace();	
	}
	catch(EncryptedDocumentException e)
	{
		e.printStackTrace();
	}
	catch(IllegalFormatException e)
	{
		e.printStackTrace();
	}
	catch(IOException e)
	{
		e.printStackTrace();	
	}
	return retVal;
	}

	public int getLastRow(String sheetName) throws EncryptedDocumentException, IllegalFormatException, InvalidFormatException
	{
	
		int retlastNum=0;
	try
	{
		FileInputStream fis = new FileInputStream("D:\\Vidya\\SeleniumWorkspace\\IdealProject\\IdealTestDatas\\Ideal_ReadExcelFile.xlsx");	
		Workbook wb = WorkbookFactory.create(fis);
		Sheet s = wb.getSheet(sheetName);
		retlastNum=s.getLastRowNum();
		
	}
	catch(IOException e)
	{
		e.printStackTrace();	
	}
	return retlastNum;

	}
	
	public int getLastCellNum(String sheetName, int rowNum) throws Exception
	{
		int cellNum=0;
		try
		{
			FileInputStream fis = new FileInputStream("D:\\Vidya\\SeleniumWorkspace\\IdealProject\\IdealTestDatas\\Ideal_ReadExcelFile.xlsx");	
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			cellNum=r.getLastCellNum();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();	
		}
		return cellNum;
		
	}
}



