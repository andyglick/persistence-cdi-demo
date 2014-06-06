package info.cukes;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.assertj.core.api.Assertions;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * @author glick
 */
@ContextConfiguration("classpath:dataSourceContext.xml")
@RunWith(CdiTestRunner.class)
public class DataSourceTest
{
  @Inject
  JDBCXADataSource jtaDataSource;

  @Test
  public void tesDataSourceInjection()
  {
    Assertions.assertThat(jtaDataSource).isNotNull();
  }
}
