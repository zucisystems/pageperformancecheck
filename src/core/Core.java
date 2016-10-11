package core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import core.StoreExcelData;

public class Core {
	
	/**
	 * @param args
	 * @throws Exception
	 */
	
	  public static final String USERNAME = "amarnath.m@zucisystems.com";
	  public static final String ACCESS_KEY = "Zuci@456";
	  public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	  
	public static void main(String[] args) throws Exception{
		
	 		//Retrieve First URL from the Excel Workbook where data is stored
			StoreExcelData perfAssetsData = new StoreExcelData();
			perfAssetsData.workBookName = "Core.xls";
			perfAssetsData.workSheetName = "URLs";
			perfAssetsData.sNo = "ID_1";
			perfAssetsData.fetchHomeTestData();
			long a[] = null;

			WriteToExcel wxl = new WriteToExcel();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("Core Size");
			HSSFSheet sheet2 = wb.createSheet("Core Time");
			int writeExcelCounter = 1;

			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss") ;
			String str = dateFormat.format(date);
			File dir = new File (".");
			String strPath = dir.getCanonicalPath();
			String path = strPath+File.separator+"src"+File.separator+"testresults"+File.separator+str;

			for(int campaignCounter = 1; campaignCounter <= 1; campaignCounter++){
	
				String tempSlNo = perfAssetsData.sNo;
				String tempURL = perfAssetsData.URL;
				String tempdt = perfAssetsData.DeviceType;
				String tempua = perfAssetsData.UserAgent;
				String tempbt = perfAssetsData.BrowserType;
	
				perfAssetsData.URL = perfAssetsData.URL == null ? "" : perfAssetsData.URL.trim();
				if(perfAssetsData.URL.equals("") ){
					break;
				}

				/* Core Method calling for HAR Generation and Value Reading */
				WebDriver wd = new WebDriver();
				wd.harGenerator(perfAssetsData.URL, perfAssetsData.sNo,perfAssetsData.DeviceType, perfAssetsData.UserAgent, perfAssetsData.BrowserType, path);
				a = wd.harReader();
				System.out.println(campaignCounter+" ) "+perfAssetsData.URL+ " : "+a[0]+" , "+a[1]+" , "+a[2]+" , "+a[3]+" , "+a[4]+" , "+a[5]+" , "+a[6]+" , "+a[7]);
				System.out.println(campaignCounter+" ) "+perfAssetsData.URL+ " : "+a[8]+" , "+a[9]+" , "+a[10]+" , "+a[11]+" , "+a[12]+" , "+a[13]+" , "+a[14]+" , "+a[15]);
				
				// Fetch the next Email recipient Data
				perfAssetsData.sNo = "ID_"+ (campaignCounter+1);
				perfAssetsData.fetchHomeTestData();

				/* Write Values to Excel */
				wxl.writeToExcel(sheet1,sheet2,a,tempSlNo,tempURL,tempdt,tempua,tempbt,writeExcelCounter);
				writeExcelCounter++;
			}
			wxl.writeExcel(str, wb, path);
	}
}