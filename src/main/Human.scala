package main

class Human(val name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, val sex: Gender) {
  
  private var alcohol = initialDrunkenness // 0 - 100
  private var stress = initialStress // 0 - 100
  private var currentLocation = startingArea
  private var items = Map[String, Item]()
  private var searched = false //determines whether a Police has searched this individual
  
  def hasBeenSearched = this.searched
  
  def beSearched = this.searched = true
  
  def bloodAlcohol = this.alcohol
  
  def stressLevel = this.stress
  
  def suspect = (this.alcohol + this.stress) / 2
  
  def perception = 1 / this.suspect
  
  def location = this.currentLocation
  
  def has(itemName: String) = this.items.contains(itemName)
  
  def addItem(item: Item) = this.items += item.name -> item
  
  def removeItem(itemName: String) = {
    if (this.has(itemName)) {
      val item = Some(this.items(itemName))
      this.items -= itemName
      item
    } else None
  }
  
  def inventory = this.items
  
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