package io.hrkt.twodssample.appSvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import io.hrkt.twodssample.domain.domain.TodoEntry;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@val
public class TodoServiceTest {
    @Autowired
    @InjectMocks
    private TodoService todoService;
    
    @Mock
    private ServiceThatMayThrowException serviceThatMayThrowException;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
    @Sql("/truncate.sql")
    public void save_list_success() {
        val todoEntries = createEntries();
        when(serviceThatMayThrowException.doSomeWork()).thenReturn("1");
        todoService.save(todoEntries);
        
        val selected = todoService.getList();
        assertThat(selected.size()).isEqualTo(2);
        assertThat(Arrays.equals(selected.toArray(), todoEntries.toArray())).isTrue();
    }

    @SuppressWarnings("unchecked")
    @Test
    @Sql("/truncate.sql")
    public void save_list_fail() {
        val todoEntries = createEntries();
        //when(serviceThatMayThrowException.doSomeWork()).thenReturn("1").thenThrow(RuntimeException.class);
        when(serviceThatMayThrowException.doSomeWork()).thenThrow(RuntimeException.class);
        try {
            todoService.save(todoEntries);
        } catch(Exception e) {
            log.info("catched " + e.getMessage());
        }

        val selected = todoService.getList();
        assertThat(selected.size()).isEqualTo(0);
        assertThat(Arrays.equals(selected.toArray(), todoEntries.toArray())).isTrue();
    }
}
