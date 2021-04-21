package com.matheus.dias.tasklist.repositories;

import com.matheus.dias.tasklist.models.Tasks;
import javafx.concurrent.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TasksRepository extends JpaRepository<Tasks, Long>, QuerydslPredicateExecutor<Tasks> {

}
