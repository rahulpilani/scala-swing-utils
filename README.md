Scala Swing Binding Utils
-------------------------

This is an initial implementation of a binding framework for swing done in scala, hopefully trying to utilize some of scala's neat features to make the binding transparent.

Binding requires a property of an object bound to a Swing component. For now, just TextComponents are supported. More will be added later as needed. One property can be bound to multiple components.

For e.g.:


	import swing._
	import com.rp.utils.binding.BoundProperty
	import com.rp.utils.binding.BoundTextComponent._
	
	object BindingExample extends SimpleSwingApplication {
	    def top = new MainFrame {
	        title = "Binding Example"
	        val boundProperty = new BoundProperty[String]
	        
	        contents = new GridPanel(2, 1) {
	        	contents += new TextField <=> boundProperty
	        	contents += new TextField <=> boundProperty
	        }
	        
	    }
	}
