# StyleShuffle Backend
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring-Boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=Hibernate&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Springdoc OpenAPI](https://img.shields.io/badge/Springdoc_OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://springdoc.org/)
[![MapStruct](https://img.shields.io/badge/MapStruct-007ACC?style=for-the-badge&logo=java&logoColor=white)](https://mapstruct.org/)
[![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white)](https://junit.org/junit5/)
[![Testcontainers](https://img.shields.io/badge/Testcontainers-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.testcontainers.org/)
[![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/)


---

## Описание проекта

**StyleShuffle Backend** – это серверная часть кроссплатформенного мобильного приложения для управления виртуальным гардеробом и создания стильных образов. Приложение дает возможность пользователям создавать образы, используя уже сфотографированные элементы их гардероба.

Пользователи могут делиться своими образами, которые отображаются в ленте новостей. Кроме того, приложение предоставляет возможность примерять одежду из различных интернет-магазинов и комбинировать ее с элементами личного гардероба.

---

## Архитектура и технологии

Backend реализован на базе **Spring Boot** и следует принципам архитектуры MVC, что позволяет эффективно разделять бизнес-логику, обработку данных и представление API. Проект использует PostgreSQL для хранения данных, а Docker Compose обеспечивает лёгкое развертывание и масштабирование.

---

## Быстрый старт

### Запуск через Docker Compose
1. **Установить и запустить Docker**

2. **Клонируйте репозиторий:**
   ```bash
   git clone git@github.com:AlinaMoroz/StyleShuffle.git
   docker compose up
  
