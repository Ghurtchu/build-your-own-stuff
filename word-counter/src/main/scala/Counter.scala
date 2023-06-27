trait Counter {
  def apply(input: String): Option[CountResult]
}

object Counter {
  def apply(userOption: UserOption): Counter = (input: String) =>
    CountResult(
      userOption = userOption,
      amount = userOption match {
        case UserOption.Byte => input.getBytes.sum
        case UserOption.Character => input.split(" ").map(_.length).sum
        case UserOption.Word => input.split(" ").length
        case UserOption.Line => input.split("\n").length
      },
    )

}
