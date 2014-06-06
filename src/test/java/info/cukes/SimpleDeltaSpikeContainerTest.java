package info.cukes;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author glick
 */
@RunWith(CdiTestRunner.class)
public class SimpleDeltaSpikeContainerTest
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Test
  public void testDeltaSpikeContainerWithOWB()
  {
    CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();

    cdiContainer.boot();

    ContextControl contextControl = cdiContainer.getContextControl();

    contextControl.startContexts();

    Thread currentThread = Thread.currentThread();

    ClassLoader classLoader = currentThread.getContextClassLoader();

    LOGGER.warn("classLoader is " + classLoader.getClass().getName());

    cdiContainer.shutdown();
  }
}
