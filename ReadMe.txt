📺 Netflix Clone

A Netflix Clone built using Java with a focus on UI, API integration, and media streaming features.

🚀 Features

🎥 User Authentication (Login/Signup)

🔍 Browse Movies & TV Shows

📌 Categories & Genres Filtering

🎞 Watch Trailers

📺 Video Streaming Support

❤️ Add to Watchlist

🔄 Continue Watching

🌙 Dark Mode

🛠️ Tech Stack

Frontend: Java Swing / JavaFX (for UI)

Backend: Java (Spring Boot / Servlets)

Database: MySQL / Firebase

Video Streaming: ExoPlayer / VLCJ

API: TMDB API (for fetching movie data)

📂 Project Structure

Netflix-Clone/
│── src/
│   ├── ui/ (User Interface components)
│   ├── auth/ (Authentication handlers)
│   ├── models/ (Data models)
│   ├── services/ (Business logic)
│   ├── database/ (Database handlers)
│   ├── video/ (Streaming integration)
│── assets/ (Images, icons, etc.)
│── README.md
│── .gitignore
│── pom.xml (Maven dependencies, if using Spring Boot)

⚙️ Installation & Setup

Clone the repository

git clone https://github.com/your-username/netflix-clone.git
cd netflix-clone

Setup Database

Create a MySQL Database named netflix_clone

Import database.sql file

Update db-config.properties with your credentials

Install Dependencies (If using Maven)

mvn install

Run the Project

java -jar NetflixClone.jar

or (if using Spring Boot)

mvn spring-boot:run

🎬 API Integration

This project uses The Movie Database (TMDB) API to fetch movie details.

🔑 Get Your API Key

Sign up at TMDB

Generate an API Key

Add it to config.properties:

TMDB_API_KEY=your_api_key_here

📸 Screenshots




🤝 Contribution

Feel free to contribute! Fork the repository and submit a Pull Request.

📜 License

This project is licensed under the MIT License.

💡 A Netflix-inspired project for learning Java development! 🎬🚀

