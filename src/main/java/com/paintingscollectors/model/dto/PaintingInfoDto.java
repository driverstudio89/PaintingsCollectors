package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.User;

import javax.validation.constraints.NotNull;

public class PaintingInfoDto {

    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String author;

    @NotNull
    private String imageUrl;

    @NotNull
    private Style style;

    @NotNull
    private User owner;

    @NotNull
    private Integer votes;

    public PaintingInfoDto(Painting painting) {
        this.id = painting.getId();
        this.name = painting.getName();
        this.author = painting.getAuthor();
        this.imageUrl = painting.getImageUrl();
        this.style = painting.getStyle();
        this.owner = painting.getOwner();
        this.votes = painting.getVotes();
    }

    public @NotNull Integer getVotes() {
        return votes;
    }

    public void setVotes(@NotNull Integer votes) {
        this.votes = votes;
    }

    @NotNull
    public long getId() {
        return id;
    }

    public void setId(@NotNull long id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull String author) {
        this.author = author;
    }

    public @NotNull String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotNull Style getStyle() {
        return style;
    }

    public void setStyle(@NotNull Style style) {
        this.style = style;
    }

    public @NotNull User getOwner() {
        return owner;
    }

    public void setOwner(@NotNull User owner) {
        this.owner = owner;
    }
}
