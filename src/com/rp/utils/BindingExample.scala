package com.rp.utils

import swing._
import com.rp.utils.binding.BoundProperty
import com.rp.utils.binding.BoundTextComponent._

object BindingExample extends SimpleSwingApplication {
    def top = new MainFrame {
        title = "Binding Example"
        val boundProperty = new BoundProperty[String]
        
        contents = new GridPanel(2, 1) {
        	contents += new TextField bind boundProperty
        	contents += new TextField bind boundProperty
        }
        
    }
}