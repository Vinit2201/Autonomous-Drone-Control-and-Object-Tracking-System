🛸 Autonomous Drone Control & Object Tracking System

A Java-based real-time drone simulation with GUI, autonomous movement, sensor monitoring & multithreading.


---

📌 Overview

This project simulates an autonomous drone capable of flying, tracking its position, monitoring battery usage, and visualizing live movement on a graphical interface.
It follows clean Object-Oriented Programming (OOP) principles and uses Java Swing for real-time UI updates.

The system includes components like:

📷 Camera Sensor

📡 GPS Sensor

🔥 Infrared Sensor


A separate multithreaded tracker continuously updates drone coordinates and battery levels on the GUI.


---

🛠 Tech Stack

Technology	Purpose

Java (OOP)	Core logic & object modeling
Java Swing	GUI dashboard
Multithreading	Real-time movement & tracking
Exception Handling	Safe drone operations
Collections Framework	Sensor management



---

🎯 Key Features

🛸 Drone System

Start/stop flight operations

Simulated random movement

Live coordinate updates

Battery usage tracking


🧵 Multithreaded Tracking

Independent thread for movement updates

Smooth position changes

Battery drain simulation


🖥 Interactive GUI

Displays drone status (Flying / Landed)

Shows real-time battery percentage

Visual drone rendering on canvas

Start, Land, and Exit controls


🧩 OOP Concepts Demonstrated

Abstraction

Inheritance

Encapsulation

Polymorphism

Threads & synchronization concepts



---

📁 Project Structure

Autonomous Drone Control System
│
├── DroneComponent.java
├── Sensor.java
├── Drone.java
├── DroneTrackerThread.java
├── GUI.java
└── AutonomousDroneControlSystem.java


---

📸 GUI Preview (Description)

Canvas area displays a moving blue drone icon.

Top bar shows:

🔋 Battery Percentage

📡 Drone Status


Bottom bar includes:

▶ Start Flight Button

⏹ Land Button

❌ Exit Button




---

🚀 How to Run the Project

1. Install Java JDK 8 or above

2. Compile the project

javac AutonomousDroneControlSystem.java

3. Run the project

java AutonomousDroneControlSystem

Or run directly via IntelliJ IDEA / VS Code / Eclipse.


---

⚙ How It Works (Internally)

✔ Drone Class

Maintains position, battery, and status (flying/landed)

Contains movement and battery reduction logic


✔ Sensor Class

Extends abstract DroneComponent

Each sensor can return status and be activated/deactivated


✔ Tracker Thread

Moves the drone randomly every second

Reduces battery by a fixed amount

Sends position updates to GUI


✔ GUI Class

Uses Swing for UI rendering

Repaints the drone on every coordinate update

Thread-safe updates using SwingUtilities.invokeLater()



---

⭐ Future Enhancements

🔍 Object detection using OpenCV

📍 GPS-based real-world mapping

🛰 Drone path planning using algorithms

📡 Live obstacle avoidance

🔊 Add audio alerts (battery low, collision, etc.)



---

👨‍💻 Author

Vinit Kumar
Java Developer | Tech Enthusiast | Drone Simulation Research


---

⭐ Support

If you like this project, please give it a ⭐ on GitHub — it motivates further development
