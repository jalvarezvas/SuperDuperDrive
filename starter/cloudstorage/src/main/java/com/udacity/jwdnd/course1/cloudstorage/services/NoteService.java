package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(Integer userID) {

        return noteMapper.getNotes(userID);
    }

    public Integer insert(Note note) {

        return noteMapper.insert(
                new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId())
        );
    }

    public void update(Note note) {
        noteMapper.update(note);
    }

    public void delete(Integer noteId) {
        noteMapper.delete(noteId);
    }
}
