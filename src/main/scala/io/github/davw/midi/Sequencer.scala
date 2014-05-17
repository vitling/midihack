package io.github.davw.midi

import javax.sound.midi.Receiver
import scala.collection.mutable


class Sequencer(val destination: Receiver) {

  val patterns: mutable.Buffer[Pattern] = mutable.Buffer()
  var position: Int = 0
  var previousNote: mutable.Buffer[Note] = mutable.Buffer()
  private def time() = System.currentTimeMillis()
  def addPattern(pattern: Pattern) {
    patterns.append(pattern)
  }
  def step() {
    previousNote.foreach(note => {
      destination.send(note.toNoteOff, time())})
    previousNote.clear()
    patterns.foreach(pattern => pattern(position).foreach(note => {
        destination.send(note.toNoteOn, time())
        previousNote.append(note)}))
    position = position + 1
  }


}
