package com.myblog.blog.repository;

import com.myblog.blog.model.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    public Optional<User> findByEmail(@Param("email") String email);

    public Optional<User> findByLogin(@Param("username") String username);

    public User findById(long id);

}
