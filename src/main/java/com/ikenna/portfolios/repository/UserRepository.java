package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findByUserName(String username);
}
