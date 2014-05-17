package io.github.davw.midi

import javax.sound.midi.Receiver
import scala.collection.mutable


class Sequencer(destination: Receiver, mutateFn: Pattern => Pattern,  mutateInterval: Int = 64) {

  var patterns: mutable.Buffer[Pattern] = mutable.Buffer()
  var position: Int = 0
  var previousNote: mutable.Buffer[Note] = mutable.Buffer()
  private def time() = System.currentTimeMillis()
  def addPattern(pattern: Pattern) {
    patterns.append(pattern)
  }
  def step() {
    if (position % mutateInterval == 0) mutate()
    previousNote.foreach(note => {
      destination.send(note.toNoteOff, time())})
    previousNote.clear()
    patterns.foreach(pattern => pattern(position).foreach(note => {
        destination.send(note.toNoteOn, time())
        previousNote.append(note)}))
    position = position + 1
  }

  def mutate() {
    patterns = patterns.map(mutateFn)
  }


}
