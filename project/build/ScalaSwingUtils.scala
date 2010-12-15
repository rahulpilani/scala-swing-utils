import sbt._

class ScalaSwingUtils(info: ProjectInfo) extends DefaultProject(info)
{
	//project name
	override val artifactID = "scala-swing-utils"
	val scalaSwing = "org.scala-lang" % "scala-swing" % "2.8.1"
    override def libraryDependencies = Set(scalaSwing) ++ super.libraryDependencies
	
    //program entry point
	override def mainClass: Option[String] = Some("com.rp.utils.BindingExample")
	
	def extraResources = "LICENSE" +++ "README.md"
    override def mainResources = super.mainResources +++ extraResources
   
}
