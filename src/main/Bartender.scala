package main

class Bartender(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, 
		sex: Gender, adventure: Adventure)
      extends NPC(name, startingArea, initialDrunkenness, initialStress, sex, adventure)
{
	private var turnsTillRestock = 0;
  
  override def act =
  {
	  // t‰ydent‰‰ juomavarastoaan (jos tyhj‰), juo (jos stressi‰ paljon)
	  turnsTillRestock -= 1;
	   
	   if (turnsTillRestock < 0)
	   {
	  	   turnsTillRestock = 3;
	  	   pourDrinks;
	  	   this.describeHappenings(this.designation + " pours drinks.");
	   }
	   else if (this.stressLevel > 50 && this.hasDrinks())
	  	   drink();
  }
  
  override def onEvidenceFound(piece: Evidence)
  {
	  // Stressaa
	  this.describeHappenings(this.designation + " saw the " + piece.name);
	  increaseStress(25);
	  this.adventure.callPolice();
  }
  
  override def designation: String =
  {
	  if (this.isKnownToPlayer())
	 	  return "bartender " + this.name;
	  else
	 	  return "a bartender";
  }
  
  def pourDrinks =
  {
	  // lis‰‰ Drinkkej‰ itselleen ostettaviksi
	  while (this.drinkAmount < 5)
	  {
	 	  this.inventory.addItem(new Drink());
	  }
  }
}