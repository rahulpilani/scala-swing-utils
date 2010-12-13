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
import swing.event._
import java.awt.event._
import java.beans._
import com.rp.utils.SwingUtils._
import javax.swing.event._
import javax.swing.text._

class BoundTextComponent(t: TextComponent) extends Reactor {
	val textProperty = new BoundProperty[String]
	val textComponent = t
	listenTo(t, textProperty)
	reactions += {
		case ValueChanged(s) => {
			val changedText = s.asInstanceOf[TextComponent].text
			if (!textProperty.value.getOrElse("").equals(changedText)) {
				textProperty -> changedText
			}
		}
		case v: ValueUpdated[String] => {
			if (!v.newValue.getOrElse("").equals(t.text))
			{
				invokeLater(() => {
					//Instead of straightforward t.text = v.newValue.getOrElse(""), 
					//We have to do this convoluted way, because of the way setText works.
					//It first calls the remove and then the insertString.
					//The remove unnecessarily fires another ValueChanged event, which causes
					//All other updates to be ignored.
					
					val len = if (t.text != null) t.text.size else 0
					deafTo(t)
					t.peer.getDocument.remove(0, len)
					listenTo(t)
					t.peer.getDocument.insertString(0, v.newValue.getOrElse(""), null)
				})
			}
		}
	}
	
	def bind(b: BoundProperty[String]) = {
		textProperty.bind(b)
		textComponent
	}
}


object BoundTextComponent {
	implicit def JTextComponent2BoundTextComponent(tc: TextComponent) = new BoundTextComponent(tc)
}