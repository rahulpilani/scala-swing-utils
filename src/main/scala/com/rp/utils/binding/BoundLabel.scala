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
import com.rp.utils.SwingUtils._

class BoundLabel(val label: Label) extends Reactor {
	val textProperty = new Model[String]
	listenTo(textProperty)
	reactions += {
		case v: ModelUpdated[String] => {
			invokeLater(() => {
				label.text = v.newValue.getOrElse("")
			})
			
		}
		
	}
	
	def <=>(b: Model[String]) = {
		textProperty.bind(b)
		label
	}
}

object BoundLabel {
	implicit def Label2BoundLabel(label: Label) = new BoundLabel(label)
}