package com.myblog.blog.service;

import com.myblog.blog.dto.ProfileImageDto;
import com.myblog.blog.exception.InvalidSizeException;
import com.myblog.blog.exception.NotFoundException;
import com.myblog.blog.exception.WrongImageTypeException;
import com.myblog.blog.mapper.ProfileImageMapper;
import com.myblog.blog.model.ProfileImage;
import com.myblog.blog.model.User;
import com.myblog.blog.repository.ProfileImageRepository;
import com.myblog.blog.util.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileImageService {

    private final ProfileImageRepository profileImageRepository;
    private final UserService userService;
    private final ProfileImageMapper profileImageMapper;

    public String uploadImage(MultipartFile img, Principal connectedUser) throws IOException {

        if(!img.getContentType().equals(MediaType.IMAGE_PNG_VALUE) &&
                !img.getContentType().equals(MediaType.IMAGE_JPEG_VALUE))
            throw new WrongImageTypeException("Only PNG and JPEG format is accepted");

        if(img.getBytes().length > 10 * 1024 * 1000)
            throw new InvalidSizeException("Image too big! Maximum size: 10MB");

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        ProfileImage profileImage = ProfileImage.builder()
                        .imgName(img.getOriginalFilename())
                        .imgType(img.getContentType())
                        .imgData(ImageUtils.compressImage(img.getBytes()))
                        .user(user)
                        .build();

        user.setProfImg(profileImage);

        profileImageRepository.save(profileImage);
        userService.saveUser(user);


        return "Uploaded succesfully: " + img.getOriginalFilename();
    }

    public ProfileImageDto downloadImage(Integer id) {
        ProfileImage profileImage = profileImageRepository.findByUserId(id);
        profileImage.setImgData(ImageUtils.decompressImage(profileImage.getImgData()));

        return profileImageMapper.profileImageToProfileImageDto(profileImage);
    }

    public List<ProfileImageDto> downloadAll() {
        List<ProfileImageDto> profileImageList = profileImageRepository.findAll()
                .stream()
                .map(profileImageMapper::profileImageToProfileImageDto)
                .collect(Collectors.toList());

        profileImageList.forEach(
                img -> img.setImageData(ImageUtils.decompressImage(img.getImageData()))
        );

        return profileImageList;
    }

    public void delete(Integer id, Principal connectedUser) {
        if (!profileImageRepository.existsByUserId(id))
            throw new NotFoundException("User doesn't have profile image");

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        user.setProfImg(null);
        userService.saveUser(user);

        profileImageRepository.deleteByUserId(id);

    }

    public ProfileImageDto updateImage(MultipartFile img, Principal connectedUser) throws IOException {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        ProfileImage profileImage = profileImageRepository.findByUserId(user.getId());

        if(!img.getContentType().equals(MediaType.IMAGE_PNG_VALUE) &&
                !img.getContentType().equals(MediaType.IMAGE_JPEG_VALUE))
            throw new WrongImageTypeException("Only PNG and JPEG format is accepted");

        if(img.getBytes().length > 10 * 1024 * 1000)
            throw new InvalidSizeException("Image too big! Maximum size: 10MB");


        profileImage.setImgData(ImageUtils.compressImage(img.getBytes()));
        profileImage.setImgType(img.getContentType());
        profileImage.setImgName(img.getOriginalFilename());

        profileImageRepository.save(profileImage);

        return profileImageMapper.profileImageToProfileImageDto(profileImage);
    }



}
