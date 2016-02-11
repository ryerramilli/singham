package org.geekolator.singham.wiremodels

import org.geekolator.singham.dsl.Opportunity

case class RoadmapDeliverable(val opportunity: Opportunity, val role : Option[String])

