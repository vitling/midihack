package io.github.davw.midi

import javax.sound.midi.{ShortMessage, MidiMessage}

case class Note(note: Int, velocity: Int, channel: Int = 1) {
  def toNoteOn: MidiMessage = {
    val msg = new ShortMessage()
    msg.setMessage(ShortMessage.NOTE_ON, channel, note, velocity)
    msg
  }

  def toNoteOff: MidiMessage = {
    val msg = new ShortMessage()
    msg.setMessage(ShortMessage.NOTE_OFF, channel, note, velocity)
    msg
  }
}
