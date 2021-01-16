package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM Credentials WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Insert("INSERT INTO credentials (url, username, key, password, userId) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userId = #{userId} WHERE credentialId = #{credentialId}")
    void update(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialId = #{credentialId}")
    void delete(Integer credentialId);
}
