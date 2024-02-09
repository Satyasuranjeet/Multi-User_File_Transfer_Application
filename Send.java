import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Send extends JFrame {
    private JTextField serverAddressField;
    private JTextField serverPortField;
    private JTextArea logArea;
    private JButton sendFileButton;
    private JButton pauseButton;
    private JButton resumeButton;
    private JProgressBar progressBar;

    private volatile boolean isPaused = false;

    public Send() {
        setTitle("File Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        serverAddressField = new JTextField("localhost", 15);
        serverPortField = new JTextField("12345", 5);
        sendFileButton = new JButton("Select and Send File");
        logArea = new JTextArea();
        logArea.setEditable(false);
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");
        progressBar = new JProgressBar();

        inputPanel.add(new JLabel("Server Address:"));
        inputPanel.add(serverAddressField);
        inputPanel.add(new JLabel("Server Port:"));
        inputPanel.add(serverPortField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(sendFileButton);
        inputPanel.add(pauseButton);
        inputPanel.add(resumeButton);

        JScrollPane scrollPane = new JScrollPane(logArea);
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(progressBar, BorderLayout.PAGE_END);

        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String serverAddress = serverAddressField.getText();
                int serverPort = Integer.parseInt(serverPortField.getText());

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(Send.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(() -> sendFile(serverAddress, serverPort, filePath));
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = true;
                logArea.append("File transfer paused.\n");
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(true);
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = false;
                logArea.append("File transfer resumed.\n");
                pauseButton.setEnabled(true);
                resumeButton.setEnabled(false);
            }
        });
    }

    private void sendFile(String serverAddress, int serverPort, String filePath) {
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            logArea.append("Connected to server: " + serverAddress + ":" + serverPort + "\n");

            // Create output stream to send file data to the server
            OutputStream outputStream = socket.getOutputStream();

            // Create input stream to read file data
            FileInputStream fileInputStream = new FileInputStream(filePath);

            // Buffer for reading file data
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;
            long fileSize = new File(filePath).length();

            // Read file data and write to the output stream
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                if (isPaused) {
                    while (isPaused) {
                        Thread.sleep(1000); // Sleep for 1 second
                    }
                }

                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                // Update progress bar
                int progress = (int) ((totalBytesRead * 100) / fileSize);
                progressBar.setValue(progress);
            }

            logArea.append("File sent successfully: " + filePath + "\n");

            // Close streams and socket
            fileInputStream.close();
            outputStream.close();
            socket.close();

            // Reset progress bar
            progressBar.setValue(0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logArea.append("Error occurred: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Send().setVisible(true);
        });
    }
}
