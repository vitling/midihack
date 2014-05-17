package io.github.davw.midi

import scala.util.Random

class SimplePatternGenerator(size: Seq[Int], root: Int, noteProbability: Double = 0.5, octaveRange: Int=3, channel: Int=0) extends PatternGenerator {

  private def random(): Double = Math.random()
  private def velocity() = Math.floor(random() * 127).toInt
  private def note() = (root + Math.floor(random()*(octaveRange+1))*12).toInt
  override def generatePattern(): Pattern = new Pattern(
    Range(0, Random.shuffle(size).head).map(i =>
      if (Math.random() < noteProbability) Some(createNote()) else None
    ), Some(this))

  def createNote(): Note = Note(note(), velocity(), channel)
}
