package com.myblog.blog.repository;

import com.myblog.blog.model.ProfileImage;
import com.myblog.blog.model.User;
import com.myblog.blog.service.ProfileImageService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProfileImageRepository extends CrudRepository<ProfileImage, Integer> {

    public ProfileImage findByUserId(Integer userId);

    public List<ProfileImage> findAll();

    @Modifying
    @Query(value = "DELETE FROM profile_images WHERE user_id = :id", nativeQuery = true)
    public Integer deleteByUserId(@Param("id") Integer id);

    public boolean existsByUserId(Integer id);
}
