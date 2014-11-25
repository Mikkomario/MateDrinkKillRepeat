package main

class Cloth extends Evidence("cloth", "A piece of clothing partially soaked in blood.", true) {
  
  def use(user: Human) =
  {
    val knife = user.inventory.getItem("knife")
    if (knife.isDefined)
    {
      knife.get.asInstanceOf[Weapon].wipe()
      true -> "You wipe your knife clean. It's a little less suspicious now, and hey, no fingerprints!"
    }
    else false -> "What could you possibly do with a bloody rag?"
  }
  
}