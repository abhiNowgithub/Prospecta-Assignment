import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CsvFormulaEvaluator {

    private Map<String, Double> values = new HashMap<>();
    private String[][] data; // To store the CSV data

    public static void main(String[] args) throws IOException {
        String inputFilePath = "C:\\Users\\risha\\Downloads\\problem1\\CSVFormulaEvaluator\\src\\input.csv";
        String outputFilePath = "output7.csv";

        CsvFormulaEvaluator evaluator = new CsvFormulaEvaluator();
        evaluator.csvProcessor(inputFilePath, outputFilePath);
    }

    public void csvProcessor(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int rowNum = 1;

            // Read header
            String header = reader.readLine();
            writer.write(header); // Write header to output
            writer.newLine();

            // Read data into array for processing
            data = reader.lines().map(lineValue -> lineValue.split(",")).toArray(String[][]::new);

            // Process each row
            for (String[] cells : data) {
                for (int i = 0; i < cells.length; i++) {
                    String cellReference = getCellReference(i + 1, rowNum);
                    String cellValue = cells[i].trim();

                    if (isFormula(cellValue)) {
                        double evaluatedValue = evaluateFormula(cellValue);
                        values.put(cellReference, evaluatedValue);
                        writer.write(String.valueOf(evaluatedValue));
                    } else {
                        double numericValue = Double.parseDouble(cellValue);
                        values.put(cellReference, numericValue);
                        writer.write(cellValue);
                    }

                    writer.write(","); // Write delimiter
                }
                writer.newLine(); // Move to next row
                rowNum++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCellReference(int column, int row) {
        char columnLetter = (char) ('A' + column - 1);
        return columnLetter + String.valueOf(row);
    }

    private boolean isFormula(String cellValue) {
        return cellValue.startsWith("=");
    }

    private double evaluateFormula(String formula) {
        String expression = formula.substring(1); // Remove the '=' sign
        String[] tokens = expression.split("\\+|-|\\*|/");

        double result = 0;
        String[] operators = expression.split("[^\\+\\-\\*\\/]+"); // Split by numbers

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            if (token.matches("[A-Z]\\d+")) { // Check if it's a cell reference
                result += values.getOrDefault(token, 0.0);
            } else {
                result += Double.parseDouble(token); // Parse as double
            }

            // Apply operators to the result
            if (i < operators.length) {
                String operator = operators[i].trim();
                if (operator.equals("-")) {
                    result = -result; // Apply subtraction
                } else if (operator.equals("*")) {
                    result *= Double.parseDouble(tokens[i + 1].trim()); // Multiplication
                } else if (operator.equals("/")) {
                    double divisor = Double.parseDouble(tokens[i + 1].trim());
                    if (divisor != 0) {
                        result /= divisor; // Division
                    } else {
                        return Double.NaN; // Handle division by zero
                    }
                }
            }
        }
        return result;
    }
}
