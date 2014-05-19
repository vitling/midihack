package io.github.davw.midi

import scala.util.Random

class SimplePatternGenerator(size: Seq[Int], noteProbability: Double = 0.5, strategies: Seq[NoteStrategy], channel: Int=0) extends PatternGenerator {

  private def random(): Double = Math.random()
  private def velocity() = Math.floor(random() * 127).toInt
  override def generatePattern(): Pattern = {
    val strategy = Random.shuffle(strategies).head
    new Pattern(
      Range(0, Random.shuffle(size).head).map(i =>
        if (Math.random() < noteProbability) Some(createNote(strategy)) else None
      ), Some(this))
  }

  def createNote(strategy: NoteStrategy): Note = Note(strategy.note(), velocity(), channel)
}
