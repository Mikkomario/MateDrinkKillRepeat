package main

abstract class NPC(name: String, startingArea: Area, initialDrunkenness: Int, initialStress: Int, sex: Gender)
      extends Human(name, startingArea, initialDrunkenness, initialStress, sex) {

  def act
  
}