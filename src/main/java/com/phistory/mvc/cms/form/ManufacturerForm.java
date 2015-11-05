package com.phistory.mvc.cms.form;

import java.sql.Blob;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Manufacturer form
 *
 * @author Gonzalo
 */
public class ManufacturerForm
{
    private Long id;
    @NotEmpty(message = "NotEmpty.manufacturerForm.name")
    private String name;
    @NotEmpty(message = "The field above must not be blank.")
    private String nationality;
    private Blob logo;
    private MultipartFile logoPictureFile;
    private String story;

    public ManufacturerForm() {
    }

    public ManufacturerForm(Long id, String name, String nationality, Blob logo, String story) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.logo = logo;
        this.story = story;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public MultipartFile getLogoPictureFile() {
        return logoPictureFile;
    }

    public void setLogoPictureFile(MultipartFile logoPictureFile) {
        this.logoPictureFile = logoPictureFile;
    }
}
