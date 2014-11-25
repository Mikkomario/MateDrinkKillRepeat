package main

import scala.util.Random

abstract class NPC(name: String, startingArea: Area, initialDrunkenness: Int, 
		initialStress: Int, sex: Gender, protected val adventure: Adventure)
      extends Human(name, startingArea, initialDrunkenness, initialStress, sex)
{
  
  def act: Unit // M‰‰ritt‰‰ mit‰ NPC tekee, t‰t‰ kutsutaan kaikille NPC:ille (jotka eiv‰t ole sammuneita) joka pelaajavuoron j‰lkeen
  
  def onEvidenceFound(foundEvidence: Evidence): Unit;
  
  def designation: String;
  
  
  def isInSameRoomAsPlayer: Boolean = this.location.people.contains(this.adventure.player.name);
  
  def describeHappenings(description: String) = 
  {
	  if (this.isInSameRoomAsPlayer)
	 	  println(description);
  }
  
  def isKnownToPlayer() = this.adventure.player.knownPeople.contains(this.name.toLowerCase());
  
  private def wouldNotice(evidence: Evidence): Boolean = 
  {
	  val random = new Random();
	  return (random.nextInt(250) < this.perception + evidence.noticeability);
  }
  
  // returns Option[Item]. None if there is no evidence that the NPC sees, otherwise the evidence wrapped in an Option
  def lookAround =
  {
	  // katsoo ymp‰rilleen, ja riippuen vireystilastaan (alkoholi, stressi) saattaa huomata todisteen
	  // Jos lˆyt‰‰ todisteen huoneesta, panikoituu
	  for (piece <- this.location.inventory.evidence)
	  {
	 	  val evidencePiece = piece.asInstanceOf[Evidence];
	 	  if (this.wouldNotice(evidencePiece))
	 	 	  onEvidenceFound(evidencePiece);
	  }
	  // Jos todiste lˆytyy omasta taskusta, panikoituu viel‰ enemm‰n
	  for (piece <- this.inventory.evidence)
	  {
	 	  val evidencePiece = piece.asInstanceOf[Evidence];
	 	  if (this.wouldNotice(evidencePiece))
	 	   {
	 	 	  increaseStress(25);
	 	 	  onEvidenceFound(evidencePiece)
	 	   }
	  }
  }
}