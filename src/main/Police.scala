package main

import scala.collection.mutable.Buffer

class Police(name: String, startingArea: Area, initialStress: Int, sex: Gender)
      extends NPC(name, startingArea, 0, initialStress, sex)
{
  
  private var suspicion: Option[Human] = None;
  private var searchesInThisArea = 0;
  private var interrogations = 0;
  
  
  def act =
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
	 	 	  this.increaseStress(5);
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
  
  
  def suspectsPlayer = if (this.suspicion.isDefined && this.suspicion.get.isInstanceOf[Player]) true else false
  
  
  def search(): Unit =
  {
    val suspect = this.location.people.maxBy( _._2.suspect )._2
    suspect.beSearched();
    if (suspect.hasEvidence)
    {
    	this.suspicion = Some(suspect)
    	suspect.inventory.removeEvidence().foreach {Police.evidenceFound += _ };
    }
    this.searchesInThisArea += 1;
  }
  
  def interrogate(): Unit =
  {
	  if (this.suspicion.isDefined)
	  {
	 	  this.suspicion.get.beInterrogated();
	 	  this.interrogations += 1;
	 	   
	 	  if (this.interrogations >= 3)
	 	 	  this.suspicion = None;
	  }
  } 
}

object Police
{
	val evidenceFound = Buffer[Item]();	
}