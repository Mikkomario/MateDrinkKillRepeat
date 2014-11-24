package main

class Bartender(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, sex: Gender)
      extends NPC(name, startingArea, initialDrunkenness, initialStress, sex)
{
	private var turnsTillRestock = 0;
  
  def act =
  {
	  // t‰ydent‰‰ juomavarastoaan (jos tyhj‰), juo (jos stressi‰ paljon)
	  turnsTillRestock -= 1;
	   
	   if (turnsTillRestock < 0)
	   {
	  	   turnsTillRestock = 3;
	  	   pourDrinks;
	   }
	   else if (this.stressLevel > 50 && this.hasDrinks())
	  	   drink();
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