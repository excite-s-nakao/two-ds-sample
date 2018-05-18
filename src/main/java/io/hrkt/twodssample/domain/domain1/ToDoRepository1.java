package io.hrkt.twodssample.domain.domain1;

import java.util.List;

import io.hrkt.twodssample.domain.domain.TodoEntry;

public interface TodoRepository1 {
    List<TodoEntry> selectList();

    void save(TodoEntry todoEntry);
}
