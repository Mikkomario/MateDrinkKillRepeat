package main


/**
 * The class `Action` represents actions that a player may take in a text adventure game.
 * `Action` objects are constructed on the basis of textual commands and are, in effect, 
 * parsers for such commands. An action object is immutable after creation.
 * 
 * @param input   a textual in-game command such as "go east" or "rest"
 */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
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
    if (this.verb == "go")
    {
      Some(actor.go(this.modifiers(0)))
    }
    else if (this.verb == "rest")
    {
      Some(actor.rest())
    }
    else if (this.verb == "quit")
    {
      Some(actor.quit())
    }
    else if (this.verb == "inventory")
    {
      Some(actor.makeInventory())
    }
    else if (this.verb == "get")
    {
      Some(actor.get(this.modifiers(0)))
    }
    else if (this.verb == "drop")
    {
      Some(actor.drop(this.modifiers(0)))
    }
    else if (this.verb == "examine")
    {
      Some(actor.examine(this.modifiers(0)))
    }
    else if (this.verb == "give" && this.modifiers(1) == "to")
    {
    	Some(actor.giveTo(this.modifiers(0), this.modifiers(2)));
    }
    else if (this.verb == "use")
    {
    	Some(actor.use(this.modifiers(0))._2);
    }
    else if (this.verb == "plant" && this.modifiers(1) == "on")
    {
    	// TODO: Implement
    	None;
    }
    else if (this.verb == "hide")
    {
    	// TODO: Implement
    	None;
    }
    else if (this.verb == "speak" && this.modifiers(0) == "with")
    {
    	// TODO: Implement
    	None;
    }
    else if (this.verb == "buy" && this.modifiers(0) == "drink")
    {
    	// TODO: Implement
    	None;
    }
    else if (this.verb == "look" && this.modifiers(0) == "at")
    {
    	// TODO: Implement
    	None;
    }
    else if (this.verb == "look" && this.modifiers(0) == "around")
    {
    	// TODO: Implement
    	None;
    }
    else if (this.verb == "drink")
    {
    	Some(actor.use("drink")._2);
    }
    else if (this.verb == "help")
    {
    	Some("This is suppposed to help you...");
    }
    else
    {
      None
    } 
  }


  /**
   * Returns a textual description of the action object, for debugging purposes.
   */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"  

  
}

