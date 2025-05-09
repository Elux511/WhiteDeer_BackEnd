package com.WhiteDeer.repository;

import com.WhiteDeer.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Task, Integer> {

}