package main

class Body extends Evidence("body", "The fellow you killed. It's still warm.", false) {
  
  def use(user: Human) = false -> ""
  
}