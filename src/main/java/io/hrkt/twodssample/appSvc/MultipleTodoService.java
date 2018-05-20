package io.hrkt.twodssample.appSvc;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.hrkt.twodssample.domain.domain.TodoEntry;
import io.hrkt.twodssample.domain.domain1.TodoRepository1;
import io.hrkt.twodssample.domain.domain2.TodoRepository2;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@Transactional
@RequiredArgsConstructor
@val
public class MultipleTodoService {
  private final TodoRepository1 todoRepository1;
  private final TodoRepository2 todoRepository2;
  private final ServiceThatMayThrowException serviceThatMayThrowException;

  public List<TodoEntry> getListFrom1() {
    val list = todoRepository1.selectList();
    return list;
  }

  public List<TodoEntry> getListFrom2() {
    val list = todoRepository2.selectList();
    return list;
  }

  public TodoEntry save(@NonNull TodoEntry todoEntry) {
    todoRepository1.save(todoEntry);
    todoRepository2.save(todoEntry);
    return todoEntry;
  }

  public void save(@NonNull List<TodoEntry> todoEntries) {
    todoEntries.stream().forEach(e -> {
      todoRepository1.save(e);
      serviceThatMayThrowException.doSomeWork();
      todoRepository2.save(e);
    });
  }
}
