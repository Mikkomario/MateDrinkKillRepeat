package main

class Police(name: String, startingArea: Area, initialStress: Int, sex: Gender)
      extends NPC(name, startingArea, 0, initialStress, sex) {
  
  private var suspicion: Option[Human] = None
  
  
  def act = { // moves to a different area, searches the most suspicious individual in its current area, or interrogates a suspect with planted evidence
    
  }
  
  
  def suspectsPlayer = if (this.suspicion.isDefined && this.suspicion.get.isInstanceOf[Player]) true else false
  
  
  def search = {
    val suspect = this.location.people.maxBy( _._2.suspect )._2
    suspect.beSearched
// if (suspect.has(EVIDENCE)) {
    this.suspicion = Some(suspect)
    //}
    
  }
  
  def interrogate = {
    
  }
  
}