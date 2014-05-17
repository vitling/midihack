package io.github.davw.midi

class Pattern(val content: Seq[Option[Note]], val gen: Option[PatternGenerator] = None) {
  def apply(index: Int) = content(index % content.size)

  override def toString = content.toString()
}
