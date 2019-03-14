package io.hrkt.twodssample.appSvc;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import io.hrkt.twodssample.domain.domain.TodoEntry;
import io.hrkt.twodssample.infrastructure.ds1.DataSource1Config;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@val
@ActiveProfiles({"test"})
public class SingleTodoServiceTest {
  @Autowired
  private SingleTodoService todoService;

  @Autowired
  private ServiceThatMayThrowException serviceThatMayThrowException;

  @Rule
  public final TestName testName = new TestName();

  @Before
  @Sql("./schema.sql")
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
  @Sql(scripts = {"/schema.sql", "/truncate.sql"},
      config = @SqlConfig(dataSource = DataSource1Config.DATA_SOURCE_1, transactionManager = DataSource1Config.TX_MANAGER_1))
  public void save_single_success() {

    serviceThatMayThrowException.resetCounter();
    serviceThatMayThrowException.disableThrowingExcpetion();
    val todo = TodoEntry.builder().todo("Buy salt").build();
    todoService.save(todo);
    val selected = todoService.getList();
    assertThat(selected.size()).isEqualTo(1);
    val expectedList = new ArrayList<>();
    expectedList.add(todo);
    assertThat(Arrays.equals(selected.toArray(), expectedList.toArray())).isTrue();
  }

  @Test
  @Sql(scripts = {"/schema.sql", "/truncate.sql"},
      config = @SqlConfig(dataSource = DataSource1Config.DATA_SOURCE_1, transactionManager = DataSource1Config.TX_MANAGER_1))
  public void save_list_success() {
    val todoEntries = createEntries();
    serviceThatMayThrowException.resetCounter();
    serviceThatMayThrowException.disableThrowingExcpetion();
    todoService.save(todoEntries);

    val selected = todoService.getList();
    assertThat(selected.size()).isEqualTo(2);
    assertThat(Arrays.equals(selected.toArray(), todoEntries.toArray())).isTrue();
  }

  @Test
  @Sql(scripts = {"/schema.sql", "/truncate.sql"},
      config = @SqlConfig(dataSource = DataSource1Config.DATA_SOURCE_1, transactionManager = DataSource1Config.TX_MANAGER_1))
  public void save_list_fail() {
    val todoEntries = createEntries();
    serviceThatMayThrowException.resetCounter();
    serviceThatMayThrowException.setThrowExceptionAt(2);
    serviceThatMayThrowException.enableThrowingExcpetion();
    try {
      todoService.save(todoEntries);
    } catch (Exception e) {
      log.info("** caught " + e.getMessage());
    }

    val selected = todoService.getList();
    assertThat(selected.size()).isEqualTo(0);
  }

  @Test(expected = NullPointerException.class)
  public void save_multiple_npe_fail() {
    List<TodoEntry> todos = null;
    todoService.save(todos);
  }

  @Test(expected = NullPointerException.class)
  public void save_single_npe_fail() {
    TodoEntry todo = null;
    todoService.save(todo);
  }
}
