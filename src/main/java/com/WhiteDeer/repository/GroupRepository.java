package com.WhiteDeer.repository;

import com.WhiteDeer.mapper.dto.GroupDto;
import com.WhiteDeer.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Task, Integer> {

}