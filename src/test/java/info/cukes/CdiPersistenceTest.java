package info.cukes;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zrgs.demo.eclipselink.cdi.EntityManagerProducer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RunWith(CdiTestRunner.class)
public class CdiPersistenceTest
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  static CdiContainer cdiContainer;

  @PostConstruct
  public void init()
  {
    LOGGER.warn("@PostConstruct::CdiPersistenceTest::init " + System.nanoTime());
  }

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

  @Test
  @Transactional
  public void testCdiDeployment()
  {
    Thread currentThread = Thread.currentThread();

    currentThread.setUncaughtExceptionHandler(uncaughtThreadExceptionHandler);

    LOGGER.warn("starting container");

    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    ContextControl contextControl = cdiContainer.getContextControl();

    contextControl.startContexts();
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

    Thread.setDefaultUncaughtExceptionHandler(uncaughtThreadExceptionHandler);

    LOGGER.warn("about to invoke cdiContainer.shutdown() in test method testCdiDeployment in test class " +
      "CdiPersistenceTest class");

    try
    {
      cdiContainer.shutdown();

      LOGGER.warn("after invoking cdiContainer.shutdown() THIS SHOULD NOT PRINT");
    }
    catch(Throwable t)
    {
      LOGGER.error("caught exception in afterClass method -- exception class is " + t.getClass().getName()
        + " the exception message is " + t.getMessage());
    }
    finally
    {
      LOGGER.info("exiting testCdiDeployment method of the CdiPersistenceTest class");
    }
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
}
