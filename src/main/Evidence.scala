package main

abstract class Evidence(name: String, description: String, canBeLifted: Boolean)
  extends Item(name, description, true, canBeLifted) {
  
  private var hiddenness = 0
  
  def plant(player: Player, target: Human): String =
  {
    if (target.perception < player.intoxication)
    {
      player.inventory.removeItem(this.name)
      target.inventory.addItem(this)
      this.hiddenness = 50;
      return "You successfully plant " + this.name + ".";
    }
    else
    {
      player.increaseStress(10)
      return "You chicken out before you can plant the " + this.name + ".";
    }
  }
  
  def hide(): String =
  {
	  this.hiddenness += 30
	  
	  if (this.hiddenness > 89)
	  {
	 	  this.hiddenness = 90;
	 	  return "You hide the " + this.name + 
	 	  		" but it seems like you can't hide the it much better than this";
	  }
	   
	  if (this.hiddenness < 50)
	  	return "You hide the " + this.name + " into a somewhat visible spot in a hurry";
	  else
	 	  return "You hide the " + this.name + " in a very good hiding spot.";
  }
  
  def noticeability = 100 - this.hiddenness
  
}