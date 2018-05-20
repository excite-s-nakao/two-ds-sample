package io.hrkt.twodssample.domain.domain2;

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
public class TodoRepository2Test {
  @Autowired
  TodoRepository2 todoRepository2;

  @Test
  @Sql(scripts = "/truncate.sql",
      config = @SqlConfig(dataSource = "datasource2", transactionManager = "txManager2"))
  public void test() {
    val todo1 = TodoEntry.builder().todo("Buy salt").build();
    todoRepository2.save(todo1);
    val selected = todoRepository2.selectList();
    assertThat(selected.size()).isEqualTo(1);
    val selectedTodo = selected.get(0);
    assertThat(selectedTodo.getTodo()).isEqualTo(todo1.getTodo());
  }

}
