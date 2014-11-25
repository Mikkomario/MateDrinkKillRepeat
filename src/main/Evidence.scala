package main

abstract class Evidence(name: String, description: String, canBeLifted: Boolean)
  extends Item(name, description, true, canBeLifted) {
  
  private var hiddenness = 0
  
  def plant(player: Player, target: Human): Boolean =
  {
    if (target.perception < player.intoxication)
    {
      player.inventory.removeItem(this.name)
      target.inventory.addItem(this)
      this.hiddenness = 50;
      true
    }
    else
    {
      player.increaseStress(10)
      false
    }
  }
  
  def hide() =
  {
	  this.hiddenness += 30
	  if (this.hiddenness > 90)
	 	  this.hiddenness = 90;
  }
  
  def noticeability = 100 - this.hiddenness
  
}