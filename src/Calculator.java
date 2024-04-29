import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Calculator implements ActionListener {
    public static JTextField textField;
    public static String curText = "0";
    public static double curFirstNumber = 0;
    public static String curOperation = "";
    public static double curSecondNumber = 0;

    // fonts
    Font font = new Font("Courier", Font.PLAIN, 45);
    Font buttonFont = new Font("Courier", Font.PLAIN, 30);

    public Calculator() {
        // main window
        JFrame mainFrame = new JFrame("Calculator");
        mainFrame.setSize(400, 585);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);

        // output text field
        textField = new JTextField();
        textField.setFont(font);
        textField.setBounds(12, 10, 362, 80);
        textField.setEditable(false);
        textField.setFocusable(false);
        textField.setText("0");

        // buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonsPanel.setBounds(10, 120, 365, 400);

        // buttons
        String[] buttonNames = {"C", "Del", "%", "/", "7", "8", "9", "*", "4", "5", "6", "+", "1", "2", "3", "-", "+/-", "0", ".", "="};
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setFocusable(false);
            button.setFont(buttonFont);
            button.addActionListener(this);
            buttonsPanel.add(button);
        }

        mainFrame.add(textField);
        mainFrame.add(buttonsPanel);
        mainFrame.setVisible(true);
    }

    public static void delDecPointIfPossible() {
        if (curOperation.isEmpty()) {
            if (Double.compare(curFirstNumber, (int) curFirstNumber) == 0) {
                curText = String.valueOf((int) curFirstNumber);
            }
        }
        else {
            if (Double.compare(curSecondNumber, (int) curSecondNumber) == 0) {
                curText = String.valueOf((int) curSecondNumber);
            }
        }
        textField.setText(curText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String func = e.getActionCommand();
        if ("0123456789".contains(func)) {
            if (textField.getText().length() == 14) return;
            if (curOperation.isEmpty()) {
                if (curText.equals("0")) {
                    curText = func;
                } else {
                    curText += func;
                }
                curFirstNumber = Double.parseDouble(curText);
            }
            else {
                if (curSecondNumber == 0 && !curText.endsWith(".")) {
                    curText = String.valueOf(e.getActionCommand());
                } else {
                    curText += String.valueOf(e.getActionCommand());
                }
                curSecondNumber = Double.parseDouble(curText);
            }
            textField.setText(curText);
        }
        if (func.equals("/") || func.equals("*") || func.equals("+") || func.equals("-")) {
            if (curOperation.isEmpty()) {
                curOperation = func;
                curText = "0";
                textField.setText(curText);
            }
        }
        else if (func.equals(".")) {
            if (!curText.contains(".")) {
                curText += ".";
                textField.setText(curText);
            }
        }
        else if (func.equals("=")) {
            switch (curOperation) {
                case "/":
                    curText = String.valueOf(curFirstNumber / curSecondNumber);
                    break;
                case "*":
                    curText = String.valueOf(curFirstNumber * curSecondNumber);
                    break;
                case "+":
                    curText = String.valueOf(curFirstNumber + curSecondNumber);
                    break;
                case "-":
                    curText = String.valueOf(curFirstNumber - curSecondNumber);
                    break;
            }

            curOperation = "";
            curFirstNumber = Double.parseDouble(curText);
            curSecondNumber = 0;

            if (curText.length() > 14) {
                DecimalFormat df = new DecimalFormat("#.############");
                df.setRoundingMode(RoundingMode.HALF_UP);
                curText = df.format(Double.parseDouble(curText));
            }
            textField.setText(curText);
            delDecPointIfPossible();
        }
        else if (func.equals("+/-")) {
            if (curOperation.isEmpty()) {
                curFirstNumber *= -1;
                if (curFirstNumber == -0) curFirstNumber = 0;
                curText = String.valueOf(curFirstNumber);
                textField.setText(curText);
            }
            else {
                curSecondNumber *= -1;
                if (curSecondNumber == -0) curSecondNumber = 0;
                curText = String.valueOf(curSecondNumber);
                textField.setText(curText);
            }
            delDecPointIfPossible();
        }
        else if (func.equals("C")) {
            curText = "0";
            curOperation = "";
            curFirstNumber = 0;
            curSecondNumber = 0;
            textField.setText(curText);
        }
        else if (func.equals("%")) {
            if (curOperation.equals("/") || curOperation.equals("*")) {
                curSecondNumber /= 100;
                curText = String.valueOf(curSecondNumber);
            }
            else if (curOperation.equals("+") || curOperation.equals("-")) {
                curSecondNumber = (curSecondNumber / 100) * curFirstNumber;
                curText = String.valueOf(curSecondNumber);
            }
            else {
                curFirstNumber = 0;
                curText = String.valueOf(curFirstNumber);
            }
            textField.setText(curText);
            delDecPointIfPossible();
        }
        else if (func.equals("Del")) {
            if (!curText.startsWith("-") && curText.length() > 1 || (curText.length() > 2)) {
                curText = curText.substring(0, curText.length() - 1);
            }
            else {
                curText = "0";
            }
            textField.setText(curText);

            if (curOperation.isEmpty()) {
                curFirstNumber = Double.parseDouble(curText);
            }
            else {
                curSecondNumber = Double.parseDouble(curText);
            }
        }
    }
}
