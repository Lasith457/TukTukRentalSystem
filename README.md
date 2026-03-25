🚖 TukTuk Rental System

📖 Project Overview

The TukTuk Rental System is a Java-based desktop application developed to manage tuk-tuk rental operations efficiently.
The system allows users to handle customer records, manage rentals, process payments, and generate detailed reports.
This project was developed as part of coursework requirements for demonstrating enterprise application development concepts.

✨ Key Features

🔐 Secure Login System                         
👤 Customer Management (Add / Update / Delete / View)         
🛺 TukTuk Management  
📅 Rental Management  
💳 Payment Processing  
📊 Rental Report Generation (JasperReports)  
🖥️ User-friendly GUI using Java Swing  

🛠️ Technologies Used  

     			    
Java           			    
Swing				        
MySQL				       
JDBC				        
JasperReports			    
IntelliJ IDEA			    

🗄️ Database Details

Database Name:
tuktuk_rental

Tables Used:  
customers  
tuktuks  
rentals  
payments   
users

⚙️ System Requirements:

Java JDK 17 or higher   
MySQL Server installed  
MySQL Workbench (optional)
Windows OS (recommended)

🔧 Setup Instructions
1. Clone the Repository
   git clone https://github.com/Lasith457/TukTukRentalSystem
2. Configure Database  
   Open MySQL  
   Create database:  
   CREATE DATABASE tuktuk_rental;  
   Import tables (or run your SQL script)
3. Update Database Connection  
   Go to:  
   database/DBConnection.java  
   Update:  
   String url = "jdbc:mysql://localhost:3306/tuktuk_rental";  
   String user = "root";  
   String password = "your_password";   
4. Run the Application  
   Open project  
   Run MainMenu.java

   
    Default Login Credentials  
    Username: admin  
    Password: 1234    
5. 📊 Report Module  
   The system includes a Rental Report generated using JasperReports.
   Report Includes:  
   Rental ID  
   Customer Name  
   TukTuk Plate Number  
   Start Date  
   End Date  
   Total Amount


   📁 Project Structure  
   src/  
   ├── ui/           # User Interfaces (Forms)  
   ├── dao/          # Data Access Objects  
   ├── model/        # Entity Classes  
   ├── database/     # Database Connection  
   ├── report/       # JasperReports Files  
   └── service/      # Business Logic   

   📸 Screenshots
   (Add screenshots here if required)

   ⚠️ Important Notes  
   Ensure MySQL server is running before launching the application
   Make sure database credentials are correct
   Reports require proper database connection to function

   🚀 Future Improvements  
   Web-based version  
   User role management  
   Online booking system  


   👨‍💻 Author  
   Lasith Chamika  
   Undergraduate - Software Engineering

   📌 License
   This project is developed for educational purposes only.
