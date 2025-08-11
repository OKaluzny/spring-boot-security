# Spring Boot Security Application

## Як запустити проект

### Попередні вимоги

1. **Java 17** або вище
2. **Maven 3.6+** (або використовуйте вбудований Maven Wrapper)
3. **Git** (для клонування репозиторію)

### Покрокова інструкція запуску

#### 1. Клонування репозиторію
```bash
git clone <repository-url>
cd spring-boot-security
```

#### 2. Збірка проекту
```bash
# Використовуючи Maven Wrapper (рекомендується)
./mvnw clean compile

# Або використовуючи встановлений Maven
mvn clean compile
```

#### 3. Запуск додатку

**Спосіб 1: Використовуючи Maven Wrapper (рекомендується)**
```bash
./mvnw spring-boot:run
```

**Спосіб 2: Використовуючи встановлений Maven**
```bash
mvn spring-boot:run
```

**Спосіб 3: Запуск JAR файлу**
```bash
# Спочатку створіть JAR
./mvnw clean package

# Потім запустіть
java -jar target/spring-boot-security-0.0.1-SNAPSHOT.jar
```

#### 4. Доступ до додатку

Після успішного запуску додаток буде доступний за адресою:

- **Головна сторінка**: http://localhost:8080
- **Сторінка входу**: http://localhost:8080/login
- **Сторінка реєстрації**: http://localhost:8080/register
- **H2 Database Console**: http://localhost:8080/h2-console

### Конфігурація бази даних

Додаток використовує вбудовану базу даних H2 в пам'яті:

- **URL**: `jdbc:h2:mem:demo_db`
- **Username**: `sa`
- **Password**: (порожній)
- **Driver**: `org.h2.Driver`

### Функціональність

- **Аутентифікація та авторизація**
- **Реєстрація користувачів**
- **Управління ролями**
- **Веб-інтерфейс з Thymeleaf**
- **Консоль H2 для розробки**

### Зупинка додатку

Для зупинки додатку натисніть `Ctrl + C` в терміналі.

### Усунення неполадок

1. **Порт 8080 зайнятий**:
   ```bash
   # Запустіть на іншому порту
   ./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
   ```

2. **Проблеми з версією Java**:
   ```bash
   # Перевірте версію Java
   java -version
   ```

3. **Проблеми з Maven**:
   ```bash
   # Очистіть Maven кеш
   ./mvnw clean
   ```

---

## Довідкова документація

Для додаткової інформації, будь ласка, ознайомтеся з наступними розділами:

* [Офіційна документація Apache Maven](https://maven.apache.org/guides/index.html)
* [Довідник Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/3.5.4/maven-plugin)
* [Створення OCI образу](https://docs.spring.io/spring-boot/3.5.4/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.4/reference/web/servlet.html)
* [Spring Security](https://docs.spring.io/spring-security/reference/)

## Посібники

Наступні посібники ілюструють, як використовувати деякі функції на практиці:

* [Створення RESTful веб-сервісу](https://spring.io/guides/gs/rest-service/)
* [Обслуговування веб-контенту з Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Створення REST сервісів з Spring](https://spring.io/guides/tutorials/rest/)
* [Захист веб-додатку](https://spring.io/guides/gs/securing-web/)

## Перевизначення Maven Parent

Через дизайн Maven, елементи успадковуються від батьківського POM до проектного POM.
Хоча більшість успадкування є нормальним, воно також успадковує небажані елементи, такі як `<license>` та `<developers>` від
батьківського.
Щоб запобігти цьому, проектний POM містить порожні перевизначення для цих елементів.
Якщо ви вручну переключитеся на інший батьківський і дійсно хочете успадкування, вам потрібно видалити ці перевизначення.
