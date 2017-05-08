package com.kyh.service;

import com.kyh.model.Role;

import java.util.List;

/**
 * Created by kongyunhui on 2017/4/25.
 */
public interface RoleService {
    List<String> getRoles(Long userId);
}
