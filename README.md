//Biên dịch và đóng gói ứng dụng Spring Boot mà không chạy test.
mvn package -DskipTests

//Kéo (pull) image MySQL 8.0.41 dựa trên Debian.
docker pull mysql:8.0.41-debian

// Xây dựng image Docker cho ứng dụng Spring Boot.
docker build -t springboot:0.0.1 .

// Tạo một mạng Docker riêng tên springboot để các container có thể giao tiếp với nhau.
docker network create springboot

// Chạy Redis trên mạng springboot, yêu cầu mật khẩu root
docker run -d --name redis -p 6378:6379 --restart unless-stopped redis --requirepass root

// Chạy MySQL trong mạng springboot với mật khẩu root.
docker run --network springboot --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=04122003 -d mysql:8.0.41-debian

// Chạy ứng dụng Spring Boot và kết nối với MySQL.
docker run --name springbootdatabase --network springboot -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/student springboot:0.0.1

// Chạy Prometheus với file cấu hình từ local
docker run -d --name prometheus -p 9090:9090 -v "F:\SpringBootDatabase\SpringBootDatabase\src\main\resources\prometheus.yml:/etc/prometheus/prometheus.yml" prom/prometheus

// Chạy Grafana để hiển thị dashboard
docker run -d --name=grafana -p 3000:3000 grafana/grafana
