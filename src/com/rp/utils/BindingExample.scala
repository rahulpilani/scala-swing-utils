package com.rp.utils

import swing._
import com.rp.utils.binding.BoundProperty
import com.rp.utils.binding.BoundTextComponent._
import com.rp.utils.binding.BoundLabel._

object BindingExample extends SimpleSwingApplication {
    def top = new MainFrame {
        title = "Binding Example"
        val boundProperty = new BoundProperty[String]
        
        contents = new GridPanel(3, 1) {
        	contents += new TextField <=> boundProperty
        	contents += new TextField <=> boundProperty
        	contents += new Label <=> boundProperty
        }
        
    }
}