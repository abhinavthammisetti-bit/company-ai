package com.abhinav.company_ai.repository;

import com.abhinav.company_ai.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByCompanyNameAndFileName(
            String companyName,
            String fileName
    );
}