package com.rodait.userservice.domain.userloginhistory;

import com.rodait.userservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {

    Long user(User user);

    List<UserLoginHistory> findUserLoginHistoriesByUserId(Long userId);
}
