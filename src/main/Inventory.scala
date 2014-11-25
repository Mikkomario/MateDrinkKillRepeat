package main

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

class Inventory {
  
  private var items = Map[String, Buffer[Item]]()
  
  def inventory = this.items
  
  def contains(itemName: String) = this.items.contains(itemName)
  
  def getItem(itemName: String) = 
  {
    val itemBuffer = this.items.get(itemName)
    if (itemBuffer.isDefined)
    {
      Some(itemBuffer.get.head)
    }
    else None
  }
  
  def getAmount(itemName: String): Int =
  {
    if (!this.items.contains("drink"))
	 	  return 0;
	  
	  return this.items.get("drink").get.size;
  }
  
  def addItem(item: Item) =
  {
    if (this.items.contains(item.name))
      this.items.get(item.name).get += item;
    else
      this.items += (item.name -> Buffer(item));
  }
  
  def removeItem(itemName: String) =
  {
    if (this.items.contains(itemName) && this.items.get(itemName).get.size > 0)
    {
      val list = this.items.get(itemName).get
      val item = Some(list.remove(0));
      if (list.isEmpty)
        this.items -= itemName
      item
    }
    else
      None
  }
  
  def isEmpty = this.items.isEmpty
  
}