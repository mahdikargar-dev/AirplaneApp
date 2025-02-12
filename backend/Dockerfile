# استفاده از ایمیج رسمی جاوا 17
FROM eclipse-temurin:23-jdk

# تنظیم دایرکتوری کاری داخل کانتینر
WORKDIR /app

# کپی کردن فایل JAR ساخته شده در کانتینر
COPY target/*.jar app.jar

# تنظیم پورت اجرا (باید با پورت تعریف شده در application.properties یکسان باشد)
EXPOSE 8080

# فرمان اجرا برای راه‌اندازی Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
