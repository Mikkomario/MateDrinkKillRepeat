package main

class Drink extends Item("drink", "An alcoholic beverage", false, true)
{
  
  private var usesLeft = 2
  
  //def howFull = this.usesLeft
  
  def drink() = {
    this.usesLeft -= 1
    if (this.usesLeft == 0) true else false
  }
  
  def use(drinker: Human) = drinker.drink(this)
  
}