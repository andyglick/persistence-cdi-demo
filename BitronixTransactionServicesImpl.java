package info.cukes.transaction;

import bitronix.tm.TransactionManagerServices;
import org.jboss.weld.transaction.spi.TransactionServices;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;
import java.util.Hashtable;

@ApplicationScoped
public class BitronixTransactionServicesImpl implements TransactionServices
{
  static  Context ctx;

  static {
    Hashtable env = new Hashtable();

    env.put(Context.INITIAL_CONTEXT_FACTORY, "bitronix.tm.jndi.BitronixInitialContextFactory");

    try {
      ctx = new InitialContext(env);
      ctx.lookup(TransactionManagerServices.getConfiguration().getJndiUserTransactionName());
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void registerSynchronization(Synchronization synchronizedObserver) {

    try {
      TransactionSynchronizationRegistry reg = ((TransactionSynchronizationRegistry) ctx.lookup(TransactionManagerServices.getConfiguration().getJndiTransactionSynchronizationRegistryName()));
      reg.registerInterposedSynchronization(synchronizedObserver);
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isTransactionActive() {
    try {
      return getUserTransaction().getStatus() == Status.STATUS_ACTIVE;
    } catch (final Exception e) {
      return false;
    }
  }

  @Override
  public UserTransaction getUserTransaction() {
    try {
      return (UserTransaction) ctx.lookup(TransactionManagerServices.getConfiguration().getJndiUserTransactionName());
    } catch (NamingException e) {
      e.printStackTrace();
    }
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void cleanup() {
  }
}
