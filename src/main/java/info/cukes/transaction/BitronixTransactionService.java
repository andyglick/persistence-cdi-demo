package info.cukes.transaction;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.apache.webbeans.spi.TransactionService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @author glick
 */
@SuppressWarnings("UnusedDeclaration")
@ApplicationScoped
public class BitronixTransactionService implements TransactionService
{
  private static BitronixTransactionManager transactionManager = null;

  public BitronixTransactionService()
  {
     transactionManager = TransactionManagerServices.getTransactionManager();
  }

  @Override
  public TransactionManager getTransactionManager()
  {
    return transactionManager;
  }

  @Override
  public Transaction getTransaction()
  {
    try
    {
      return transactionManager.getTransaction();
    }
    catch (SystemException e)
    {
      throw new RuntimeException("getTransactionFailed", e);
    }
  }

  @Override
  public UserTransaction getUserTransaction()
  {
    return (UserTransaction) transactionManager;
  }

  @Override
  public void registerTransactionSynchronization(TransactionPhase phase, ObserverMethod<? super Object> observer,
    Object event) throws Exception
  {

  }

  public void shutdown()
  {
    transactionManager.shutdown();
  }
}
