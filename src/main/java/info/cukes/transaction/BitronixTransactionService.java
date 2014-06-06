package info.cukes.transaction;

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
@ApplicationScoped
public class BitronixTransactionService implements TransactionService
{
  private TransactionManager transactionManager;

  @Override
  public TransactionManager getTransactionManager()
  {
    transactionManager = TransactionManagerServices.getTransactionManager();
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
    transactionManager.getU
    return null;
  }

  @Override
  public void registerTransactionSynchronization(TransactionPhase phase, ObserverMethod<? super Object> observer,
    Object event) throws Exception
  {

  }
}
