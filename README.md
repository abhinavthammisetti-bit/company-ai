# Company AI

A full-stack **Retrieval-Augmented Generation (RAG)** application for querying company documents using natural language.

Company AI allows users to upload company PDFs, index their content, and ask questions against the uploaded knowledge base. Relevant information is retrieved using semantic search and passed to an NVIDIA AI model to generate the final response.

## Overview

The application combines:

* **Spring Boot** for the backend and API layer
* **React + Vite** for the frontend
* **MySQL** for document and application data
* **NVIDIA API** for embeddings and LLM inference
* **Qdrant Cloud** for vector storage and semantic retrieval

The main goal is to provide **company-specific answers grounded in uploaded documents**, rather than relying only on the model's general knowledge.

## Architecture

```text
User
 │
 ▼
React + Vite
 │
 ▼
Spring Boot API
 │
 ├──────────────► MySQL
 │                 Documents / Metadata
 │
 ├──────────────► NVIDIA Embedding API
 │                 2048-dimensional vectors
 │
 ▼
Qdrant Cloud
Vector Search
 │
 ▼
Relevant Document Chunks
 │
 ▼
Prompt + Retrieved Context
 │
 ▼
NVIDIA LLM
 │
 ▼
AI Response
```

## RAG Pipeline

### 1. Document Upload

A company PDF is uploaded through the React interface.

### 2. Text Extraction

The Spring Boot backend extracts the text from the uploaded document.

### 3. Document Chunking

The extracted content is divided into smaller chunks that can be independently retrieved.

### 4. Embedding Generation

Each chunk is converted into a vector using NVIDIA's embedding model:

```text
nvidia/nemotron-3-embed-1b
```

The generated vectors contain **2048 dimensions**.

### 5. Vector Storage

Document vectors are stored in Qdrant Cloud along with metadata such as:

```text
company
text
```

### 6. Query Embedding

When a user asks a question, the question is converted into the same 2048-dimensional vector space.

### 7. Semantic Retrieval

Qdrant performs similarity search to identify the most relevant document chunks.

Retrieval is filtered by the selected company to prevent information from other company knowledge bases from being included.

### 8. Context-Aware Generation

The retrieved chunks are added to the prompt and sent to the NVIDIA LLM.

The model is instructed to answer using the supplied company context.

```text
Question
   ↓
Query Embedding
   ↓
Qdrant Semantic Search
   ↓
Relevant Chunks
   ↓
Retrieved Context
   ↓
NVIDIA LLM
   ↓
Response
```

## Key Features

* PDF document ingestion
* Company-specific knowledge bases
* Semantic vector search
* NVIDIA-powered embeddings
* NVIDIA LLM inference
* Qdrant Cloud vector database
* MySQL document storage
* Multi-turn conversation context
* Streaming AI responses
* Company-level retrieval filtering
* React-based chat interface

## Technology Stack

| Layer           | Technology                  |
| --------------- | --------------------------- |
| Frontend        | React, Vite, JavaScript     |
| Backend         | Java 17, Spring Boot 3.5.16 |
| API             | Spring WebFlux              |
| ORM             | Spring Data JPA             |
| Database        | MySQL 8                     |
| Embeddings      | NVIDIA Nemotron 3 Embed 1B  |
| LLM             | NVIDIA API                  |
| Vector Database | Qdrant Cloud                |
| Build           | Maven, npm                  |
| Version Control | Git, GitHub                 |

## Project Structure

```text
company-ai-fullstack/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/abhinav/company_ai/
│   │       │       ├── ai/
│   │       │       ├── application/
│   │       │       ├── config/
│   │       │       ├── controller/
│   │       │       ├── domain/
│   │       │       ├── dto/
│   │       │       ├── entity/
│   │       │       ├── repository/
│   │       │       ├── service/
│   │       │       └── vector/
│   │       └── resources/
│   └── pom.xml
│
├── src/
│   ├── components/
│   ├── pages/
│   ├── services/
│   ├── styles/
│   ├── App.jsx
│   └── main.jsx
│
├── public/
├── dist/
├── package.json
├── vite.config.js
└── README.md
```

## Running Locally

### Requirements

* Java 17+
* Node.js
* MySQL 8+
* Git
* NVIDIA API key
* Qdrant Cloud account and API key

### Environment Variables

The application expects the following environment variables:

```text
NVIDIA_API_KEY
QDRANT_API_KEY
```

API keys should never be committed to the repository.

### Start the Backend

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Backend:

```text
http://localhost:8080
```

### Start the Frontend

From the project root:

```powershell
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

### Production Build

Backend:

```powershell
cd backend
.\mvnw.cmd clean package -DskipTests
```

Frontend:

```powershell
npm run build
```

Preview the production frontend:

```powershell
npm run preview
```

## Example

A sample company document can contain:

```text
Company Name: TCS
Department: Artificial Intelligence
Project: Enterprise AI Assistant
Employees: 50000
```

Question:

```text
How many employees does TCS have?
```

Retrieved information:

```text
Employees: 50000
```

Response:

```text
50000
```

## Validation

The current implementation has been validated for:

* PDF upload and text extraction
* MySQL document persistence
* Document chunking
* NVIDIA embedding generation
* 2048-dimensional embeddings
* Qdrant vector insertion
* Semantic retrieval
* Company-based filtering
* Conversation context
* NVIDIA LLM generation
* Streaming responses
* Frontend production build
* Backend production build

## Future Improvements

* Authentication and role-based access
* Persistent conversation history
* Document management and deletion
* Source citations in responses
* Improved response formatting
* Retrieval evaluation and ranking improvements
* Support for additional document formats
* Cloud deployment
* Admin dashboard

## Author

**Abhinav Thammisetti**

B.Tech — Computer Science and Engineering (Data Science)

GitHub: [abhinavthammisetti-bit](https://github.com/abhinavthammisetti-bit)

---

Built as a full-stack RAG application using Java, Spring Boot, React, NVIDIA AI and Qdrant.
