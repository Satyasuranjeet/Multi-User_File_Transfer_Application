# Multi-User File Transfer Application

## Overview
This Java project is a multi-user file transfer application that simulates an FTP-like server. It is designed to handle multiple requests simultaneously, providing users with the ability to pause and resume downloads. The project focuses on developing a graphical user interface (GUI) for user interactions, utilizing Java threads for concurrency, and implementing socket programming for efficient communication between the client and server.

## Learning Outcomes
By working on this project, you will gain hands-on experience and proficiency in the following areas:

- **GUI Development:** You will learn how to create a user-friendly graphical interface that allows users to interact with the file transfer system seamlessly.

- **Java Threads:** Understanding and implementing Java threads is a crucial aspect of this project. It involves managing multiple tasks concurrently to enhance the application's performance and responsiveness.

- **Socket Programming:** The project involves socket programming to establish communication between the client and server, facilitating the transfer of files and commands.

## Features

### 1. File Sharing
- Creation of an FTP-like server that allows users to upload and download files securely.
- Implementation of protocols to ensure the integrity and security of file transfers.

### 2. Concurrency Control
- Effective use of synchronization mechanisms to prevent conflicts when multiple users attempt to access or modify files simultaneously.

### 3. User Interface
- Development of a simple and intuitive GUI for users to interact with the server.
- Features include options for uploading, downloading, pausing, and resuming file transfers.

## Usage
1. **Compile the Code:** Use a Java compiler to compile both the client and server files.
   ```bash
   javac Send.java
   javac Receive.java
   ```

2. **Run the Server:** Start the server application.
   ```bash
   java Server
   ```

3. **Run the Client:** Launch the client application, which will open the GUI for user interactions.
   ```bash
   java Client
   ```

4. **Interact with the GUI:** Use the GUI to perform file upload, download, pause, and resume operations.

## Dependencies
- This project relies on standard Java libraries for GUI development, threading, and socket programming.

## Contributing
Contributions are welcome! Feel free to fork the repository, create branches, and submit pull requests to enhance the functionality or fix any issues.

 <header style="background-color: #333; padding: 20px; text-align: center;">
    <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 10px; max-width: 400px; margin: 0 auto;">
      <img style="width: 100%; height: 100%; object-fit: cover; border-radius: 8px;" src="Assets\1.png" alt="Image 1">
      <img style="width: 100%; height: 100%; object-fit: cover; border-radius: 8px;" src="Assets\2.png" alt="Image 2">
      <img style="width: 100%; height: 100%; object-fit: cover; border-radius: 8px;" src="Assets\3.png" alt="Image 3">
      <img style="width: 100%; height: 100%; object-fit: cover; border-radius: 8px;" src="Assets\4.png" alt="Image 4">
    </div>
  </header>

