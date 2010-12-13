package com.rp.utils
import javax.swing.SwingUtilities
object SwingUtils {

	def invokeLater(f: () => Unit) = {
		SwingUtilities.invokeLater(new Runnable()
		{
			def run() {
				f.apply()
			}
		})
	}
}