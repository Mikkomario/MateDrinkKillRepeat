package main

abstract class NPC(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, sex: Gender)
      extends Human(name, startingArea, initialDrunkenness, initialStress, sex) {
  
  def act // M‰‰ritt‰‰ mit‰ NPC tekee, t‰t‰ kutsutaan kaikille NPC:ille (jotka eiv‰t ole sammuneita) joka pelaajavuoron j‰lkeen
  
  
  // returns Option[Item]. None if there is no evidence that the NPC sees, otherwise the evidence wrapped in an Option
  def lookAround = { // katsoo ymp‰rilleen, ja riippuen vireystilastaan (alkoholi, stressi) saattaa huomata todisteen
    
  }
  
}