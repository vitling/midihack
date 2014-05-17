package io.github.davw.midi

import javax.sound.midi.{ShortMessage, MidiMessage}

class Lfo(cc: Int, periodSteps: Int) {
  def getMsg(index: Int): MidiMessage = {
    val mm = new ShortMessage()
    val v = Math.floor(Math.sin((index * Math.PI * 2.0) / periodSteps) * 63 + 64).toInt
    mm.setMessage(ShortMessage.CONTROL_CHANGE, cc, v)
    mm
  }
}
