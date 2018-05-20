package io.hrkt.twodssample.appSvc;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import io.hrkt.twodssample.domain.domain.TodoEntry;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@val
public class MultipleTodoServiceTest {
  @Autowired
  private MultipleTodoService todoService;

  @Autowired
  private ServiceThatMayThrowException serviceThatMayThrowException;

  @Rule
  public final TestName testName = new TestName();

  @Before
  public void setup() throws Exception {
    log.info("---------------------------------------------------------");
    log.info("setup for test:" + testName.getMethodName());
    log.info("---------------------------------------------------------");
  }

  private List<TodoEntry> createEntries() {
    val todoEntries = new ArrayList<TodoEntry>();
    val todo1 = TodoEntry.builder().todo("Buy salt").build();
    todoEntries.add(todo1);
    val todo2 = TodoEntry.builder().todo("Buy sugar").build();
    todoEntries.add(todo2);
    return todoEntries;
  }

  @Test
  @Sql(scripts = "/truncate.sql",
  config = @SqlConfig(dataSource = "datasource1", transactionManager = "txManager1"))
  @Sql(scripts = "/truncate.sql",
  config = @SqlConfig(dataSource = "datasource2", transactionManager = "txManager2"))
  public void save_single_success() {
    serviceThatMayThrowException.resetCounter();
    serviceThatMayThrowException.disableThrowingExcpetion();
    val todo = TodoEntry.builder().todo("Buy salt").build();
    todoService.save(todo);

    val selected1 = todoService.getListFrom1();
    assertThat(selected1.size()).isEqualTo(1);
    val selected2 = todoService.getListFrom2();
    assertThat(selected2.size()).isEqualTo(1);
  }

  @Test
  @Sql(scripts = "/truncate.sql",
  config = @SqlConfig(dataSource = "datasource1", transactionManager = "txManager1"))
  @Sql(scripts = "/truncate.sql",
  config = @SqlConfig(dataSource = "datasource2", transactionManager = "txManager2"))
  public void save_list_success() {
    val todoEntries = createEntries();
    serviceThatMayThrowException.resetCounter();
    serviceThatMayThrowException.disableThrowingExcpetion();
    todoService.save(todoEntries);

    val selected1 = todoService.getListFrom1();
    assertThat(selected1.size()).isEqualTo(2);
    val selected2 = todoService.getListFrom2();
    assertThat(selected2.size()).isEqualTo(2);
  }

  @Test
  @Sql(scripts = "/truncate.sql",
  config = @SqlConfig(dataSource = "datasource1", transactionManager = "txManager1"))
  @Sql(scripts = "/truncate.sql",
  config = @SqlConfig(dataSource = "datasource2", transactionManager = "txManager2"))
  public void save_list_fail() {
    val todoEntries = createEntries();
    serviceThatMayThrowException.resetCounter();
    serviceThatMayThrowException.setThrowExceptionAt(2);
    serviceThatMayThrowException.enableThrowingExcpetion();
    try {
      todoService.save(todoEntries);
    } catch (Exception e) {
      log.info("** catched " + e.getMessage());
    }

    val selected1 = todoService.getListFrom1();
    assertThat(selected1.size()).isEqualTo(0);
    val selected2 = todoService.getListFrom2();
    assertThat(selected2.size()).isEqualTo(1);
  }
}
