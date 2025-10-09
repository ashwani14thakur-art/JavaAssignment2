import java.util.Scanner;

public class CalculatorApp {

  
    static class Calculator {
            public int add(int a, int b) {
                 return a + b;
        }

        public double add(double a, double b) {
            return a + b;
        }

        public int add(int a, int b, int c) {
            return a + b + c;
        }

        public int subtract(int a, int b) {
            return a - b;
        }

        public double multiply(double a, double b) {
            return a * b;
        }

        public double divide(int a, int b) {
            if (b == 0) {
                System.out.println("Error: Cannot divide by zero!");
                return 0;
            } else {
                return (double) a / b;
            }
        }
    }

    Scanner sc = new Scanner(System.in);
    Calculator calc = new Calculator();

    void performAddition() {
        System.out.println("Choose addition type:");
        System.out.println("1. Two integers");
        System.out.println("2. Two doubles");
        System.out.println("3. Three integers");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter first integer: ");
                int a1 = sc.nextInt();
                System.out.print("Enter second integer: ");
                int b1 = sc.nextInt();
                System.out.println("Result: " + calc.add(a1, b1));
                break;

            case 2:
                System.out.print("Enter first double: ");
                double a2 = sc.nextDouble();
                System.out.print("Enter second double: ");
                double b2 = sc.nextDouble();
                System.out.println("Result: " + calc.add(a2, b2));
                break;

            case 3:
                System.out.print("Enter first integer: ");
                int x = sc.nextInt();
                System.out.print("Enter second integer: ");
                int y = sc.nextInt();
                System.out.print("Enter third integer: ");
                int z = sc.nextInt();
                System.out.println("Result: " + calc.add(x, y, z));
                break;

            default:
                System.out.println("Invalid choice!");
        }
    }

    void performSubtraction() {
        System.out.print("Enter first integer: ");
        int a = sc.nextInt();
        System.out.print("Enter second integer: ");
        int b = sc.nextInt();
        System.out.println("Result: " + calc.subtract(a, b));
    }

    void performMultiplication() {
        System.out.print("Enter first double: ");
        double a = sc.nextDouble();
        System.out.print("Enter second double: ");
        double b = sc.nextDouble();
        System.out.println("Result: " + calc.multiply(a, b));
    }

    void performDivision() {
        System.out.print("Enter first integer: ");
        int a = sc.nextInt();
        System.out.print("Enter second integer: ");
        int b = sc.nextInt();
        double result = calc.divide(a, b);
        System.out.println("Result: " + result);
    }

    void mainMenu() {
        int choice;
        do {
            System.out.println("\n--- Calculator Application ---");
            System.out.println("1. Add Numbers");
            System.out.println("2. Subtract Numbers");
            System.out.println("3. Multiply Numbers");
            System.out.println("4. Divide Numbers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    performAddition();
                    break;
                case 2:
                    performSubtraction();
                    break;
                case 3:
                    performMultiplication();
                    break;
                case 4:
                    performDivision();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
    }

    public static void main(String[] args) {
        CalculatorApp ui = new CalculatorApp();
        System.out.println("Welcome to the Calculator Application!");
        ui.mainMenu();
    }
}

