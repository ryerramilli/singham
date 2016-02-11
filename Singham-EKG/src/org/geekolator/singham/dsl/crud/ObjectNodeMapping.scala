package org.geekolator.singham.dsl.crud

import org.geekolator.singham.dsl.SinghamNode

object DbMapping {
  
  implicit class FromObject[T](domainObject: SinghamNode[T]) {
    
    def getLabel() = {
      domainObject.getClass().getSimpleName
    }
    
  }
}