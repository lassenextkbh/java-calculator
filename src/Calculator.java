import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    // Declare the width and height of the window.
    int windowWidth = 360;
    int windowHeight = 540;

    // Declare the colors used in the project
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    // Declare the values for all the buttons
    String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "=",
    };
    String[] rightSymbols = { "÷", "×", "-", "+", "=" };
    String[] topSymbols = { "AC", "+/-", "%" };

    // Declare window and components
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    Calculator() {
        // General window settings
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close when the window "close" button is pressed
        frame.setLayout(new BorderLayout()); // Set up layout for the window

        // Styling for displayLabel
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white); // Text color: white
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT); // Make the text align to the right
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        // Styling for displayPanel
        displayPanel.setLayout(new BorderLayout()); // Set up the layout of the panel
        displayPanel.add(displayLabel); // Put the text label inside the panel

        frame.add(displayPanel, BorderLayout.NORTH); // Put the panel inside the window (aligned to north/top)

        // Styling for buttonsPanel
        buttonsPanel.setLayout(new GridLayout(5, 4)); // Set the layout of the panels for the buttons to have 5 rows and
                                                      // 4 cols
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel); // Add the buttonsPanel to the window

        // Adding all the buttons to the buttonsPanel
        for (int i = 0; i < buttonValues.length; i++) {
            // Declare button and the value
            JButton button = new JButton();
            String buttonValue = buttonValues[i];

            // Stylize button
            button.setOpaque(true);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack, 1));

            // Stylize topSymbols
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } // Stylize rightSymbols
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } // Stylize digits
            else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            // Add button to buttonsPanel
            buttonsPanel.add(button);

            // Add action listener so button is usable
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource(); // e is the event and e.getSource() is the button clicked
                    String buttonValue = button.getText(); // Get the value (operator) of the button pressed
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        // Button from rightSymbols clicked

                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        // Button from topSymbols clicked

                    } else { // Digits or "."
                        if (buttonValue == ".") {

                        } else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                // If the current text on the displayLabel is 0, replace it.
                                displayLabel.setText(buttonValue);
                            } else {
                                // Otherwise append the value pressed.
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        }
    }
}
