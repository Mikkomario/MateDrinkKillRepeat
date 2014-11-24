package main

import scala.math.min
import scala.math.max
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map

class Human(val name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, val sex: Gender) {
  
  private var alcohol = initialDrunkenness // 0 - 100
  private var stress = initialStress // 0 - 100
  private var currentLocation = startingArea
  private val items = new Inventory
  private var searchTimes = 0 //determines whether a Police has searched this individual
  
  override def toString = { // kuvaus: sukupuoli, rooli, näennäinen humaltuneisuus, näennäinen valppaus
    ""
  }

  
  def isPassedOut = this.alcohol == 100
  
  def beSearched = this.searchTimes += 1
  
  def intoxication = this.alcohol
  
  def stressLevel = this.stress
  
  def suspect = ( (this.alcohol + this.stress) / 2 ) / (1 + this.searchTimes)
  
  def perception = this.stress - this.alcohol + 50 // -50 - +150
  
  def location = this.currentLocation
  
  def has(itemName: String) = this.inventory.getItem(itemName).isDefined
  
  def inventory = this.items
  
  /**
   * Attempts to move the human in the given direction. This is successful if there 
   * is an exit from the human's current location towards the given direction.
   * 
   * @param direction  a direction name (may be a nonexistent direction)
   * @return a description of the results of the attempt 
   */
  def go(direction: String) = { //true jos toimii, false jos ei
    val destination = this.location.neighbor(direction)
    val originalLocation = this.location
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) {
      originalLocation.removePerson(this)
      this.currentLocation.addPerson(this)
      true
    } else false
  }
  
  
  def buyDrink: Boolean = { // ostaa yhden juoman, jos samassa alueessa on bartender jolla on drinkki tai useampi
    val bartenders = this.location.people.filter( _._2.isInstanceOf[Bartender] )
    val vendor = bartenders.find( _._2.inventory.removeItem("drink").isDefined ) //jos on bartender jolla on juoma, poistaa siltä juoman ja palauttaa bartenderin Optionissa
    if (vendor.isDefined) {
      this.inventory.addItem(new Drink)
      true
    } else false
  }
  
  def drink(drink: Drink) =
  {
    if (this.hasDrinks()) 
    {
    val consumed = drink.drink()
      this.stress = max(this.stress - 25, 0)
      this.alcohol = min(this.alcohol + 15, 100)
      if (consumed) {
        this.inventory.removeItem("drink")
      }
      true
    }
    else false
  }
  
  def drink(): Boolean =
  {
	  if (!this.hasDrinks())
	 	  return false;
	   
	  val mug = this.inventory.getItem("drink").get.asInstanceOf[Drink];
	  return this.drink(mug);
  }
  
  def hasDrinks(): Boolean = this.drinkAmount > 0
  
  def drinkAmount: Int = this.inventory.getAmount("drink")
  
  def use(itemName: String) = {
    val item = this.inventory.getItem(itemName)
    if (item.isDefined)
    {
      item.get.use(this)
    }
    else false
  }
  
}