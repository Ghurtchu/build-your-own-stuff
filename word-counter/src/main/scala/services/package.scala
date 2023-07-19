import domain.{CountResult, MultiCountResult}
import services.ParseCmdAndThenCountWords.Error

package object services {

  object OptionSyntax {
    implicit class OptionOps(self: Option[MultiCountResult]) {
      def logResult(): Unit = {
        val filepath = self.fold("")(_.filepath)

        self.fold(println(s"could not open file: $filepath"))(println)
      }
    }
  }

  object EitherSyntax {
    implicit class EitherOps(self: Either[Error, CountResult]) {
      def logResult(): Unit = self.fold(err => println(err.msg), println)
    }
  }

}
