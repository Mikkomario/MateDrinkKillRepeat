package main

abstract class Evidence(name: String, description: String) extends Item(name, description, true) {
  
  private var hiddenness = 0
  
  def plant(target: Human): Boolean
  
  def noticeability = 100 - this.hiddenness
  
}