package main

import scala.collection.mutable.Buffer

class Police(name: String, startingArea: Area, initialStress: Int, sex: Gender, adventure: Adventure)
      extends NPC(name, startingArea, 0, initialStress, sex, adventure)
{
  
  private var suspicion: Option[Human] = None;
  private var searchesInThisArea = 0;
  private var interrogations = 0;
  
  
  override def act =
  {
	  // moves to a different area, searches the most suspicious individual in its current 
	  // area, or interrogates a suspect with planted evidence
	  if (this.stressLevel > 70 && this.hasDrinks())
	 	  drink();
	  else if (this.suspicion.isDefined)
	  {
	 	  if (this.location.containsPerson(this.suspicion.get))
	 	 	  interrogate();
	 	  else
	 	  {
	 	 	  this.increaseStress(10);
	 	 	  move();
	 	 	  this.searchesInThisArea = 0;
	 	  }
	  }
	  else if (this.searchesInThisArea < 3)
	 	  search();
	  else
	  {
	 	  move();
	 	  this.searchesInThisArea = 0;
	  }
  }
  
  override def onEvidenceFound(piece: Evidence)
  {
	  // Collects the evidence and stresses out a bit
	  this.location.inventory.removeItem(piece.name);
	  this.inventory.removeItem(piece.name);
	  Police.evidenceFound += piece;
	   
	  if (Police.evidenceFound.size == 3)
	 	  this.adventure.policeOfficers.foreach { _.suspicion = Some(this.adventure.player) };
	 
	 increaseStress(5);
  }
  
  
  def suspectsPlayer = if (this.suspicion.isDefined && this.suspicion.get.isInstanceOf[Player]) true else false
  
  
  def search(): Unit =
  {
	this.increaseStress(5);
    val suspect = this.location.people.maxBy( _._2.suspect )._2
    suspect.beSearched();
    if (suspect.hasEvidence)
    {
    	this.suspicion = Some(suspect)
    	suspect.inventory.removeEvidence().foreach {Police.evidenceFound += _ };
    	
    	// If all evidence was found, starts suspecting the player
    	if (Police.evidenceFound.size == 3)
    		this.adventure.policeOfficers.foreach { _.suspicion = Some(this.adventure.player) };
    }
    this.searchesInThisArea += 1;
  }
  
  def interrogate(): Unit =
  {
	  if (this.suspicion.isDefined)
	  {
	 	  this.suspicion.get.beInterrogated();
	 	  this.interrogations += 1;
	 	   
	 	  if (this.suspicion.get == this.adventure.player)
	 	 	  this.adventure.playerInterrogated = true;
	 	   
	 	  if (this.interrogations >= 3)
	 	 	  this.suspicion = None;
	  }
  } 
}

object Police
{
	val evidenceFound = Buffer[Item]();	
}