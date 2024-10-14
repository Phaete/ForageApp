# Forage Tracker - Share your Forage!

Welcome to Forage Tracker! This application allows users to track and share their forage finds, such as fruit trees, mushrooms, berry bushes, and more, on an interactive map using geo-location.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Docker Setup](#docker-setup)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features
- 📍 **Geo-location Tracking**: Easily log the locations of your finds on an interactive map.
- 🍓 **Forage Categories**: Track different types of forages like fruit trees, mushrooms, berry bushes, and others.
- 🌍 **Share Your Finds**: Share your forage locations with the community.
- 🔍 **Search Functionality**: Find forage locations shared by others.
- 🔄 **Circular Routes**: Create and visualize circular routes between selected forage spots.
- 🗺️ **Optimal Routes**: Select a start- and endpoint, and the app will calculate the best route through chosen forage items to get the best harvest possible.


## Getting Started
These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
- **Java 21** or higher
- **Node.js** and **npm**
- **Spring Boot**
- **React**
- **Docker**

### Installation
1. **Clone the repository**:
    ```sh
    git clone https://github.com/Phaete/ForageApp.git
    cd ForageApp
    ```

3. **Backend Setup**:
   
    - Navigate to the `backend` directory:
        ```sh
        cd backend
        ```
   
   - Build the Spring Boot application:
        ```sh
        ./mvnw clean install
        ```
   
   - Run the application:
        ```sh
        ./mvnw spring-boot:run
        ```

4. **Frontend Setup**:
   
   - Navigate to the `frontend` directory:
        ```sh
        cd ../frontend
        ```
   
   - Install dependencies:
        ```sh
        npm install
        ```
   
   - Start the React application:
        ```sh
        npm start
        ```

### Docker Setup
To run the application using Docker, follow these steps:

1. **Create Dockerfile for Backend**: In the `backend` directory, create a `Dockerfile`:
   
    ```dockerfile
    # Backend Dockerfile
    FROM --platform=linux/amd64 openjdk:21
    EXPOSE 8080
    COPY backend/target/ForageApp.jar app.jar
    ENTRYPOINT ["java", "-jar", "/app.jar"]
    ```
    
2. **Next Steps**
   
TODO

### Usage
Once the app is running, open your browser and navigate to `http://localhost:5173` to start using Forage Tracker.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact
If you have any questions or suggestions, feel free to reach out!

- **Konstantin Wenig** - PH:[your-email@example.com](mailto:your-email@example.com)
- **GitHub** - [Phaete](https://github.com/Phaete)

Happy foraging! 🍄🌲🍎
