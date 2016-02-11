package org.geekolator.singham.data

import io.singham.utils.poi.Implicits._
import scala.collection.JavaConversions._

object ApplicationImporter
    extends App
    with org.geekolator.singham.storage.Transactable
    with org.geekolator.singham.SinghamLogging {
  
  import java.io.FileInputStream
  import org.geekolator.singham.dsl._

  runTransaction(() => {
    
    val excel = new FileInputStream("Applications.xlsx");

    try {
      
      excel.sheet("Detail").rows.filter(_.isValid ).foreach { row =>

        App(None, row.appName) --> Contact() --> Person(None, row.contactName)
        
      }

    } finally {
      
      excel.close();
      
    }
    
  })
  
  implicit class RowImplicit(row: org.apache.poi.ss.usermodel.Row) {
    
    def appName : String = row.getCell(3).getStringCellValue.trim()
    def contactName : String = row.getCell(4).getStringCellValue.trim()
    
    def isValid() : Boolean  = {
      
      val notValid = appName.isEmpty()  || contactName.isEmpty()
      
      if(notValid) logger.warn("!!!! ****** Found empty cells ******* !!!!")
      
      !notValid
    }
    
  }

}