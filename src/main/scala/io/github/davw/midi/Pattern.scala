package io.github.davw.midi

class Pattern(length: Int) {
  private val content: Array[Option[Note]] = Array.fill(length)(None)
  def set(index: Int, note: Note) {
    content.update(index, Some(note))
  }
  def unSet(index: Int) {
    content.update(index, None)
  }
  def apply(index: Int) = content(index % length)
}
