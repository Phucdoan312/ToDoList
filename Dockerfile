# 1. "Nguyên liệu": Dùng một bản Java 21 "mỏng" (slim)
FROM openjdk:21-jdk-slim

# 2. "Đóng gói": Copy cái file .jar bạn vừa tạo
#    từ thư mục "target" vào bên trong "Gói hàng" (Image)
#    và đổi tên nó thành "app.jar"
COPY target/ToDoList-0.0.1-SNAPSHOT.jar app.jar

# 3. "Lệnh khởi động": Khi "Gói hàng" này chạy,
#    hãy dùng lệnh "java -jar /app.jar"
ENTRYPOINT ["java","-jar","/app.jar"]