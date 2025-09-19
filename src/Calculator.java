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
            "AC", "+/-", "%", "/",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "=",
    };
    String[] rightSymbols = { "/", "×", "-", "+", "=" };
    String[] topSymbols = { "AC", "+/-", "%" };

    // Declare window and components
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // Declare variables for keeping track of the two numbers and the operator (A+B,
    // A-B, A*B, etc.)
    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
        // General window settings
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
                        if (buttonValue == "=") {
                            if (A != null) {
                                // If A is set to a value, get B from the displayLabel
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+") {
                                    // If the opreator is "+" add A and B together and set the result to the display
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                } else if (operator == "-") {
                                    // If the opreator is "-" subtract B from A and set the result to the display
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                } else if (operator == "×") {
                                    // If the opreator is "*" multiply A by B and set the result to the display
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                } else if (operator == "/") {
                                    // If the opreator is "/" divide A by B and set the result to the display
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }
                        } else if ("+-×/".contains(buttonValue)) {
                            if (operator == null) {
                                // If the operator button hasn't been pressed yet, save the number to A, reset
                                // display and initialize B
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue; // Set the operator to the clicked button
                        }
                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        // Button from topSymbols clicked
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel.getText()); // Convert text to a double
                            numDisplay *= -1; // Multiply it by -1 (flip +/- sign)
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        } else if (buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel.getText()); // Convert text to a double
                            numDisplay /= 100; // Divide it by 100 (get %)
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    } else { // Digits, "." or "√"
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                // If our current displayLabel doesen't contain "."
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if (buttonValue == "√") {
                            double numDisplay = Double.parseDouble(displayLabel.getText()); // Convert text to a double
                            numDisplay = Math.sqrt(numDisplay); // Find the square root of what's on the display.
                            displayLabel.setText(removeZeroDecimal(numDisplay));

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
            // Show the frame when everything has loaded.
            frame.setVisible(true);
        }
    }

    void clearAll() {
        // Clear A and B and the operator
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            // If numDisplay is a whole number
            return Integer.toString((int) numDisplay);
        } else {
            // Otherwise just return it back
            return Double.toString(numDisplay);
        }
    }
}
