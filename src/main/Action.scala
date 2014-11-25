package main

import scala.io.Source

/**
 * The class `Action` represents actions that a player may take in a text adventure game.
 * `Action` objects are constructed on the basis of textual commands and are, in effect, 
 * parsers for such commands. An action object is immutable after creation.
 * 
 * @param input   a textual in-game command such as "go east" or "rest"
 */
class Action(input: String)
{

  private val commandText = if (input != null) input.trim.toLowerCase else "";
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).split(" ");

  
  /**
   * Causes the given player to take the action represented by this object, assuming 
   * that the command was understood.
   *  
   * @param actor   a player who is to take action
   * @return a description of what happened as a result of the action (such as "You go west.").
   *         This is wrapped in an `Option`; if the command was not recognized, `None` is returned. 
   */
  def execute(actor: Player): Option[String] =
  {
    if (this.commandText == "")
      return None;
    
 	if (this.verb == "go")
    {
      return Some(actor.go(this.modifiers(1)))
    }
    else if (this.verb == "rest")
    {
      return Some(actor.rest())
    }
    else if (this.verb == "quit")
    {
      return Some(actor.quit())
    }
    else if (this.verb == "inventory")
    {
      return Some(actor.makeInventory())
    }
 	else if (this.verb == "buy" && this.modifiers(1) == "drink")
    {
    	return Some(actor.buyDrink()._2);
    }
 	 else if (this.verb == "drink")
    {
    	return Some(actor.use("drink")._2);
    }
    else if (this.verb == "help")
    {
      val helpFile = Source.fromFile("help.txt")
      val helpText = helpFile.getLines().mkString("\n")
    	return Some(helpText);
    }
 	if (this.modifiers.size >= 2)
 	{
 		if (this.verb == "get")
	    {
	      return Some(actor.get(this.modifiers(1)))
	    }
	    else if (this.verb == "drop")
	    {
	      return Some(actor.drop(this.modifiers(1)))
	    }
	    else if (this.verb == "examine")
	    {
	      return Some(actor.examine(this.modifiers(1)))
	    }
 		else if (this.verb == "use")
	    {
	    	return Some(actor.use(this.modifiers(1))._2);
	    }
	    else if (this.verb == "hide")
	    {
	    	return Some(actor.hide(this.modifiers(1)));
	    }
 	}
 	
 	if (this.modifiers.size >= 3)
 	{
 		if (this.verb == "give" && this.modifiers(2) == "to")
	    {
	    	return Some(actor.giveTo(this.modifiers(1), this.modifiers(3)));
	    }
	    else if (this.verb == "plant" && this.modifiers(2) == "on")
	    {
	    	return Some(actor.plantOn(this.modifiers(1), this.modifiers(3)))
	    }
	    else if (this.verb == "speak" && this.modifiers(1) == "with")
	    {
	    	return Some(actor.speakWith(this.modifiers(2)));
	    }
	    else if (this.verb == "look" && this.modifiers(1) == "at")
	    {
	    	return Some(actor.lookAt(this.modifiers(2)));
	    }
 	}
    
      return None;
  }


  /**
   * Returns a textual description of the action object, for debugging purposes.
   */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"  

  
}

