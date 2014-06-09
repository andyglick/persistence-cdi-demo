package info.cukes.transaction;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.transaction.TransactionManager;
import java.lang.invoke.MethodHandles;

/**
 * @author glick
 */
@SuppressWarnings("UnusedDeclaration")
@ApplicationScoped
public class TransactionManagerConfigurationFactory
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public @Produces @Config
  TransactionManager getConfigurationTransactionManager(InjectionPoint injectionPoint)
  {
    BitronixTransactionManager transactionManager = TransactionManagerServices.getTransactionManager();

    return transactionManager;
  }
}
