package io.hrkt.twodssample.appSvc;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.hrkt.twodssample.domain.domain.TodoEntry;
import io.hrkt.twodssample.domain.domain1.TodoRepository1;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@Transactional
@RequiredArgsConstructor
@val
public class TodoService {
    private final TodoRepository1 toDoRepository1;
    private final ServiceThatMayThrowException serviceThatMayThrowException;
    
    public List<TodoEntry> getList() {
        val list = toDoRepository1.selectList();
        return list;
    }
    
    public TodoEntry save(@NonNull TodoEntry todoEntry) {
        toDoRepository1.save(todoEntry);
        return todoEntry;
    }
    
    public void save(@NonNull List<TodoEntry> todoEntries) {
        todoEntries.stream().forEach(e -> {
            toDoRepository1.save(e);
            serviceThatMayThrowException.doSomeWork();
        });
    }
}
