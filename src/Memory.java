/**@author Samantha Perez
 * September 23, 2019
 * Description: this program will play a memory game with the user and will continue to until the user decides
 * he does not want to play another round.
 */

import java.util.Scanner;
import java.util.Random;

public class Memory {


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);


        int initializer = 1;

        while (initializer == 1) {
            // c will be initializing the inner while loop
            int c = 1;

            String[][] values = {{"A", "B", "C", "D"},
                    {"E", "F", "G", "H"},
                    {"A", "B", "C", "D"},
                    {"E", "F", "G", "H"}};


            Boolean[][] upDown = {{false, false, false, false},
                    {false, false, false, false},
                    {false, false, false, false},
                    {false, false, false, false}};


            System.out.println("-New Game-");

            values = shuffle(values);
            displayBoard(values, upDown);


            while (c == 1) {
                int cardOne = getChoice();

                // checking if the card has already been matched
                while (checkFlipped(cardOne, upDown) == true) {
                    System.out.println("Card has already been matched ");
                    cardOne = getChoice();
                }

                // flipping over card one
                upDown = flipChoice(cardOne, upDown);

                displayBoard(values, upDown);


                int cardTwo = getChoice();

                // validating if the user has already enetered that card
                while (cardTwo == cardOne) {
                    System.out.println("Location already entered");
                    cardTwo = getChoice();
                }

                // checking if that card has already been matched
                while (checkFlipped(cardTwo, upDown) == true) {
                    System.out.println("Card has already been matched ");
                    cardTwo = getChoice();
                }

                // flipping over card two
                upDown = flipChoice(cardTwo, upDown);

                // determining whether or not cards are a match
                if (isMatch(cardOne, cardTwo, values) == false) {
                    System.out.println();
                    displayBoard(values, upDown);

                    // flipping cards back over if not a match
                    upDown = flipChoice(cardOne, upDown);
                    upDown = flipChoice(cardTwo, upDown);
                    System.out.println("Not a match.");

                } else if (isMatch(cardOne, cardTwo, values) == true) {
                    displayBoard(values, upDown);
                    System.out.println("Match found!");

                }

                c = 0;
                // checking to see if the game is finished by checking if any
                // cards are faced down
                for (int i = 0; i < upDown.length; i++) {

                    for (int j = 0; j < upDown.length; j++) {
                        if (upDown[i][j] == false) {
                            c = 1;
                        }
                    }
                }
            }

            String play;

            // block will determine if the user wants to keep playing
            do {
                System.out.println("Play again? Yes or No?");
                play = in.next();
                play = play.toLowerCase();

            } while (!(play.equals("yes") || play.equals("no")));

            if (play.equals("yes")) {
                initializer = 1;
            } else {
                initializer = 0;
            }
        }
    }

    /**
     * This method gets the users selection for the card they want.
     * @return returns a valid selection.
     */

    public static int getChoice() {

        System.out.println("Please enter choice: ");
        int choice = CheckInput.getIntRange(1, 16);

        return choice;
    }


    /**
     * This method display the board each time with the cards either face up or face down.
     *
     * @param val recieves an array of strings from A-H for the game.
     * @param UpD recieves a array of boolena values that determine if a card is faced up or down.
     */
    public static void displayBoard(String val[][], Boolean UpD[][]) {

        int[][] Pos = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};

        String S;

        for (int i = 0; i < 4; i++) {

            System.out.println("+----+\t+----+\t+----+\t+----+");
            System.out.println("|    |\t|    |\t|    |\t|    |");
            //prints numbers
            for (int j = 0; j < 4; j++) {

                // will face up if needed
                if (UpD[i][j] == true) {
                    S = val[i][j];
                } else {
                    // turning into string for output
                    S = Integer.toString(Pos[i][j]);

                }
                S = String.format("%2s", S);
                System.out.print("| " + S + " |\t");
            }
            System.out.println("\n|    |\t|    |\t|    |\t|    |");
            System.out.println("+----+\t+----+\t+----+\t+----+\n");
        }
    }

    /**
     * This method shuffles the values inside the arrray of strings by choosing two and swapping them.
     *
     * @param values recieves an array of strings that are the cards you need to match.
     * @return returns the array of strings shuffled up.
     */
    public static String[][] shuffle(String values[][]) {

        for (int i = 0; i < 100; i++) {

            //generating random indexes to swap
            int index1 = new Random().nextInt(4);
            int index2 = new Random().nextInt(4);

            int switcher = new Random().nextInt(4);
            int switcher2 = new Random().nextInt(4);

            //swapping values
            for (int j = 0; j < 1; j++) {

                String temp = values[index1][index2];
                values[index1][index2] = values[switcher][switcher2];
                values[switcher][switcher2] = temp;
            }

        }
        return values;
    }

    /**
     * This method flips over the card , selected by the user, either up or down.
     *
     * @param choice recieves the users selection.
     * @param face   recieves an array of boolean values.
     * @return returns back the updated boolean array.
     */
    public static Boolean[][] flipChoice(int choice, Boolean face[][]) {

        // getting the location of card chosen.
        int row = (choice - 1) / 4;
        int column = (choice - 1) % 4;

        //flipping over the card.
        if (face[row][column] == true) {
            face[row][column] = false;
        } else if (face[row][column] == false) {
            face[row][column] = true;
        }
        return face;
    }

    /**
     * This method determines if two cards are a match by comparing the two.
     *
     * @param choiceOne recieves the users choice of card one.
     * @param choiceTwo recieves the users choice of card two.
     * @param values    recieves an array of string values.
     * @return returns back a boolean value of either true or false if the two cards are a match or aren't.
     */
    public static boolean isMatch(int choiceOne, int choiceTwo, String values[][]) {
        // getting the index of card one and card two.
        int rowOne = getRow(choiceOne);
        int columnOne = getColumn(choiceOne);

        int rowTwo = getRow(choiceTwo);
        int columnTwo = getColumn(choiceTwo);

        // comparing the two to see if theyre a match.
        if (values[rowOne][columnOne] == values[rowTwo][columnTwo]) {
            return true;
        }
        return false;
    }

    /**
     * This function determines if the card chosen by the user is already flipped up or down.
     *
     * @param choice recieves an integer which is the users choice.
     * @param face   recieves an array of boolean values.
     * @return returns back a boolean value representing if the card is or isn't flipped.
     */
    public static Boolean checkFlipped(int choice, Boolean face[][]) {

        int row = getRow(choice);
        int column = getColumn(choice);

        if (face[row][column] == true) {
            return true;
        }
        return false;
    }

    /**
     * This function gets the row index needed by dividing by 4.
     * @param choice is the users choice in which we want to find the specific row it is in.
     * @return returns the row index.
     */

    public static int getRow( int choice ){
        int row = (choice - 1) / 4;
        return row;
    }

    /**
     * This function gets the column index needed by dividing by 4.
     * @param choice is the users choice in which we want to find the specific column  it is in.
     * @return returns the column index.
     */
    public static int getColumn( int choice ){
        int column = (choice - 1) % 4;
        return column;
    }
}