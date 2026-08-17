package com.abhinav.company_ai.repository;

import com.abhinav.company_ai.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {

    List<DocumentChunk> findByDocumentCompanyName(String companyName);

    List<DocumentChunk> findAll();

}