package info.cukes;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.zrgs.demo.eclipselink.cdi.EntityManagerProducer;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RunWith(CdiTestRunner.class)
public class CdiPersistenceTest
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  static CdiContainer cdiContainer;

  static Thread.UncaughtExceptionHandler uncaughtThreadExceptionHandler = new Thread.UncaughtExceptionHandler()
  {

    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
      LOGGER.warn("");
      LOGGER.warn("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
      LOGGER.warn("thread " + t.getName() + " experienced an uncaughtThreadException");
      LOGGER.warn("exception of class " + e.getClass().getName() + " produced the message " + e.getMessage());
      LOGGER.warn("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
      LOGGER.warn("");
    }
  };

  @Inject
  AuthorRepository authorRepository;

  @Inject
  BookRepository bookRepository;

  @SuppressWarnings("UnusedDeclaration")
  @Inject
  EntityManagerProducer entityManagerProducer;

  @SuppressWarnings("UnusedDeclaration")
  @BeforeClass
  public static void setUpForTestClass()
  {
    Thread currentThread = Thread.currentThread();

    currentThread.setUncaughtExceptionHandler(uncaughtThreadExceptionHandler);

    LOGGER.warn("starting container");

    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    ContextControl contextControl = cdiContainer.getContextControl();

    contextControl.startContexts();
  }

  @Before
  public void setUp()
  {
    LOGGER.warn("in empty test sutUp method");

    Thread.setDefaultUncaughtExceptionHandler(uncaughtThreadExceptionHandler);
  }

  @Test
  @Transactional
  public void testCdiDeployment()
  {
    LOGGER.warn("entering test class");

    createAndStoreSomePersistentObjects();

    List<Author> fetchedAuthors = authorRepository.findAll();

    // Assertions.assertThat(fetchedAuthors.size()).isEqualTo(2);

    LOGGER.warn("the size of the fetchedAuthors is " + fetchedAuthors.size());

    LOGGER.warn("should be printing entered authors now");

    for (Author author : fetchedAuthors)
    {
      LOGGER.warn("author is " + author);
    }

    List<Book> fetchedBooks = bookRepository.findAll();

    LOGGER.warn("the size of the fetchedBooks is " + fetchedBooks.size());

    // Assertions.assertThat(fetchedBooks.size()).isEqualTo(1);
  }

  public void createAndStoreSomePersistentObjects()
  {

    LOGGER.warn("entering author writing method");

    Author andyGlick = new Author("Andy Glick");
    Author jimLaSpada = new Author("Jim La Spada");

    Book cucumberBook = new Book("The Cucumber Book");

    andyGlick.addAuthoredBook(cucumberBook);

    jimLaSpada.addAuthoredBook(cucumberBook);

    cucumberBook.addAnAuthor(andyGlick);
    cucumberBook.addAnAuthor(jimLaSpada);

    authorRepository.save(andyGlick);

    authorRepository.save(jimLaSpada);

    bookRepository.save(cucumberBook);

    LOGGER.warn("andy glick is " + andyGlick);
    LOGGER.warn("jim laspada is " + jimLaSpada);
    LOGGER.warn("the cucumber book is " + cucumberBook);

    LOGGER.warn("exiting author writing method");
  }

  @After
  public void tearDown()
  {
    LOGGER.warn("beginning container shutdown in tearDown method");
  }

  @AfterClass
  public static void afterClass()
  {
    LOGGER.warn("in static afterclass method of the CdiPersistenceTest class");

    try
    {
      cdiContainer.shutdown();
    }
    catch(Throwable t)
    {
      LOGGER.error("caught exception in afterClass method -- exception class is " + t.getClass().getName()
        + " the exception message is " + t.getMessage());
    }
    finally
    {
      LOGGER.info("exiting afterclass method of the CdiPersistenceTest class");
    }
  }
}
