package main

import scala.util.Random

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
	   
	   // Chills out 60% of the time
	   if (new Random().nextDouble() < 0.4)
	 	 move();
  }
  
  override def onEvidenceFound(piece: Evidence)
  {
	  // Stressaa ihan sikana
	  this.describeHappenings(this.designation + " found the " + piece.name + " and is terrified.")
	  increaseStress(40);
	  this.adventure.callPolice();
  }
  
  override def designation: String = 
  {
	  if (isKnownToPlayer())
	 	  return this.name;
	  else
	 	  return "a customer";
  }
}