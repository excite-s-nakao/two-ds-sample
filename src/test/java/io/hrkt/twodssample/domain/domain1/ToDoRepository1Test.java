package io.hrkt.twodssample.domain.domain1;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import io.hrkt.twodssample.domain.domain.TodoEntry;
import lombok.val;

@SpringBootTest
@RunWith(SpringRunner.class)
@val
public class ToDoRepository1Test {
  @Autowired
  TodoRepository1 toDoRepository1;

  @Test
  @Sql(scripts = "/truncate.sql",
      config = @SqlConfig(dataSource = "datasource1", transactionManager = "txManager1"))
  public void test() {
    val todo1 = TodoEntry.builder().todo("Buy salt").build();
    toDoRepository1.save(todo1);
    val selected = toDoRepository1.selectList();
    assertThat(selected.size()).isEqualTo(1);
    val selectedTodo = selected.get(0);
    assertThat(selectedTodo.getTodo()).isEqualTo(todo1.getTodo());

  }

}
