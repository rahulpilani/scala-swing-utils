/**
   Copyright 2010 Rahul Pilani

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

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
	
	def ->(newProperty: BoundProperty[T]): BoundProperty[T] = {
		this -> newProperty.value
	}
}