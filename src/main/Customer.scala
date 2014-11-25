package main

class Customer(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, 
		sex: Gender, adventure: Adventure)
      extends NPC(name, startingArea, initialDrunkenness, initialStress, sex, adventure)
{
  
  override def act: Unit =
  {
	  // Moves around, drinks or buys drinks
	  if (this.hasDrinks())
	  {
	 	  drink();
	 	  return;
	  }
	  if ((this.stressLevel > 20 || this.intoxication < 40))
	  {
	 	  if (this.buyDrink()._1)
	 	 	  return;
	  }
	   
	  move();
  }
  
  override def onEvidenceFound(piece: Evidence)
  {
	  // Stressaa ihan sikana
	  increaseStress(40);
	  this.adventure.callPolice();
  }
}