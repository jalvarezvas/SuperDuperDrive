package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getNotes(Integer userId);

    @Insert("INSERT INTO notes (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Update("UPDATE notes SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription}, userId = #{userId} WHERE noteId = #{noteId}")
    void update(Note note);

    @Delete("DELETE FROM notes WHERE noteId = #{noteId}")
    void delete(Integer noteId);
}
