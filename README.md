Scala Swing Binding Utils
-------------------------

This is an initial implementation of a binding framework for swing done in scala, hopefully trying to utilize some of scala's neat features to make the binding transparent.

Binding requires a property of an object bound to a Swing component. For now, just TextComponents are supported. More will be added later as needed. One property can be bound to multiple components.

For e.g.:


	import com.rp.utils.binding.BoundProperty
	import com.rp.utils.binding.BoundTextComponent._

	object BindingExample extends SwingApplication {
		def top = new MainFrame {
			title = "Binding Example"
			val boundProperty = new BoundProperty[String]
			val textFieldA = new TextField
			val textFieldB = new TextField
    
			textFieldA.bind(boundProperty)
			textFieldB.bind(boundProperty)
			add(textFieldA)
			add(textFieldB)
		}
	}