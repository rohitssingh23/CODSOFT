import java.util.Scanner;
import java.util.Random;

public class NumberGame {
    private final Scanner scanner;
    private final Random random;
    private final int minRange;
    private final int maxRange;
    private final int maxAttempts;
    private int totalRounds;
    private int totalScore;

    public NumberGame(int minRange, int maxRange, int maxAttempts) {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.maxAttempts = maxAttempts;
        this.totalRounds = 0;
        this.totalScore = 0;
    }

    public void playGame() {
        boolean playAgain = true;

        while (playAgain) {
            int randomNumber = generateRandomNumber();
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("Welcome to the Number Guessing Game!");
            System.out.println("I have selected a number between " + minRange + " and " + maxRange + ".");
            System.out.println("You have " + maxAttempts + " attempts to guess it.");

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int guess = getValidGuess();

                attempts++;

                if (guess == randomNumber) {
                    System.out.println("Congratulations! You've guessed the correct number in " + attempts + " attempts!");
                    totalScore += maxAttempts - attempts + 1;
                    guessedCorrectly = true;
                } else if (guess < randomNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you've run out of attempts. The number was: " + randomNumber);
            }

            totalRounds++;
            System.out.println("Your current score: " + totalScore);
            System.out.print("Would you like to play again? (yes/no): ");
            String playChoice = scanner.next().toLowerCase();
            if (!playChoice.equals("yes")) {
                playAgain = false;
            }
        }

        System.out.println("Thank you for playing! Here are your final score:");
        System.out.println("Total rounds played: " + totalRounds);
        System.out.println("Total score: " + totalScore);

        // Close scanner
        scanner.close();
    }

    private int generateRandomNumber() {
        return random.nextInt(maxRange - minRange + 1) + minRange;
    }

    private int getValidGuess() {
        while (true) {
            try {
                int guess = Integer.parseInt(scanner.next());
                if (guess >= minRange && guess <= maxRange) {
                    return guess;
                } else {
                    throw new IllegalArgumentException("Guess out of range");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        NumberGame game = new NumberGame(1, 100, 10);
        game.playGame();
    }
}
