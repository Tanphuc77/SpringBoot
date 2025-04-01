# Stage 1: Build ứng dụng với Maven
FROM maven:3.9.8-amazoncorretto-17 AS build

# Tạo thư mục làm việc trong container
WORKDIR /app

# Copy toàn bộ source code vào container
COPY pom.xml .
COPY src ./src

# Biên dịch dự án thành file JAR
RUN mvn clean package -DskipTests

# Stage 2: Tạo Docker Image chứa ứng dụng
FROM amazoncorretto:17

# Định nghĩa thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ giai đoạn build sang container
COPY --from=build /app/target/SpringBootDatabase-0.0.1-SNAPSHOT.jar app.jar

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
