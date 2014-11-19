package main

class Human(val name: String, initialDrunkenness: Int, initialStress: Int, val sex: Gender) {
  
  private var alcohol = initialDrunkenness // 0 - 100
  private var stress = initialStress // 0 - 100
  
  def bloodAlcohol = this.alcohol
  
  def stressLevel = this.stress
  
  def attentionArousal = (this.alcohol + this.stress) / 2
  
  def perception = 1 / this.attentionArousal
  
  
  
}