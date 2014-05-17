package io.github.davw.midi;

import javax.sound.midi.*;

public class Main {

  private static MidiMessage noteOn(int noteValue) throws InvalidMidiDataException {
    ShortMessage msg = new ShortMessage();
    msg.setMessage(ShortMessage.NOTE_ON, 64, 96);
    return msg;
  }

  public static void main(String[] args) throws InvalidMidiDataException, InterruptedException {
    try {
      Receiver r = MidiSystem.getReceiver();
      for (int i=0; i < 10; i++) {
        r.send(noteOn(64), System.currentTimeMillis());
        r.send(noteOn(64), System.currentTimeMillis());
        Thread.sleep(1000);
      }
    } catch (MidiUnavailableException e) {
      throw new RuntimeException(e);
    }
  }
}
