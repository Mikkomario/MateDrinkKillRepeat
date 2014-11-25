package main


/**
 * The class `Adventure` represents text adventure games. An adventure consists of a player and 
 * a number of areas that make up the game world. It provides methods for playing the game one
 * turn at a time and for checking the state of the game.
 * 
 * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very 
 * specific adventure game that involves a small trip through a twisted forest. All newly created 
 * instances of class `Adventure` are identical to each other. To create other kinds of adventure 
 * games, you will need to modify or replace the source code of this class. 
 */
class Adventure {

  /** The title of the adventure game. */
  val title = "Mate Drink Kill Repeat"
    
  private var policeComing = false
  private var turnsUntilPoliceArrive = -1
  private var people = Vector[NPC]()
  private var policeOfficers: Option[Vector[Police]] = None
  private val maleNames = Vector[String]("John", "Tim", "Randy", "Benedict", "Stanley", "Eric", "Moses", "Chris", "Ben", "Jerry", "Timothy", "Jack", "James", "Mike", "Bob", "Seppo")
  private val femaleNames = Vector[String]("Catherine", "Christine", "Molly", "Elizabeth", "Laura", "Donna", "Lucy", "Madeleine", "Mrs. Bond", "Amélie", "Ellie")
  
  def callPolice() =
  {
    this.policeComing = true
    this.turnsUntilPoliceArrive = 2
  }
  
  
  private val toilets     = new Area("Toilets", "You are in the toilets. There are bloodspatters here and there.\nA pool of blood is forming under one of the stalls.")
  private val tables      = new Area("Tables", "You are at the tables where customers come to sit and enjoy their drinks.")
  private val bar         = new Area("Bar", "There are many fancy drinks available. Damn, only two beers on the tap.")
  private val danceFloor  = new Area("Dance Floor", "Here the music is loudest. The smells of sweat and perfume mingle obscenely in the air.")
  private val balcony     = new Area("Balcony", "Ah, fresh air! You see dense bushes on the ground, two floors down.")

        toilets.setNeighbors(Vector("north" -> tables,      "east" -> danceFloor                                           ))
         tables.setNeighbors(Vector(                        "east" -> bar,    "south" -> danceFloor,  "west" -> toilets    ))
            bar.setNeighbors(Vector("north" -> tables,                                                "west" -> danceFloor ))
     danceFloor.setNeighbors(Vector("north" -> tables,      "east" -> bar,    "south" -> balcony,     "west" -> toilets    ))
        balcony.setNeighbors(Vector("north" -> danceFloor                                                                  ))

  this.toilets.inventory.addItem(new Body)
  

  /** The character that the player controls in the game. */
  val player = new Player(toilets)
  this.player.inventory.addItem(new Weapon)
  this.player.inventory.addItem(new Cloth)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40 


  /**
   * Determines if the adventure is complete, that is, if the player has won. 
   */
  def isComplete = !this.player.has("knife") && !this.player.has("cloth")

  /**
   * Determines whether the game is over.
   * 
   * @return `true` if the player has won, lost or quit; `false` otherwise
   */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.lost

  def lost = this.player.isOutOfAction // || this.police.suspectsPlayer

  /**
   * Returns a message that is to be displayed to the player at the beginning of the game.
   */
  def welcomeMessage = "You are at a party. It's pretty cool.\nThe problem is, you just killed someone!\nYou better get rid of the evidence, before the coppers catch you!"

    
  /**
   * Returns a message that is to be displayed to the player at the end of the game. 
   * The message will be different depending on whether or not the player has completed the quest.
   */
  def goodbyeMessage = {
    if (this.isComplete) {
      "Home at last... and phew, just in time! Well done!"
    } else if (this.turnCount == this.timeLimit) {
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    } else { // game over due to player quitting
      "Quitter!" 
    }
  }

  
  /**
   * Plays a turn by executing the given in-game command.
   * (No turns elapse if the command is unknown.)
   * 
   * @param command  an in-game command such as "go west"
   * @return a textual report of what happened, or an error message if the command was unknown 
   */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) { 
      this.turnCount += 1 
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }
  
  
}

