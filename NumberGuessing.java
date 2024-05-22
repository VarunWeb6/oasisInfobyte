package com.example.oasisinfobyte;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuessTheNumber extends JFrame implements ActionListener {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    private static final int NUM_ATTEMPTS = 5;
    private Random rand;
    private int numToGuess;
    private int numAttempts;
    private JTextField inputField;
    private JLabel messageLabel;
    private JButton submitButton;
    private JLabel attemptLabel;
    private JLabel scoreLabel;
    private int score;
    private int round;

    public GuessTheNumber() {
        // Initialize components
        rand = new Random();
        numToGuess = -1;
        numAttempts = 0;
        inputField = new JTextField(3);
        messageLabel = new JLabel("Enter your guess:");
        submitButton = new JButton("Submit");
        attemptLabel = new JLabel("Attempts left: ");
        scoreLabel = new JLabel("Score: ");

        // Set up layout
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(messageLabel, constraints);
        constraints.gridx = 1;
        add(inputField, constraints);
        constraints.gridx = 2;
        add(submitButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        add(attemptLabel, constraints);
        constraints.gridy = 2;
        add(scoreLabel, constraints);

        // Register action listener
        submitButton.addActionListener(this);

        // Initialize variables for next round
        startNewRound();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = inputField.getText().trim();
        try {
            int guess = Integer.parseInt(text);
            if (guess < numToGuess) {
                messageLabel.setText("Too low!");
            } else if (guess > numToGuess) {
                messageLabel.setText("Too high!");
            } else {
                messageLabel.setText("Congratulations! You guessed correctly.");
                score += Math.max((NUM_ATTEMPTS - numAttempts), 0) * 10;
                round++;
                startNewRound();
            }
            numAttempts--;
            attemptLabel.setText("Attempts left: " + numAttempts);
            inputField.requestFocus();
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid input. Please enter a valid number.");
            inputField.selectAll();
        }
    }

    private void startNewRound() {
        numToGuess = rand.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        numAttempts = NUM_ATTEMPTS;
        inputField.setText("");
        messageLabel.setText("Enter your guess:");
        attemptLabel.setText("Attempts left: " + numAttempts);
        scoreLabel.setText("Score: " + score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GuessTheNumber();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
