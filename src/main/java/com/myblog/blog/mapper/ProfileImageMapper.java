package com.myblog.blog.mapper;

import com.myblog.blog.dto.ProfileImageDto;
import com.myblog.blog.model.ProfileImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileImageMapper {

    @Mapping(target = "imageData", source = "profileImage.imgData")
    @Mapping(target = "imageType", source = "profileImage.imgType")
    @Mapping(target = "userId", source = "profileImage.user.id")
    ProfileImageDto profileImageToProfileImageDto(ProfileImage profileImage);

}
