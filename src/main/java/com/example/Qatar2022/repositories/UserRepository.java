package com.example.Qatar2022.repositories;

import com.example.Qatar2022.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //SELECT * FROM student WHERE email=?
    Optional<UserEntity> findUserEntityByEmail(String email) ;
}
