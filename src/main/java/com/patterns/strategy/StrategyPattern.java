package com.patterns.strategy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.patterns.strategy.strategies.BubbleSort;
import com.patterns.strategy.strategies.InsertionSort;
import com.patterns.strategy.strategies.SortStrategy;

/**
 * The <code>StrategyPattern</code> class is the main entry point to demonstrate
 * the strategy design pattern with sorting algorithms through a Swing GUI.
 *
 * @author Luan Nadaletti
 */
public class StrategyPattern {

    /**
     * The main entry point of the application.
     *
     * @param args Command line arguments. Not used in this application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SortingFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}

/**
 * The <code>SortingFrame</code> class provides a Swing interface to select,
 * input and execute different sorting strategies.
 */
class SortingFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JComboBox<String> algorithmComboBox;
    private final JTextArea inputTextArea;
    private final JTextArea outputTextArea;

    /**
     * Constructs a new SortingFrame with all the necessary GUI components.
     */
    public SortingFrame() {
        setTitle("Sorting App");
        setSize(600, 400);

        algorithmComboBox = new JComboBox<>();
        algorithmComboBox.addItem("Bubble Sort");
        algorithmComboBox.addItem("Insertion Sort");
        algorithmComboBox.setSelectedItem("Bubble Sort");

        inputTextArea = new JTextArea(10, 20);
        outputTextArea = new JTextArea(10, 20);
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        inputTextArea.setLineWrap(true);
        outputTextArea.setLineWrap(true);

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmComboBox
                    .getSelectedItem();
            SortStrategy sortStrategy = getSortStrategyByLabel(
                    selectedAlgorithm);

            int[] array = inputArrayUsingDialog();
            if (array != null) {
                sortStrategy.sort(array);
                outputTextArea.setText(sortStrategy.getSortInfo());
            }
        });

        JButton randomListButton = new JButton("Random Numbers");
        randomListButton.addActionListener(e -> {
            StringBuilder randomNumbersListToString = new StringBuilder();
            for (Integer number : generateRandomList()) {
                randomNumbersListToString.append(number + " ");
            }
            inputTextArea.setText(randomNumbersListToString.toString());
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Enter numbers separated by spaces:"));
        inputPanel.add(new JScrollPane(inputTextArea));

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.add(new JLabel("Sort Information:"));
        outputPanel.add(new JScrollPane(outputTextArea));

        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        algorithmPanel.add(new JLabel("Choose sorting algorithm:"));
        algorithmPanel.add(algorithmComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(sortButton);
        buttonPanel.add(randomListButton);

        JPanel inputOutputPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        inputOutputPanel.add(inputPanel);
        inputOutputPanel.add(outputPanel);

        setLayout(new BorderLayout());
        add(algorithmPanel, BorderLayout.NORTH);
        add(inputOutputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Captures the input numbers from the text area, and converts them into an
     * int array. Displays a dialog if the input contains non-numeric
     * characters.
     *
     * @return an array of integers representing the user input or null if input
     *         is empty or invalid.
     */
    private int[] inputArrayUsingDialog() {
        String inputText = inputTextArea.getText().trim();

        if (!inputText.isEmpty()) {
            String[] inputArray = inputText.split("\\s+");
            int[] array = new int[inputArray.length];

            for (int i = 0; i < inputArray.length; i++) {
                try {
                    array[i] = Integer.parseInt(inputArray[i]);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid input. Please enter valid numbers.");
                    return null;
                }
            }

            return array;
        }

        return null;
    }

    /**
     * Returns the sort strategy based on the label provided.
     *
     * @param label the name/label of the sorting algorithm.
     * @return a {@link SortStrategy} instance based on the given label, or null
     *         if not matched.
     */
    private SortStrategy getSortStrategyByLabel(String label) {
        if (label.equalsIgnoreCase("bubble sort")) {
            return new BubbleSort();
        }
        if (label.equalsIgnoreCase("insertion sort")) {
            return new InsertionSort();
        }

        return null;
    }

    /**
     * Generates a list of random integers.
     *
     * @return a list of random integers.
     */
    private List<Integer> generateRandomList() {
        Random random = new Random();

        int maxListSize = 10000;
        int listSize = random.nextInt(maxListSize);

        List<Integer> resultList = new ArrayList<>(listSize);

        for (int i = 0; i < listSize; i++) {
            resultList.add(random.nextInt(10000));
        }

        return resultList;
    }

}
