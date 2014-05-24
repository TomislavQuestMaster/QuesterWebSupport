package net.thequester.websupport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Tomo.
 */
@Entity
@Table(name="reviews")
public class Review {

    @Id
    @GeneratedValue
    private long id;

    private long questId;
    private long userId;

    private String text;
    private Integer likes;

    public Review() {
    }

    public Review(long questId, long userId, String text, Integer likes) {
        this.questId = questId;
        this.userId = userId;
        this.text = text;
        this.likes = likes;
    }

    public long getQuestId() {
        return questId;
    }

    public void setQuestId(long questId) {
        this.questId = questId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
