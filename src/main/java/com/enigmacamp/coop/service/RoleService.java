package com.enigmacamp.coop.service;

import com.enigmacamp.coop.constant.RoleEnum;
import com.enigmacamp.coop.entity.Role;

public interface RoleService {
    Role getOrSave(RoleEnum role);
}
