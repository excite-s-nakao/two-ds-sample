package io.hrkt.twodssample.infrastructure.ds1;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import io.hrkt.twodssample.domain.domain.TodoEntry;
import io.hrkt.twodssample.domain.domain1.TodoRepository1;

@Mapper
@Transactional
public interface TodoMapper1 extends TodoRepository1{
    @Override
    @Select("SELECT todo from todos")
    public List<TodoEntry> selectList();

    @Override
    @Select("INSERT todos(todo) VALUES(#{todo})")
    public void save(TodoEntry todoEntry);
}
