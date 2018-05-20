package io.hrkt.twodssample.domain.domain2;

import java.util.List;
import io.hrkt.twodssample.domain.domain.TodoEntry;

public interface TodoRepository2 {
  List<TodoEntry> selectList();

  void save(TodoEntry todoEntry);
}
