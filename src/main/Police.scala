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
	  else if (this.searchesInThisArea < 3 && this.location.populationSize > 1)
	   {
	 	  // Also counts drinks as "evidence"
	 	  if (this.location.inventory.contains("drink"))
	 	   {
	 	 	  this.inventory.addItem(this.location.inventory.removeItem("drink").get)
	 	 	  this.describeHappenings(this.designation + " picked up a drink. Important evidence, you know.")
	 	 	  this.searchesInThisArea += 1;
	 	   }
	 	  
	 	  search();
	   }
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
	   
	  println(this.designation + " found the " + piece.name + " and took care of it.");
	   
	  if (Police.evidenceFound.size == 3)
	   {
	 	  this.adventure.policeOfficers.foreach { _.makeSuspect(Some(this.adventure.player)) };
	 	  println("The police now suspect you!");
	   }
	 
	 increaseStress(5);
  }
  
  override def designation: String = 
  {
	  if (this.isKnownToPlayer())
	 	  return "police " + this.name;
	  else
	 	  return "a police";
  }
  
  
  
  def suspectsPlayer = if (this.suspicion.isDefined && this.suspicion.get.isInstanceOf[Player]) true else false
  
  def makeSuspect(target: Option[Human]): Unit = this.suspicion = target;
  
  def search(): Unit =
  {
	this.increaseStress(5);
    val suspect = this.location.people.maxBy( _._2.suspect )._2
    suspect.beSearched();
    
    if (suspect.hasEvidence)
    {
    	this.suspicion = Some(suspect)
    	suspect.inventory.removeEvidence().foreach {Police.evidenceFound += _ };
    	
    	println(this.designation + " searched " + suspectName + " and found incriminating evidence.");
    	
    	// If all evidence was found, starts suspecting the player
    	if (suspect == this.adventure.player)
    		this.adventure.playerInterrogated = true;
    	else if (Police.evidenceFound.size == 3)
    	{
    		 println("The police now suspect you!");
    		this.adventure.policeOfficers.foreach { _.suspicion = Some(this.adventure.player) };
    	}
    }
    else
    	this.describeHappenings(this.designation + " searched " + suspectName + " but didn't find anything");
    
    this.searchesInThisArea += 1;
  }
  
  def interrogate(): Unit =
  {
	  if (this.suspicion.isDefined)
	  {
	 	  this.describeHappenings(this.designation + " interrogates " + this.suspectName)
	 	  
	 	  this.suspicion.get.beInterrogated();
	 	  this.interrogations += 1;
	 	   
	 	  if (this.suspicion.get == this.adventure.player)
	 	 	  this.adventure.playerInterrogated = true;
	 	   
	 	  if (this.interrogations >= 3)
	 	 	  this.suspicion = None;
	  }
  } 
  
  private def suspectName: String = 
  {
	  if (this.suspicion.isEmpty)
	 	  return "No one";
	  
	  var suspectName = "you";
	if (this.suspicion.get.isInstanceOf[NPC])
		suspectName = suspect.asInstanceOf[NPC].designation;
	
	return suspectName;
  }
}

object Police
{
	val evidenceFound = Buffer[Item]();	
}