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

object ModelUpdated {
  def unapply [X] (a: ModelUpdated[X]): (Option[X], Model[X]) = (a.newValue, a.source)
}

class ModelUpdated[X](val newValue: Option[X], val source: Model[X]) extends Event

class Model[T] extends Publisher {
	var v: Option[T] = None
	def value_= (other: Option[T]): Unit = v = other
	def value: Option[T] = v
	
	def bind(other: Model[T])
	{
		this.listenTo(other)
		other.listenTo(this)
		this.value = other.value
	}
	
	reactions += {
		case v: ModelUpdated[T] => {
			val o: Option[T] = v.newValue
			
			if (this != v.source && !o.getOrElse("").equals(this.value.getOrElse("")))
			{
				this -> o
			}
		}
	}

	def ->(newValue: T): Model[T] = {
		val option: Option[T] = newValue match {
			case null => None
			case "" => None
			case x: Some[T] => x
			case y => Some[T](y)
			
		}
		this -> option
	}
	
	def ->(newValue: Option[T]): Model[T] = {
		val oldValue = value
		value_=(newValue) 
		publish(new ModelUpdated(newValue, this))
		this
	}
	
	def ->(newProperty: Model[T]): Model[T] = {
		this -> newProperty.value
	}
}