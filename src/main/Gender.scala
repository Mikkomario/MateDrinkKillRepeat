package main

class Gender(val sex: Int) {
  
  override def toString = if (this.sex == 0) "Male" else "Female"

}

case object Male extends Gender(0)
case object Female extends Gender(1)
