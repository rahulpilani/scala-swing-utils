package com.rp.utils.binding
import swing._
import event._
import com.rp.utils.SwingUtils._

object ValueUpdated {
  def unapply [X] (a: ValueUpdated[X]): (Option[X], BoundProperty[X]) = (a.newValue, a.source)
}

class ValueUpdated[X](val newValue: Option[X], val source: BoundProperty[X]) extends Event

class BoundProperty[T] extends Publisher {
	var value: Option[T] = None
		
	def bind(other: BoundProperty[T])
	{
		this.listenTo(other)
		other.listenTo(this)
		this.value = other.value
	}
	
	reactions += {
		case v: ValueUpdated[T] => {
			val o: Option[T] = v.newValue
			
			if (this != v.source && !o.getOrElse("").equals(value.getOrElse("")))
			{
				this -> o
			}
		}
	}

	def ->(newValue: T): BoundProperty[T] = {
		val option: Option[T] = newValue match {
			case null => None
			case "" => None
			case x: Some[T] => x
			case y => Some[T](y)
			
		}
		this -> option
	}
	
	def ->(newValue: Option[T]): BoundProperty[T] = {
		val oldValue = value
		value = newValue 
		publish(new ValueUpdated(newValue, this))
		this
	}
	
	def :=(newProperty: BoundProperty[T]): BoundProperty[T] = {
		this -> newProperty.value
	}
}