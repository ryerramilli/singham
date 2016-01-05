package io.singham.data

object ApplicationImporter
    extends App
    with io.singham.foundation.GraphApp {

  def importx() {

    import org.apache.poi.xssf.usermodel._

    import java.io.FileInputStream

    val fis = new FileInputStream("Applications.xlsx");

    try {

      val workbook = new XSSFWorkbook(fis);

      val sheet = workbook.getSheet("Detail")

      var idx = 0
      for (idx <- sheet.getFirstRowNum until sheet.getLastRowNum) {

        val row = sheet.getRow(idx)

        val cell = row.getCell(3)
        val ownerCell = row.getCell(4)

        import io.singham.dsl._

        if (ownerCell.getStringCellValue.trim().length() > 0 && cell.getStringCellValue.trim().length() > 0)
          App(cell.getStringCellValue) --> Contact() --> Person(ownerCell.getStringCellValue)
        else
          println("Found empty cells")

      }

    } finally {

      fis.close();

    }
  }

  runTransaction(importx)

}