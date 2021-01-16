package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isFilenameAvailable(String fileName, Integer userId) {

        return ((fileMapper.getFileByName(fileName, userId) == null) ? true : false);

    }

    public File getFile(Integer fileId) {

        return fileMapper.getFile(fileId);
    }

    public List<File> getFiles(Integer userid) {

        return fileMapper.getFiles(userid);
    }

    public Integer insert(MultipartFile multipartFile, Integer userId) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        return fileMapper.insert(
                new File(null, fileName, multipartFile.getContentType(), "" + multipartFile.getSize(), userId, multipartFile.getBytes())
        );
    }

    public void delete(Integer fileId) {

        fileMapper.delete(fileId);
    }
}
