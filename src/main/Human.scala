package main

class Human(val name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, val sex: Gender) {
  
  private var alcohol = initialDrunkenness // 0 - 100
  private var stress = initialStress // 0 - 100
  private var currentLocation = startingArea
  
  def bloodAlcohol = this.alcohol
  
  def stressLevel = this.stress
  
  def attentionArousal = (this.alcohol + this.stress) / 2
  
  def perception = 1 / this.attentionArousal
  
  def location = this.currentLocation
  
  /**
   * Attempts to move the human in the given direction. This is successful if there 
   * is an exit from the human's current location towards the given direction.
   * 
   * @param direction  a direction name (may be a nonexistent direction)
   * @return a description of the results of the attempt 
   */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
  }
  
  
  def buyDrink = {
    
  }
  
  
  def drink = {
    
  }
  
}