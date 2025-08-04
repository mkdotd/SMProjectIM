# 📦 SMProjectIM - Software Maintenance Inventory Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78909C?style=for-the-badge&logo=mockito&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)

</div>

## 🎯 Project Overview

**SMProjectIM** is a **Inventory Management System** developed as a software maintenance project for academic coursework. 

## 🛠️ Technologies Used

### Core Technologies
- **Java 11+** - Primary programming language
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework for isolated testing
- **Java Crypto API** - AES encryption for secure authentication

### Development Tools
- **Python** - Auxiliary scripting and AI integration
- **Jupyter Notebooks** - Code generation and experimentation
- **Maven/Gradle** - Build automation (configured)

### AI-Powered Development
- **Hugging Face Integration** - Code generation assistance
- **API-based Code Review** - Automated testing suggestions

## 🚦 Getting Started

### Prerequisites
- Java 11 or higher
- JUnit 5 (for running tests)
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/mkdotd/SMProjectIM.git
   cd SMProjectIM/Inventory
   ```

2. **Compile the project(powershell)**
   ```bash
   javac -d out -cp "src/main/java" (Get-ChildItem src/main/java/com/inventory/*.java, src/main/java/UtilityFunctions/*.java)
   ```

3. **Run the application**
   ```bash
   java -cp out com.inventory.Main
   ```

4. **Default Login Credentials**
   - **Username:** `madhan`
   - **Password:** `1234`

## 📁 Data Management

### File Structure
```
inventory.txt Format:
ID,Name,Quantity,Price
1,Test Product,10,15.0
2,Sample Item,50,45.0
```
