# Run the project

## Step 1 : Install OpenJDK
OpenJDK version 17
Windows : https://download.java.net/java/GA/jdk17/0d483333a00540d886896bac774ff48b/35/GPL/openjdk-17_windows-x64_bin.zip  
Mac/AArch64 : https://download.java.net/java/GA/jdk17/0d483333a00540d886896bac774ff48b/35/GPL/openjdk-17_macos-aarch64_bin.tar.gz  
Mac/x64 : https://download.java.net/java/GA/jdk17/0d483333a00540d886896bac774ff48b/35/GPL/openjdk-17_macos-x64_bin.tar.gz  

## Step 2 : Add JAVA_HOME variable and set JAVA path variable -> https://www.youtube.com/watch?v=yGxCQisOL1A

## Step 3 : Install MySQL
SQL server download  
Windows : https://dev.mysql.com/downloads/file/?id=545364  
MacOS 15 ARM : https://dev.mysql.com/downloads/file/?id=545666   
MacOS 15 x86 : https://dev.mysql.com/downloads/file/?id=545667  

## Step 4 : Setup MySQL -> https://www.youtube.com/watch?v=hiS_mWZmmI0

## Step 5 : Open MySQL command line client (from windows search)

## Step 6 : Execute following command in MySQL
```sql
CREATE DATABASE test;
```
## Step 7 : Download the source code -> https://github.com/programmer-raghav-deo/CitySync-Backend/archive/refs/heads/main.zip

## Step 8 : Configure email id (used for sending otp), host (e.g. smtp.gmail.com), app key
update application.properties - host(e.g. smtp.gmail.com),email id, app key  
update Constants.java - ADMIN_EMAIL_ID (Put your email id) 

## Step 9 : Build the new project using maven and run

# Access the documentation

## Step 1 : Run the project (using above instructions)

## Step 2 : Go to link -> https://localhost:8080/swagger-ui/index.html

# Screenshots of final project (Frontend + Backend)



