object Syntax {

  implicit class OptionOps[A](self: Option[A]) {
    def str: String = self.fold("")(_.toString)
  }

  implicit class LoggingOps[A](self: A) {
    def log: Unit = println(self)
  }

}
