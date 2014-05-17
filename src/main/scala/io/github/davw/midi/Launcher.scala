package io.github.davw.midi

import javax.sound.midi.MidiSystem

object Launcher {
  def main(args: Array[String]) {
    val receiver = MidiSystem.getReceiver
    val melodicPatterns = Seq(
      new SimplePatternGenerator(Seq(8,12,16), 44, 0.33, 0, 0),
      new SimplePatternGenerator(Seq(32, 16), 56, 0.2, 0, 1),
      new SimplePatternGenerator(Seq(8, 24), 44, 0.2, 0, 1),
      new SimplePatternGenerator(Seq(32), 44, 0.5, 3, 2))

    val kickSeq = Seq.tabulate(128)(i => if (i % 4 == 0 && i < 115) Some(Note(32,127,10)) else None)
    val kick = new Pattern(kickSeq)
    val hihat = new Pattern(Seq(None, None, Some(Note(33, 127, 10)), Some(Note(33, 32, 10))))
    val snare = new Pattern(Seq(None, None, None, None, Some(Note(34, 127, 10)), None, None, None))

    val bassLfo = new Lfo(1, 590)
    val sequencer = new Sequencer(receiver, mutate, 128, Seq(bassLfo))

    melodicPatterns.foreach(p => sequencer.addPattern(p.generatePattern()))
    sequencer.addPattern(kick)
    sequencer.addPattern(hihat)
    sequencer.addPattern(snare)

    val clock = new Clock(() => sequencer.step(), 60000/(122*4))
    clock.start()
  }

  def mutate(p: Pattern): Pattern =
    p.gen match {
      case Some(generator) => generator.generatePattern()
      case None => p
    }
}
