# Real-Time Event Ticketing System CLI

## 📖 Project Description
A command-line interface (CLI) application designed to simulate a real-time ticketing environment using advanced multi-threading and the producer-consumer pattern. This system focuses on managing concurrent ticket releases and purchases while ensuring data integrity in a multi-threaded environment.

## ✨ Features
- **Concurrent Ticket Management**: Handles multiple vendors (producers) and customers (consumers) simultaneously.
- **Thread-Safe Operations**: Ensures proper synchronization to prevent race conditions and deadlocks.
- **Customizable Configuration**: Allows users to set parameters like ticket release rates and maximum capacity.
- **Real-Time Logging**: Displays system activities, such as ticket additions and purchases.
- **JSON-Based Configuration**: Uses Google Gson libraries for configuration management.

## 🛠️ Technologies Used
- **Java**: The primary programming language.
- **Google Gson**: For handling JSON-based configuration.
- **Multi-threading**: Utilized for concurrent ticket management.
- **Producer-Consumer Pattern**: To efficiently manage the flow of tickets between vendors and customers.

## 🚀 Getting Started
### Prerequisites
- Java Development Kit (JDK) 11 or higher.
- An IDE like IntelliJ IDEA, Eclipse, or a text editor with Java support.


### Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/LakinduDeSilva/Real-Time_Event_Ticketing_System_CLI.git
   
### Adding Google Gson Library in IntelliJ IDEA
To include the **Google Gson** library in your project:

1. Make sure the `gson-2.11.0.jar` file is located in the `libs` folder of your project.
2. Open **Project Structure** in IntelliJ (**File** → **Project Structure**).
3. In the **Modules** section:
    - Go to the **Dependencies** tab.
    - Click on **+** and select **JARs or directories**.
    - Choose the `gson-2.11.0.jar` file from the `libs` folder.
4. Apply the changes and rebuild your project.

