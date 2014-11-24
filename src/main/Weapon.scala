package main

class Weapon extends Evidence("knife", "The murder weapon. It looks pretty gnarly.", true) {
  
  private var wiped = false
  
  def use(user: Human) =
  {
    if (!user.location.people.isEmpty)
      true
    else
      false
  }
  
  def isClean = this.wiped
  
  def wipe() = this.wiped = true
  
}