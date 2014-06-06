package info.cukes;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;

/**
 * @author glick
 */
public class ThreadingDelegateTest
{
  private static final transient Logger LOGGER
    = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Test
  public void navigateThreads()
  {
    Thread currentThread = Thread.currentThread();

    ThreadGroup originalThreadGroup = currentThread.getThreadGroup();

    ThreadGroup workingThreadGroup = originalThreadGroup;

    ThreadGroup topMostThreadGroup = originalThreadGroup;

    while (workingThreadGroup != null)
    {
      topMostThreadGroup = workingThreadGroup;

      workingThreadGroup = workingThreadGroup.getParent();
    }

    int activeThreadCount = topMostThreadGroup.activeCount();

    LOGGER.info("the number of threads is " + activeThreadCount);

    Thread[] activeThreads = new Thread[activeThreadCount];

    topMostThreadGroup.enumerate(activeThreads, true);

    for (Thread aThread : activeThreads)
    {
      ThreadGroup threadGroup = aThread.getThreadGroup();

      LOGGER.info("thread data is " + aThread.toString() + " threadGroup is "
        + threadGroup.toString());

      threadGroup.list();
    }
  }
}
