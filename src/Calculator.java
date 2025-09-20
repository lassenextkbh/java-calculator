import java.awt.*;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    // UI Constants
    private static final int WINDOW_WIDTH = 360;
    private static final int WINDOW_HEIGHT = 540;

    // Colors
    private static final Color LIGHT_GRAY = new Color(212, 212, 210);
    private static final Color DARK_GRAY = new Color(80, 80, 80);
    private static final Color BLACK = new Color(28, 28, 28);
    private static final Color ORANGE = new Color(255, 149, 0);

    // Button categories
    private static final Set<String> OPERATORS = Set.of("/", "×", "-", "+", "=");
    private static final Set<String> TOP_FUNCTIONS = Set.of("AC", "+/-", "%");
    private static final Set<String> DIGITS = Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    private static final String[] BUTTON_LAYOUT = {
            "AC", "+/-", "%", "/",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };

    // UI Components
    private final JFrame frame;
    private final JLabel displayLabel;
    private final JPanel displayPanel;
    private final JPanel buttonsPanel;

    // Calculator state
    private String firstOperand = "0";
    private String operator = null;
    private String secondOperand = null;

    public Calculator() {
        frame = new JFrame("Calculator");
        displayLabel = new JLabel();
        displayPanel = new JPanel();
        buttonsPanel = new JPanel();

        initializeUI();
        createButtons();
        frame.setVisible(true);
    }

    private void initializeUI() {
        setupFrame();
        setupDisplay();
        setupButtonsPanel();
    }

    private void setupFrame() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void setupDisplay() {
        displayLabel.setBackground(BLACK);
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);
    }

    private void setupButtonsPanel() {
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(BLACK);
        frame.add(buttonsPanel);
    }

    private void createButtons() {
        for (String buttonText : BUTTON_LAYOUT) {
            JButton button = createStyledButton(buttonText);
            button.addActionListener(e -> handleButtonClick(buttonText));
            buttonsPanel.add(button);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.setFocusable(false);
        button.setBorder(new LineBorder(BLACK, 1));

        // Apply styling based on button type
        if (TOP_FUNCTIONS.contains(text)) {
            button.setBackground(LIGHT_GRAY);
            button.setForeground(BLACK);
        } else if (OPERATORS.contains(text)) {
            button.setBackground(ORANGE);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(DARK_GRAY);
            button.setForeground(Color.WHITE);
        }

        return button;
    }

    private void handleButtonClick(String buttonText) {
        if (OPERATORS.contains(buttonText)) {
            handleOperator(buttonText);
        } else if (TOP_FUNCTIONS.contains(buttonText)) {
            handleTopFunction(buttonText);
        } else if (DIGITS.contains(buttonText)) {
            handleDigit(buttonText);
        } else if (".".equals(buttonText)) {
            handleDecimalPoint();
        } else if ("√".equals(buttonText)) {
            handleSquareRoot();
        }
    }

    private void handleOperator(String op) {
        if ("=".equals(op)) {
            calculateResult();
        } else {
            prepareForNextOperand();
            operator = op;
        }
    }

    private void handleTopFunction(String function) {
        switch (function) {
            case "AC":
                clearAll();
                displayLabel.setText("0");
                break;
            case "+/-":
                toggleSign();
                break;
            case "%":
                convertToPercentage();
                break;
        }
    }

    private void handleDigit(String digit) {
        String currentDisplay = displayLabel.getText();
        if ("0".equals(currentDisplay)) {
            displayLabel.setText(digit);
        } else {
            displayLabel.setText(currentDisplay + digit);
        }
    }

    private void handleDecimalPoint() {
        String currentDisplay = displayLabel.getText();
        if (!currentDisplay.contains(".")) {
            displayLabel.setText(currentDisplay + ".");
        }
    }

    private void handleSquareRoot() {
        double value = Double.parseDouble(displayLabel.getText());
        double result = Math.sqrt(value);
        displayLabel.setText(formatResult(result));
    }

    private void calculateResult() {
        if (firstOperand == null || operator == null) {
            return;
        }

        secondOperand = displayLabel.getText();
        double numA = Double.parseDouble(firstOperand);
        double numB = Double.parseDouble(secondOperand);
        double result;

        switch (operator) {
            case "+":
                result = numA + numB;
                break;
            case "-":
                result = numA - numB;
                break;
            case "×":
                result = numA * numB;
                break;
            case "/":
                if (numB == 0) {
                    displayLabel.setText("Error");
                    clearAll();
                    return;
                }
                result = numA / numB;
                break;
            default:
                return;
        }

        displayLabel.setText(formatResult(result));
        clearAll();
    }

    private void prepareForNextOperand() {
        if (operator == null) {
            firstOperand = displayLabel.getText();
            displayLabel.setText("0");
            secondOperand = "0";
        }
    }

    private void toggleSign() {
        double value = Double.parseDouble(displayLabel.getText());
        value *= -1;
        displayLabel.setText(formatResult(value));
    }

    private void convertToPercentage() {
        double value = Double.parseDouble(displayLabel.getText());
        value /= 100;
        displayLabel.setText(formatResult(value));
    }

    private void clearAll() {
        firstOperand = "0";
        operator = null;
        secondOperand = null;
    }

    private String formatResult(double value) {
        // Remove unnecessary decimal places for whole numbers
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        } else {
            return String.valueOf(value);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}