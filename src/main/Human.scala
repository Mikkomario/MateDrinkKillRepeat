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
  private val items = new Inventory
  private var searchTimes = 0 //determines whether a Police has searched this individual
  
  override def toString = { // kuvaus: sukupuoli, rooli, näennäinen humaltuneisuus, näennäinen valppaus
    ""
  }

  def hasEvidence = this.inventory.inventory.exists( _._2.exists( _.isInstanceOf[Evidence] ) )
  
  def isInShock = this.stress > 99
  
  def increaseStress(amount: Int) = this.stress = min(this.stress + amount, 100);
  
  def isPassedOut = this.alcohol > 99
  
  def isOutOfAction = this.isInShock || this.isPassedOut
  
  def beSearched() = this.searchTimes += 1
  
  def beInterrogated() =
  {
	  this.searchTimes += 1;
	  this.stress += 20;
  }
  
  def intoxication = this.alcohol
  
  def stressLevel = this.stress
  
  def stressDescription =
  {
    if (this.stress < 25) "feeling awesome"
    else if (this.stress < 50) "feeling a bit stressed out"
    else if (this.stress < 75) "feeling very stressed"
    else if (this.stress < 100) "going crazy"
    else "in total shock"
  }
  
  def intoxDescription =
  {
    if (this.alcohol < 25) "sober as a Moomin in July"
    else if (this.alcohol < 50) "slightly drunk already"
    else if (this.alcohol < 75) "drunksh"
    else if (this.alcohol < 100) "shit-faced" // krhm k-15 kö?
    else "passed out"
  }
  
  def suspectDescription: String = 
  {
	  if (this.suspect < 25)
	 	  return "the most unsuspicious fella in the world";
	  else if (this.suspect < 50)
	 	  return "not very suspicious at all"
	  else if (this.suspect < 75)
	 	  return "a rather suspicious sneak"
	  else if (this.suspect < 100)
	 	  return "a very suspicious villain, indeed"
	   
	  return "most likely a mass murderer"
  }
  
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
  def travel(direction: String) =
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
	   
	  while (!travel(directions(random.nextInt(directions.size))))
	  {
	 	  // Loopedy loop
	  }
  }
  
  
  def buyDrink(): (Boolean, String) =
  {
	  // ostaa yhden juoman, jos samassa alueessa on bartender jolla on drinkki tai useampi
    val bartenders = this.location.bartenders;
    
    if (bartenders.isEmpty)
    	return false -> "There's not a single bartender around."
    
    val vendor = bartenders.find( _._2.inventory.removeItem("drink").isDefined )
    //jos on bartender jolla on juoma, poistaa siltä juoman ja palauttaa bartenderin Optionissa
    if (vendor.isDefined)
    {
      this.inventory.addItem(new Drink)
      true -> "You pick a nice drink from the nearby bartender.";
    }
    else 
    	false -> "The bartender doesn't have any drinks right now."
  }
  
  def drink(drink: Drink) =
  {
    if (this.hasDrinks()) 
    {
    val consumed = drink.drink()
      this.stress = max(this.stress - 25, 0)
      this.alcohol = min(this.alcohol + 15, 100)
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
    else false -> "You don't have that."
  }
  
  def statusDescription: String = 
  {  
	  return this.stressDescription + ", yet " + this.intoxDescription;
  }
}