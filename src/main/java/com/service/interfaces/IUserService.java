package com.service.interfaces;

import com.dao.entities.Users;

/**
 * Created by Admin on 30.04.2017.
 */
public interface IUserService {
    public void save(Users user);
    public Users findByUsername(String username);
}
