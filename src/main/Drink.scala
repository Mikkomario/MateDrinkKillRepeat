package main

class Drink extends Item("drink", "An alcoholic beverage", false, true)
{
  
  private var usesLeft = 2
  
  //def howFull = this.usesLeft
  
  def drink() = {
    this.usesLeft -= 1
    if (this.usesLeft == 0) true else false
  }
  
  def use(drinker: Human) =
  {
    if (drinker.drink(this))
    {
      if (this.usesLeft < 1) drinker.inventory.removeItem(this.name);
    	true -> "You take a healthy chug of alcoholic goodness. You feel a little calmer."
    }
    else
    	false -> "You have no drink! You should totally find a bartender to tend to your bar needs."
  }
  
}