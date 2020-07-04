package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.LeaveEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends CrudRepository<LeaveEntity, Long> {

    @Query ("SELECT leaveEntity FROM LeaveEntity leaveEntity where leaveEntity.user.id=?1")
    List<LeaveEntity> findAllByUserId(long i);

}
