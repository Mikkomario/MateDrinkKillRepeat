package main

class Cloth extends Evidence("cloth", "A piece of clothing partially soaked in blood.") {
  
  def use(user: Human) =
  {
    val knife = user.getItem("knife")
    if (knife.isDefined)
    {
      knife.get.asInstanceOf[Weapon].wipe()
      true
    }
    else false
  }
  
}