package ru.gb.androind1.lesson06;

import java.util.Calendar;
import java.util.Date;

public class Note {

    private final static String TYPE_NOTE = "Простой тип";

    private Date createDate;            // Дата и время создания заметки
    private String title;               // Заголовок заметки
    private String content;             // Содержимое заметки
    private String typeNote;            // Тип заметки
    private boolean marked;             // Флаг важности заметки

    public Note() {

    }

    public Note(Date createDate, String title, String content, String typeNote, boolean marked) {
        this.createDate = createDate;
        this.title = title;
        this.content = content;
        this.typeNote = typeNote;
        this.marked = marked;
    }

    public Note(Date createDate, String title, String content) {
        this.createDate = createDate;
        this.title = title;
        this.content = content;
        this.typeNote = TYPE_NOTE;
        this.marked = false;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeNote() {
        return typeNote;
    }

    public void setTypeNote(String typeNote) {
        this.typeNote = typeNote;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
