package com.abhinav.company_ai.service;

import com.abhinav.company_ai.entity.Document;
import com.abhinav.company_ai.entity.DocumentChunk;
import com.abhinav.company_ai.repository.DocumentChunkRepository;
import com.abhinav.company_ai.vector.EmbeddingService;
import com.abhinav.company_ai.vector.QdrantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChunkService {

    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    public ChunkService(
            DocumentChunkRepository chunkRepository,
            EmbeddingService embeddingService,
            QdrantService qdrantService
    ) {
        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
        this.qdrantService = qdrantService;
    }

    public void createChunks(Document document) {

    String content = document.getContent();

    String[] lines = content.split("\\r?\\n");

    String currentHeading = "";
    StringBuilder currentContent = new StringBuilder();

    for (String line : lines) {

        line = line.trim();

        if (line.isEmpty()) {
            continue;
        }

        boolean isHeading = line.matches(
                "(?i)(Company Name|Industry|Department|Departments|Employees|Products|Project|Projects|Leave Policy|Insurance|Benefits).*:"
        );

        if (isHeading) {

            if (!currentHeading.isEmpty()) {

                saveChunk(
                        currentHeading + "\n" + currentContent.toString().trim(),
                        document
                );
            }

            currentHeading = line;
            currentContent.setLength(0);

        } else {

            if (currentHeading.isEmpty()) {

                saveChunk(line, document);

            } else {

                currentContent.append(line).append("\n");
            }
        }
    }

    if (!currentHeading.isEmpty()) {

        saveChunk(
                currentHeading + "\n" + currentContent.toString().trim(),
                document
        );
    }

    System.out.println("Chunks created successfully.");
}

    private void saveChunk(String text, Document document) {

        DocumentChunk chunk = new DocumentChunk(text, document);

        chunk = chunkRepository.save(chunk);

        float[] embedding =
                embeddingService.generatePassageEmbedding(chunk.getText());

        qdrantService.storeVector(
                chunk.getId().toString(),
                embedding,
                document.getCompanyName(),
                chunk.getText()
        );
    }

    public String getRelevantChunks(String question) {

        List<DocumentChunk> chunks = chunkRepository.findAll();

        StringBuilder context = new StringBuilder();

        for (DocumentChunk chunk : chunks) {

            context.append(chunk.getText()).append("\n\n");
        }

        return context.toString();
    }
}