package main

abstract class Evidence(name: String, description: String, canBeLifted: Boolean)
  extends Item(name, description, true, canBeLifted) {
  
  private var hiddenness = 0
  
  def plant(target: Human): Boolean
  
  def noticeability = 100 - this.hiddenness
  
}