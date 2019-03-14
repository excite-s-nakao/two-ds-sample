package io.hrkt.twodssample.domain.domain1;

import static org.assertj.core.api.Assertions.assertThat;
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
public class TodoRepository1Test {
  @Autowired
  TodoRepository1 toDoRepository1;

  @Rule
  public final TestName testName = new TestName();

  @Before
  public void setup() throws Exception {
    log.info("---------------------------------------------------------");
    log.info("setup for test:" + testName.getMethodName());
    log.info("---------------------------------------------------------");
  }

  @Test
  @Sql(scripts = {"/schema.sql", "/truncate.sql"}, 
      config = @SqlConfig(dataSource = DataSource1Config.DATA_SOURCE_1, transactionManager = DataSource1Config.TX_MANAGER_1))
  public void test() {
    val todo1 = TodoEntry.builder().todo("Buy salt").build();
    toDoRepository1.save(todo1);
    val selected = toDoRepository1.selectList();
    assertThat(selected.size()).isEqualTo(1);
    val selectedTodo = selected.get(0);
    assertThat(selectedTodo.getTodo()).isEqualTo(todo1.getTodo());

  }

}
