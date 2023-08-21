package services
trait LoadInputFromStdIn {
  def apply: String
}

object LoadInputFromStdIn {
  def of: LoadInputFromStdIn = new LoadInputFromStdIn {
    override def apply: String =
      scala.io.Source.stdin.getLines
        .mkString("\n")
  }
}
