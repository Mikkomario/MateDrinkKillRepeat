package main

abstract class NPC(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, sex: Gender)
      extends Human(name, startingArea, initialDrunkenness, initialStress, sex) {
  
  def act // M‰‰ritt‰‰ mit‰ NPC tekee, t‰t‰ kutsutaan kaikille NPC:ille (jotka eiv‰t ole sammuneita) joka pelaajavuoron j‰lkeen
  
}