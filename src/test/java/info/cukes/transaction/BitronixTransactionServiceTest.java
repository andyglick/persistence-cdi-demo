package info.cukes.transaction;


import bitronix.tm.BitronixTransactionManager;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @author glick
 */
public class BitronixTransactionServiceTest
{
  BitronixTransactionService bitronixTransactionService;

  static TransactionManager transactionManager;

  @Before
  public void setUp()
  {
    bitronixTransactionService = new BitronixTransactionService();

    transactionManager = bitronixTransactionService.getTransactionManager();
  }

  @Test
  public void testBitronixTransactionServiceReturnsUserTransaction()
  {
    UserTransaction userTransaction = bitronixTransactionService.getUserTransaction();

    Assertions.assertThat(userTransaction.getClass().isAssignableFrom(UserTransaction.class));
  }

  @Test
  public void testBitronixTransactionServiceReturnsTransactionManager()
  {
    TransactionManager transactionManager = bitronixTransactionService.getTransactionManager();

    Assertions.assertThat(transactionManager.getClass()).isSameAs(BitronixTransactionManager.class);

    Assertions.assertThat(transactionManager.getClass().isAssignableFrom(TransactionManager.class));
  }

  @AfterClass
  public static void tearDownClass()
  {
    ((BitronixTransactionManager) transactionManager).shutdown();
  }
}
