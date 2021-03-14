package com.mirkiewicz.traveler;

public class Note {

    private int idNote;
    private String noteContent;


    public Note() {
    }

    public Note(int idNote, String noteContent) {
        this.idNote = idNote;
        if(noteContent.length() > 500)
            noteContent = noteContent.substring(0,500);
        this.noteContent = noteContent;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
