package main

class Gender(val sex: Int) {
  
  override def toString = if (this.sex == 0) "Male" else "Female"
    
  def isMale = this.sex == 0
  
  def isFemale = !this.isMale

}

case object Male extends Gender(0)
case object Female extends Gender(1)
