package com.atguigu.dao;

import com.atguigu.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> findPermissionByRoleId(Integer roleId);
}
