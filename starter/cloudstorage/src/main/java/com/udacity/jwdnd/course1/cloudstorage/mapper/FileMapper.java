package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM files WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Select("SELECT * FROM files WHERE filename = #{fileName} and userid = #{userId}")
    File getFileByName(String fileName, Integer userId);

    @Select("SELECT * FROM files WHERE userid = #{userId}")
    List<File> getFiles(Integer userId);

    @Insert("INSERT INTO files (filename, contentType, fileSize, userId, filedata) VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Update("UPDATE files SET filename = #{filename}, contentType = #{contentType}, fileSize = #{fileSize}, userId = #{userId} WHERE fileId = #{fileId}")
    void update(File file);

    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    void delete(Integer fileId);
}
