package main

import scala.collection.mutable.Map

  
/**
 * A `Player` object represents a player character controlled by the real-life user of the program. 
 * 
 * A player object's state is mutable: the player's location and possessions can change, for instance.
 * 
 * @param startingArea  the initial location of the player
 */
class Player(startingArea: Area) extends Human("Tom", startingArea, 0, 60, Male) {

  private var quitCommandGiven = false              // one-way flag
  private var hasKilledAgain = false                // one-way flag
   
  
  private def findTarget(targetName: String): Option[Human] =
  {
	  if (targetName == this.name)
	 	  return None;
	  else if (targetName == "police")
	 	  return this.location.randomPolice;
	  else if (targetName == "bartender")
	 	  return this.location.randomBartender;
	  else if (targetName == "someone")
	  {
	 	  if (this.location.populationSize == 1)
	 	 	  return None;
	 	  else
	 	  {
	 	 	  var target: Option[Human] = Some(this);
	 	 	  while (target.get == this)
	 	 	  {
	 	 	 	  target = this.location.randomDude;
	 	 	  }
	 	 	  return target;
	 	  }
	  }
	   
	  return this.location.people.get(targetName);
  }
  
  def murder = this.hasKilledAgain = true
  
  def isSerialKiller = this.hasKilledAgain
  
  /**
   * Determines if the player has indicated a desire to quit the game.
   */
  def hasQuit = this.quitCommandGiven

  
  /**
   * Causes the player to rest for a short while (this has no substantial effect in game terms).
   * 
   * @return a description of what happened
   */
  def rest() = {
    "You rest for a while. Better get a move on, though." 
  }
  
  
  /**
   * Signals that the player wants to quit the game.
   * 
   * @return a description of what happened within the game as a result (which is the empty string, in this case) 
   */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  
  /**
   * Returns a brief description of the player's state, for debugging purposes.
   */
  override def toString = "Now at: " + this.location.name   

  
  
  def drop(itemName: String) = {
    val item = this.inventory.removeItem(itemName)
    if (item.isDefined)
    {
      this.location.inventory.addItem(item.get)
      "You drop the " + itemName + "."
    }
    else "You don't have that!"
  }
  
  
  def examine(itemName: String) = {
    if (this.has(itemName)) "You look closely at " + itemName + ".\n" + this.inventory.getItem(itemName).get.description
    else "If you want to examine something, you need to pick it up first."
  }
  
  def go(direction: String) =
  {
    if (this.travel(direction)) "You go to the " + this.location.name + "."
    else "You can't go there!"
  }
  
  def get(itemName: String) = {
    val item = this.location.inventory.removeItem(itemName)
    if (item.isDefined) {
      this.inventory.addItem(item.get)
      "You pick up the " + itemName + "."
    } else "There is no " + itemName + " here to pick up."
  }
  
  
  def makeInventory() = {
    if (this.inventory.isEmpty) "You are empty-handed."
    else "You are carrying:\n" + this.inventory.inventory.map( _._2 ).flatten.mkString("\n")
  }
  
  def giveTo(itemName: String, targetName: String): String = 
  {
	  val target = findTarget(targetName);
	   
	  if (target.isEmpty)
	 	  return "You can't see a single " + targetName;
	  
	  
	  val item = this.inventory.getItem(itemName);
	  if (item.isEmpty)
	 	  return "You don't have a single " + itemName + " to give.";
	   
	  // Moves the item to the recipient
	   this.inventory.removeItem(item.get.name);
	   target.get.inventory.addItem(item.get);
	  
	   // If you give evidence, it will be automaticlaly noticed
	   // if you give it to police, they start to suspect you
	  if (item.get.isInstanceOf[Evidence])
	  {
	 	  if (target.get.isInstanceOf[NPC])
	 	 	  target.get.asInstanceOf[NPC].onEvidenceFound(item.asInstanceOf[Evidence]);
	 	   
	 	  if (target.get.isInstanceOf[Police])
	 	  {
	 	 	  target.get.asInstanceOf[Police].makeSuspect(Some(this));
	 	 	  return "You hand over inciminating evidence to the police, good job."
	 	  }
	  }
	   
	  return "You place " + itemName + " into " + targetName + "'s hands.";
  }
}


