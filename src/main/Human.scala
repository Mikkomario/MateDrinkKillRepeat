package main

import scala.math.min
import scala.math.max
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import scala.util.Random

class Human(val name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, val sex: Gender) {
  
  private var alcohol = initialDrunkenness // 0 - 100
  private var stress = initialStress // 0 - 100
  private var currentLocation = startingArea
  private var items = Map[String, Buffer[Item]]()
  private var searchTimes = 0 //determines whether a Police has searched this individual
  
  override def toString = { // kuvaus: sukupuoli, rooli, näennäinen humaltuneisuus, näennäinen valppaus
    ""
  }
  
  def getItem(itemName: String) = 
  {
    val itemBuffer = this.items.get(itemName)
    if (itemBuffer.isDefined)
    {
      Some(itemBuffer.get.head)
    }
    else None
  }
  
  def increaseStress(amount: Int) = this.stress += amount;
  
  def isPassedOut = this.alcohol == 100
  
  def beSearched() = this.searchTimes += 1
  
  def beInterrogated() =
  {
	  this.searchTimes += 1;
	  this.stress += 20;
  }
  
  def intoxication = this.alcohol
  
  def stressLevel = this.stress
  
  def suspect = ( (this.alcohol + this.stress) / 2 ) / (1 + this.searchTimes)
  
  def perception = this.stress - this.alcohol + 50 // -50 - +150
  
  def location = this.currentLocation
  
  def has(itemName: String) = this.items.contains(itemName)
  
  def addItem(item: Item) =
  {
    if (this.items.contains(item.name))
      this.items.get(item.name).get += item;
    else
      this.items += (item.name -> Buffer(item));
  }
  
  def removeItem(itemName: String) =
  {
    if (this.has(itemName) && this.items.get(itemName).get.size > 0)
    {
      val list = this.items.get(itemName).get
      val item = Some(list.remove(0));
      if (list.isEmpty)
        this.items -= itemName
      item
    }
    else
      None
  }
  
  def inventory = this.items
  
  /**
   * Attempts to move the human in the given direction. This is successful if there 
   * is an exit from the human's current location towards the given direction.
   * 
   * @param direction  a direction name (may be a nonexistent direction)
   * @return a description of the results of the attempt 
   */
  def go(direction: String) =
  { //true jos toimii, false jos ei
    val destination = this.location.neighbor(direction)
    val originalLocation = this.location
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined)
    {
      originalLocation.removePerson(this)
      this.currentLocation.addPerson(this)
      true
    }
    else
    	false
  }
  
  /**
   * Moves to a random direction wouldn't work if the human could ge stuck
   */
  def move(): Unit = 
  {
	  // Creates the directions
	  val directions = Vector("north", "east", "south", "west");
	  val random = new Random();
	   
	  while (!go(directions(random.nextInt(directions.size))))
	  {
	 	  // Loopedy loop
	  }
  }
  
  
  def buyDrink(): Boolean = { // ostaa yhden juoman, jos samassa alueessa on bartender jolla on drinkki tai useampi
    val bartenders = this.location.people.filter( _._2.isInstanceOf[Bartender] )
    val vendor = bartenders.find( _._2.removeItem("drink").isDefined ) //jos on bartender jolla on juoma, poistaa siltä juoman ja palauttaa bartenderin Optionissa
    if (vendor.isDefined) {
      this.addItem(new Drink)
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
        this.removeItem("drink")
      }
      true
    }
    else false
  }
  
  def drink(): Boolean =
  {
	  if (!this.hasDrinks())
	 	  return false;
	   
	  val mug = this.items.get("drink").get(0).asInstanceOf[Drink];
	  return this.drink(mug);
  }
  
  def hasDrinks(): Boolean = this.drinkAmount > 0
  
  def drinkAmount: Int =
  {
	  if (!this.has("drink"))
	 	  return 0;
	  
	  return this.items.get("drink").get.size;
  }
  
  def use(itemName: String) = {
    val itemBuffer = this.items.get(itemName)
    if (itemBuffer.isDefined)
    {
      itemBuffer.get.head.use(this)
    }
    else false
  }
  
}