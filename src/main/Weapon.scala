package main

class Weapon extends Evidence("knife", "The murder weapon. It looks pretty gnarly.", true) {

  def use(user: Human) =
  {
    if (!user.location.people.isEmpty)
    {
      user.asInstanceOf[Player].murder
      true -> "You killed again! There's no way to get out of this now..."
    }
    else
      false -> "There's no-one here to shank. Bugger."
  }
  
  def wipe() = this.hide()
  
}