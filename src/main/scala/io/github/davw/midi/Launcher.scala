package io.github.davw.midi

import javax.sound.midi.MidiSystem

object Launcher {
  def main(args: Array[String]) {
    val receiver = MidiSystem.getReceiver
    val patternGen = new SimplePatternGenerator(16, 44, 0.1, 0, 0)
    val patternGen2 = new SimplePatternGenerator(32, 44, 0.1, 0, 1)
    val patternGen3 = new SimplePatternGenerator(8, 44, 0.1, 0, 1)
    val kickSeq = Seq.tabulate(128)(i => if (i % 4 == 0 && i < 115) Some(Note(32,127,10)) else None)
    val kick = new Pattern(kickSeq)
    val hihat = new Pattern(Seq(None, None, Some(Note(33, 127, 10)), Some(Note(33, 32, 10))))
    val sequencer = new Sequencer(receiver, mutate, 128)
    Range(0,4).foreach(i => sequencer.addPattern(patternGen.generatePattern()))
    Range(0,2).foreach(i => sequencer.addPattern(patternGen2.generatePattern()))
    Range(0,2).foreach(i => sequencer.addPattern(patternGen3.generatePattern()))
    sequencer.addPattern(kick)
    sequencer.addPattern(hihat)

    val clock = new Clock(() => sequencer.step(), 60000/(130*4))
    clock.start()
  }

  def mutate(p: Pattern): Pattern =
    p.gen match {
      case Some(generator) => generator.generatePattern()
      case None => p
    }
}
