package com.epam.spring.core.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.core.domain.user.User;

/**
 * @author alehstruneuski
 */
public interface IUserService extends IAbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email);
    
    public void updateUser(@Nonnull User user);

}
