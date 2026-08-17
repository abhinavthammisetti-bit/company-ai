package com.abhinav.company_ai.entity;

import jakarta.persistence.*;

@Entity
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String text;

    // Will store the embedding vector later
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String embedding;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    public DocumentChunk() {
    }

    public DocumentChunk(String text, Document document) {
        this.text = text;
        this.embedding = null;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmbedding() {
        return embedding;
    }

    public void setEmbedding(String embedding) {
        this.embedding = embedding;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}