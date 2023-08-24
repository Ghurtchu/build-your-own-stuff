import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`,  mill.playlib._

object loadbalancer extends PlayModule with SingleModule {

  def scalaVersion = "2.13.11"
  def playVersion = "2.8.19"
  def twirlVersion = "1.5.1"

  object test extends PlayTests
}
