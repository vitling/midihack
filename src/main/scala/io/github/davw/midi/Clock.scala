package io.github.davw.midi

import java.util.concurrent.{Executors, TimeUnit, ScheduledExecutorService}

class Clock(f: () => Unit, interval: Long) {
  private val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

  def wrapFn(f: () => Unit): Runnable = new Runnable {
    override def run(): Unit = {
      try {
        f()
      } catch {
        case e: Exception => e.printStackTrace()
      }
    }
  }

  def start() {

    executor.scheduleAtFixedRate(wrapFn(f), 0, interval, TimeUnit.MILLISECONDS)
  }
}
