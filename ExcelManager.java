import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class ExcelManager {
	
	private String chemin;


	public String getChemin() {
		return chemin;
	}


	public void setChemin(String chemin) {
		this.chemin = chemin;
	}


	public ExcelManager(String filePath) throws IOException{
		this.chemin = filePath;
	}


	public void affiche(){

		try {

			FileInputStream fis = new FileInputStream(chemin);

			Workbook workbook = null;

			workbook = new HSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			System.out.println("number of sheets :" + numberOfSheets);

			for(int i=0; i < numberOfSheets; i++){

				Sheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) 
				{

					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) 
					{

						Cell cell = cellIterator.next();

						switch(cell.getCellType()){
						case Cell.CELL_TYPE_STRING:
							System.out.println("Random data::"+cell.getStringCellValue());

							break;
						case Cell.CELL_TYPE_NUMERIC:
							System.out.println("Random data::"+cell.getNumericCellValue());
						}
					}
				}

			}

			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}





	public void modifierCellule(int i, int j, double value) throws IOException{

		FileInputStream input_document = new FileInputStream(new File(chemin));
		HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
		HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 

		Cell cell = null; 
		cell = my_worksheet.getRow(i).getCell(j);
		cell.setCellValue(value);

		input_document.close();

		FileOutputStream output_file =new FileOutputStream(new File(chemin));
		my_xls_workbook.write(output_file);
		output_file.close();


	}

	public double lireDoubleCellule(int i, int j) throws IOException{

		double retour=-1;


		FileInputStream input_document = new FileInputStream(new File(chemin));
		HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
		HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 


		Cell cell = null; 
		cell = my_worksheet.getRow(i).getCell(j);

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			System.out.println("Type String");
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				System.out.println(cell.getDateCellValue());
			} else {
				System.out.println("Type Numerique");
				System.out.println(cell.getNumericCellValue());
				retour = cell.getNumericCellValue();
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			System.out.println(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			System.out.println(cell.getCellFormula());
			break;
		default:
			System.out.println();
		}
		input_document.close();
		FileOutputStream output_file =new FileOutputStream(new File(chemin));
		my_xls_workbook.write(output_file);
		output_file.close();

		return retour;


	}

	public String lireStringCellule(int i, int j) throws IOException{

		String chaine = ""; 


		FileInputStream input_document = new FileInputStream(new File(chemin));
		HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
		HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 


		Cell cell = null; 
		cell = my_worksheet.getRow(i).getCell(j);

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			System.out.println("Type String");
			chaine = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				System.out.println(cell.getDateCellValue());
			} else {
				System.out.println(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			System.out.println(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			System.out.println(cell.getCellFormula());
			break;
		default:
			System.out.println();
		}
		input_document.close();
		FileOutputStream output_file =new FileOutputStream(new File(chemin));
		my_xls_workbook.write(output_file);
		output_file.close();

		return chaine;

	}


	public void setColor (int i, int j, double power) throws IOException{

		FileInputStream input_document = new FileInputStream(new File(chemin));
		HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
		HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 

		Cell cell = null;
		Row row = my_worksheet.getRow(i);
		cell = row.getCell(j);

		HSSFCellStyle cellStyle = my_xls_workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);

		input_document.close();

		FileOutputStream output_file =new FileOutputStream(new File(chemin));
		my_xls_workbook.write(output_file);
		output_file.close();

	}

	public Color getColor(double power)
	{
		double H = power * 0.4;
		double S = 0.9;
		double B = 0.9;

		return Color.getHSBColor((float)H, (float)S, (float)B);
	}

}
