# Используем официальное изображение OpenJDK как базовое изображение
FROM openjdk:17-jdk-alpine

# Задаём рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный JAR-файл в контейнер
COPY build/libs/goyoga-0.0.1-SNAPSHOT.jar /app/goyoga.jar

# Задаём порт, который будет использовать приложение
EXPOSE 8080

# Определяем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/goyoga.jar"]
