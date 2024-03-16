package com.myblog.blog.repository;

import com.myblog.blog.model.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.email = :email AND u.password = :password")
    public User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.email = :email")
    public User findByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.username = :username")
    public User findByUsername(@Param("username") String username);

}
