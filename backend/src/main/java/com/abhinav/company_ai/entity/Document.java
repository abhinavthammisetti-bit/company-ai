package com.abhinav.company_ai.entity;
import jakarta.persistence.*;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String fileName;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    public Document() {
    }

    public Document(String companyName, String fileName, String content) {
        this.companyName = companyName;
        this.fileName = fileName;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}