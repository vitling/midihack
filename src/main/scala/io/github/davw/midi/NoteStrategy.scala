package io.github.davw.midi

import scala.util.Random

trait NoteStrategy {
  def note(): Int
}

case class Unison(root: Int, octaveRange: Int=3) extends NoteStrategy {
  def note() = (root + Math.floor(Math.random()*(octaveRange+1))*12).toInt
}

case class Fifths(root: Int, scaleFactor: Double) extends NoteStrategy {
  def rescale(v: Int) = ((v + 120 - root) % 12) + root
  def note() = rescale(root + (Random.nextGaussian() * scaleFactor).toInt*5)
}

case class Minor(root: Int) extends NoteStrategy {
  def note() = root + Random.shuffle(Seq(0,0,3,7)).head
}