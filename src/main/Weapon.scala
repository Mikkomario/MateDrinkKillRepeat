package main

class Weapon(name: String, description: String) extends Item(name, description, true) {
  
  def use(user: Human) =
  {
    if (!user.location.people.isEmpty)
      true
    else
      false
  }
  
  
}