package io.github.davw.midi

import javax.sound.midi.Receiver
import scala.collection.mutable
import scala.util.Random


class Sequencer(destination: Receiver, mutateFn: Pattern => Pattern,  mutateInterval: Int = 64, lfo: Seq[Lfo] = Nil) {
  def transpose(note: Note, v: Int) = Note(if (note.channel != 10) note.note + v else note.note, note.velocity, note.channel)
  var patterns: mutable.Buffer[Pattern] = mutable.Buffer()
  var activePatterns: Seq[Pattern] = Seq()

  var position: Int = 0
  var previousNote: mutable.Buffer[Note] = mutable.Buffer()
  private def time() = System.currentTimeMillis()
  def addPattern(pattern: Pattern) {
    patterns.append(pattern)
  }

  def selectPatterns(): Seq[Pattern] = {
    val rndPatterns = Random.shuffle(patterns.toSeq)
    rndPatterns.slice(0, Math.ceil(rndPatterns.size * 3 / 4.0).toInt)
  }

  def step() {
    lfo.foreach(l => destination.send(l.getMsg(position), time()))
    if (activePatterns.isEmpty) activePatterns = selectPatterns()
    if (position % mutateInterval == 0) mutate()
    previousNote.foreach(note => {
      destination.send(note.toNoteOff, time())})
    previousNote.clear()
    activePatterns.foreach(pattern => pattern(position).foreach(note => {
        val newNote = transpose(note, globalTranspose)
        destination.send(newNote.toNoteOn, time())
        previousNote.append(newNote)}))
    position = position + 1
  }

  var globalTranspose = 0
  var mutateCounter = 0
  def mutate() {
    if (mutateCounter % 8 == 0) globalTranspose = Math.floor(Math.random()*12).toInt-6
    if (mutateCounter % 2 == 0) patterns = patterns.map(mutateFn)
    activePatterns = selectPatterns()
    mutateCounter = mutateCounter + 1
  }


}
