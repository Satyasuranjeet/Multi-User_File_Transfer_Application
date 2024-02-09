import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receive extends JFrame {
    private JTextArea logArea;
    private JButton startButton;
    private JTextField portField;

    public Receive() {
        setTitle("File Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start Server");
        portField = new JTextField("12345", 5);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = Integer.parseInt(portField.getText());
                startServer(port);
            }
        });

        controlPanel.add(new JLabel("Port: "));
        controlPanel.add(portField);
        controlPanel.add(startButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void startServer(int port) {
        logArea.append("Server is listening on port " + port + "\n");

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    logArea.append("Client connected: " + clientSocket.getInetAddress() + "\n");

                    // Handle file transfer
                    receiveFile(clientSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void receiveFile(Socket clientSocket) {
        try {
            // Create input stream to read file data from the client
            InputStream inputStream = clientSocket.getInputStream();

            // Use JFileChooser to specify the location to save the received file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save File");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);

                // Buffer for reading file data
                byte[] buffer = new byte[1024];
                int bytesRead;

                // Read file data from the input stream and write to the file
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                logArea.append("File received successfully: " + filePath + "\n");

                // Close streams and socket
                fileOutputStream.close();
                inputStream.close();
                clientSocket.close();
            } else {
                logArea.append("File transfer canceled by the user.\n");
                // Close the socket if the user cancels the file transfer
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Receive().setVisible(true);
        });
    }
}
