package com.chenyi.yanhuohui.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager,Long>, JpaSpecificationExecutor<Manager> {

    @Query(nativeQuery = true,value = "select * from manager where name = ?1")
    List<Manager> findByName(String name);

    //这里将猪肉总量存在了manager表中，只是为了测试而已。
    @Query(nativeQuery = true,value = "select m.role from manager m where name = 'pork'")
    String queryPork();
}
