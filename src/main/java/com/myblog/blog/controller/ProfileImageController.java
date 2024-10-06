package com.myblog.blog.controller;

import com.myblog.blog.dto.ProfileImageDto;
import com.myblog.blog.exception.InvalidRoleException;
import com.myblog.blog.model.ProfileImage;
import com.myblog.blog.model.User;
import com.myblog.blog.service.ProfileImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/profile-images")
@AllArgsConstructor
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @Transactional
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("img") MultipartFile img, Principal connectedUser) throws IOException, InvalidRoleException {
        return ResponseEntity.ok().body(profileImageService.uploadImage(img, connectedUser));
    }

    @GetMapping("/download-image/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Integer id) {
        ProfileImageDto img = profileImageService.downloadImage(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(img.getImageType())).body(img.getImageData());
    }

    @GetMapping("/download-all")
    public ResponseEntity<?> downloadAll() {
        List<ProfileImageDto> profileImageList = profileImageService.downloadAll();
        return ResponseEntity.status(HttpStatus.OK).body(profileImageList);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Principal connectedUser) {
        profileImageService.delete(id, connectedUser);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

    @Transactional
    @PatchMapping("/update-image")
    public ResponseEntity<ProfileImageDto> updateImage(@RequestParam("img") MultipartFile img, Principal connectedUser) throws IOException {
        ProfileImageDto profileImageDto = profileImageService.updateImage(img, connectedUser);
        return ResponseEntity.status(HttpStatus.OK).body(profileImageDto);
    }


}
