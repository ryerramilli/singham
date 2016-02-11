package io.singham.utils.poi

import org.apache.poi.xssf.usermodel._
import org.apache.poi.ss.usermodel._

object Implicits {
  
  import scala.language.implicitConversions
  
  implicit class WithPoi(file: java.io.FileInputStream) {
       
    def sheet(name: String) : XSSFSheet  = new XSSFWorkbook(file).getSheet(name)
    
  }
  
  implicit class SheetWithPoi(sheet: XSSFSheet) {
    
    def rows() : java.util.Iterator[Row]  = sheet.rowIterator()
    
  }

}