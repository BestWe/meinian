package com.atguigu.dao;

import com.atguigu.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findRoleByUserId(Integer userId);
}
