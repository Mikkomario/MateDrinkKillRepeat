package main

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer
import scala.util.Random

/**
 * The class `Area` represents locations in a text adventure game world. 
 * A game world consists of areas. In general, an "area" can be pretty much 
 * anything: a room, a building, an acre of forest, or something completely 
 * different. What different areas have in common is that players can be located 
 * in them and that they can have exits leading to other, neighboring areas. 
 * An area also has a name and a description. 
 *
 * @param name         the name of the area 
 * @param description  a basic description of the area (typically not including 
 *                     information about items)
 */
class Area(var name: String, var description: String)
{
  
  private val neighbors = Map[String, Area]()
  private val items = new Inventory
  private val population = Map[String, Human]()
  
  /**
   * Returns the area that can be reached from this area by moving in the
   * given direction.
   * 
   * @return the neighboring area, wrapped in an `Option`; `None` if there
   *         is no exit in the given direction 
   */
  def neighbor(direction: String) = this.neighbors.get(direction)

  
  /**
   * Adds an exit from this area to the given area. The neighboring area
   * is reached by moving in the specified direction from this area.
   * 
   * @param direction  the direction of the exit from this area
   * @param neighbor   the area that the exit leads to
   */
  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }

  
  /**
   * Adds exits from this area to the given areas. 
   * Calling this method is equivalent to calling the `setNeighbor`
   * method on each of the given direction--area pairs.
   * 
   * @param exits  pairs of directions and the neighboring areas in that direction
   * @see [[setNeighbor]]
   */
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }
  
  
  /**
   * Returns a multi-line description of the area as a player sees it.
   * This includes a basic description of the area as well as information
   * about exits and items. 
   */
  def fullDescription = {
    def itemList = {
      if (!this.items.isEmpty) {
      "\nYou see here: " + this.inventory.inventory.map(_._2).flatten.mkString(" ")
      } else ""
    }
    val exitList = "\n\nExits available: " + this.neighbors.mapValues( _.name ).mkString(", ")
    this.description + itemList + exitList
  } 
  
  
  /**
   * Returns a single-line description of the area for debugging purposes.  
   */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

  def inventory = this.items

  def containsPerson(person: Human): Boolean = !this.people.filter(_._2 == person).isEmpty;

  
  def contains(itemName: String) = this.inventory.contains(itemName)
  
  def addPerson(person: Human) = this.population += person.name -> person
  
  
  def removePerson(person: Human) = this.population -= person.name
  
  def people = this.population
  
  def populationSize = this.population.size;
  
  private def randomPersonFrom(pool: Map[String, Human]): Option[Human] = 
  {
	  if (pool.size == 0)
	 	  return None;
	  
	  val random = new Random();
	  return pool.get(pool.keySet.toVector(random.nextInt(pool.size)));
  }
  
  def randomDude: Option[Human] = randomPersonFrom(this.population);
  
  def customers = this.population.filter(_._2.isInstanceOf[Customer])
  
  def randomCustomer: Option[Human] = this.randomPersonFrom(this.customers)
  
  def polices = this.population.filter(_._2.isInstanceOf[Police]);
  
  def randomPolice: Option[Human] = randomPersonFrom(this.polices);
  
  def bartenders = this.population.filter(_._2.isInstanceOf[Bartender]);
  
  def randomBartender = this.randomPersonFrom(this.bartenders);
}
