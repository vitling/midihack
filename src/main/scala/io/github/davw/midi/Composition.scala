package io.github.davw.midi

import javax.sound.midi.MidiSystem

object Composition {
  def main(args: Array[String]) {
    val receiver = MidiSystem.getReceiver
    val melodicPatterns = Seq(
      new SimplePatternGenerator(Seq(8,12,16), 0.35, Seq(Unison(44, 0)), 0),
      new SimplePatternGenerator(Seq(32, 16), 0.4, Seq(Minor(56), Fifths(56, 1), Unison(56,1), Unison(56, 0)), 1),
      new SimplePatternGenerator(Seq(8, 24), 0.2, Seq(Unison(44, 0)), 1),
      new SimplePatternGenerator(Seq(32), 0.5, Seq(Unison(44, 3)), 2))

    val kickSeq = Seq.tabulate(128)(i => if (i % 4 == 0 && i < 115) Some(Note(32,127,10)) else None)
    val kick = new Pattern(kickSeq)
    val hihat = new Pattern(Seq(None, None, Some(Note(33, 127, 10)), Some(Note(33, 32, 10))))
    val snare = new Pattern(Seq(None, None, None, None, Some(Note(34, 127, 10)), None, None, None))

    val bassLfo = new Lfo(1, 350)
    val sequencer = new Sequencer(receiver, mutate, 128, Seq(bassLfo))

    melodicPatterns.foreach(p => sequencer.addPattern(p.generatePattern()))
    sequencer.addPattern(kick)
    sequencer.addPattern(hihat)
    sequencer.addPattern(snare)

    val clock = new Clock(() => sequencer.step(), 60000/(124*4))
    clock.start()
  }

  def mutate(p: Pattern): Pattern =
    p.gen match {
      case Some(generator) => generator.generatePattern()
      case None => p
    }
}
