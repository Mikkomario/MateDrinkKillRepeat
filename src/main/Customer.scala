package main

class Customer(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, sex: Gender)
      extends NPC(name, startingArea, initialDrunkenness, initialStress, sex) {
  
  def act: Unit =
  {
	  // Moves around, drinks or buys drinks
	  if (this.hasDrinks())
	  {
	 	  drink();
	 	  return;
	  }
	  if ((this.stressLevel > 50 || this.intoxication < 30))
	  {
	 	  if (this.buyDrink())
	 	 	  return;
	  }
	   
	  move();
  }
}