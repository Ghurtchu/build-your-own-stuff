trait Counter {
  def apply(input: String): CountResult
}

object Counter {
  def apply(userOption: UserOption): Counter = (input: String) => ???
}
