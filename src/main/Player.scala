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
  private var items = Map[String, Item]()
   
  
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

  
  def has(itemName: String) = this.items.contains(itemName)
  
  
  def drop(itemName: String) = {
    if (this.has(itemName)) {
      this.location.addItem(this.items(itemName))
      this.items -= itemName
      "You drop the " + itemName + "."
    } else "You don't have that!"
  }
  
  
  def examine(itemName: String) = {
    if (this.has(itemName)) "You look closely at " + itemName + ".\n" + this.items(itemName).description
    else "If you want to examine something, you need to pick it up first."
  }
  
  
  def get(itemName: String) = {
    val item = this.location.removeItem(itemName)
    if (item.isDefined) {
      this.items += itemName -> item.get
      "You pick up the " + itemName + "."
    } else "There is no " + itemName + " here to pick up."
  }
  
  
  def makeInventory() = {
    if (this.items.isEmpty) "You are empty-handed."
    else "You are carrying:\n" + this.items.map(_._2).mkString("\n")
  }
  
}

