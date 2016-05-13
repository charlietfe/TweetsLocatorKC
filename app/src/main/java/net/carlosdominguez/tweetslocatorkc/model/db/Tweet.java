package net.carlosdominguez.tweetslocatorkc.model.db;

import java.util.Date;


/**
 * Created by Carlos on 13/05/16.
 */
public class Tweet {
    private long id;
    private String username;
    private String message;

    public Tweet() {}

    public Tweet(String username, String message) {
        this.username = username;
        this.message = message;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    private Date publicationDate;


}
