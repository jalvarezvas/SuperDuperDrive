package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO users (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insert(User user);

    @Update("UPDATE users SET username = #{username}, salt = #{salt}, password = #{password}, firstname = #{firstname}, lastname = #{lastname} WHERE userId = #{userId}")
    void update(User user);

    @Delete("DELETE FROM users WHERE userId = #{userId}")
    void delete(Integer userId);
}
