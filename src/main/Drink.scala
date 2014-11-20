package main

class Drink extends Item("drink", "An alcoholic beverage", false) {
  
  private var usesLeft = 2
  
  def howFull = this.usesLeft
  
  def drink(drinker: Human) = {
    this.usesLeft -= 1
    drinker.drink
  }
  
}