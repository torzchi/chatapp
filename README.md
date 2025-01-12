# Documentatie Completa in DocumentatieChatapp.pdf
# Chat Application

Aplicatie de mesagerie in real-time implementata folosing Websockets-uri ca mod principal de comunicare si framework-ul SpringBoot impreuna cu toate facilitatile acestuia. 

## Noscow List

### Must
- Conversatii 1-1
- Group chats
- Discutie generala/publica

### Should
- Suport pentru trimitere poze/fisiere/documente
- Afisarea utilizatorilor conectati

### Could
- Sistem de reactionare la mesaje
- Apeluri video/audio

### Won't
- Mesaje vocale
- Editare mesaje dupa trimitere
- Avatare/customizare profile utilizatori

## Tech Stack
- Spring Boot
- Spring WebSocket 
- Spring Data JPA
- Spring Security
- MySQL Database
- Maven Build Tool

## Cerinte functionare
- Java 17+
- MySQL 8+
- Maven 3.8+
- Un mediu de rulare pentru server (Tomcat)

## Arhitectura Proiect

```plaintext
Client <-> WebSocket <-> Server <-> Database
```
