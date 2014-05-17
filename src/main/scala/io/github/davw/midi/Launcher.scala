package io.github.davw.midi

import javax.sound.midi.MidiSystem

object Launcher {
  def main(args: Array[String]) {
    val receiver = MidiSystem.getReceiver
    val pattern1 = new Pattern(6)
    pattern1.set(0, Note(52, 127))
    pattern1.set(1, Note(40, 127))
    pattern1.set(2, Note(64, 127))
    pattern1.set(3, Note(64, 64))
    val pattern2 = new Pattern(5)
    pattern2.set(2, Note(76, 96))
    pattern2.set(4, Note(76, 96))

    val kick = new Pattern(4)
    kick.set(0, Note(32, 127, 2))

    val sequencer = new Sequencer(receiver)
    sequencer.addPattern(pattern1)
    sequencer.addPattern(pattern2)
    sequencer.addPattern(kick)

    val clock = new Clock(() => sequencer.step(), 60000/(130*4))
    clock.start()
  }
}
