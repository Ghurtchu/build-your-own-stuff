package services

trait Parser[-A, +B] {
  def apply(input: A): B
}
