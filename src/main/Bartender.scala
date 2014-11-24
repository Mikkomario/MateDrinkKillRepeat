package main

class Bartender(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, sex: Gender)
      extends NPC(name, startingArea, initialDrunkenness, initialStress, sex) {
  
  def act = { // t‰ydent‰‰ juomavarastoaan (jos tyhj‰), juo (jos stressi‰ paljon), katselee ymp‰rilleen tai h‰lytt‰‰ poliisin
    
  }
  
  def pourDrinks = { // lis‰‰ Drinkkej‰ itselleen ostettaviksi
    for (i <- 0 to 2) {
      this.addItem(new Drink)
    }
  }
  
}