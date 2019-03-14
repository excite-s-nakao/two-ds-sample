package io.hrkt.twodssample.domain.domain2;

import static org.assertj.core.api.Assertions.assertThat;
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
import io.hrkt.twodssample.infrastructure.ds2.DataSource2Config;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@val
public class TodoRepository2Test {
  @Autowired
  TodoRepository2 todoRepository2;

  @Rule
  public final TestName testName = new TestName();

  @Before
  public void setup() throws Exception {
    log.info("---------------------------------------------------------");
    log.info("setup for test:" + testName.getMethodName());
    log.info("---------------------------------------------------------");
  }

  @Test
  public void doNothing() {}
  
  @Test
  @Sql(scripts = {"/schema.sql", "/truncate.sql"}, 
      config = @SqlConfig(dataSource = DataSource2Config.DATA_SOURCE_2, transactionManager = DataSource2Config.TX_MANAGER_2))
  public void test() {
    val todo1 = TodoEntry.builder().todo("Buy salt").build();
    todoRepository2.save(todo1);
    val selected = todoRepository2.selectList();
    assertThat(selected.size()).isEqualTo(1);
    val selectedTodo = selected.get(0);
    assertThat(selectedTodo.getTodo()).isEqualTo(todo1.getTodo());
  }

}
