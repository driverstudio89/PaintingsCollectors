package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.enums.StyleName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddPaintingDto {

    @NotNull
    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!")
    private String name;

    @NotNull
    @Size(min = 5, max = 30, message = "Author length must be between 5 and 40 characters!")
    private String author;

    @NotBlank(message = "Please enter valid image URL!")
    @Size(max = 150, message = "Please enter valid image URL!")
    private String imageUrl;

    @NotNull(message = "You must select a style!")
    private StyleName styleName;

    public @NotNull @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!") String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!") String name) {
        this.name = name;
    }

    public @NotNull @Size(min = 5, max = 30, message = "Author length must be between 5 and 40 characters!") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull @Size(min = 5, max = 30, message = "Author length must be between 5 and 40 characters!") String author) {
        this.author = author;
    }

    public @NotNull(message = "Please enter valid image URL!") @Size(max = 150, message = "Please enter valid image URL!") String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotNull(message = "Please enter valid image URL!") @Size(max = 150, message = "Please enter valid image URL!") String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotNull(message = "You must select a style!") StyleName getStyleName() {
        return styleName;
    }

    public void setStyleName(@NotNull(message = "You must select a style!") StyleName styleName) {
        this.styleName = styleName;
    }
}
