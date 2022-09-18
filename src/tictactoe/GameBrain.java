package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class GameBrain {

    public static int[] winningPosition = new int[2];
    public static int[] blockPosition = new int[2];

    /**
     * Finds or accepts the coordinates for the next input move depending on whether the player is a user or
     * the computer
     *
     * @param mode  denotes the mode, i.e. (user, easy, medium, hard)
     * @param board passes the current board as the object
     * @param turn  denotes whose turn it is
     */
    public static void coordinateInput(String mode, Board board, int turn) {
        int[] coordinate = new int[2];
        int[][] currentState = board.getGameState();

        Scanner scanner = new Scanner(System.in);

        //Accepting coordinates from user to make a move
        if ("user".equals(mode)) {
            boolean retryInput = true;
            do {
                System.out.print("Enter the coordinates: > ");
                if (scanner.hasNextInt()) {
                    coordinate[0] = scanner.nextInt();
                    coordinate[1] = scanner.nextInt();

                } else {
                    System.out.println("You should enter numbers!");
                    scanner.nextLine();
                    continue;
                }
                if (coordinate[0] > 3 || coordinate[0] < 1 || coordinate[1] > 3 || coordinate[1] < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");

                    continue;
                }
                if (currentState[coordinate[0] - 1][coordinate[1] - 1] != 0) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                retryInput = false;

            } while (retryInput);

        }

        //If the computer difficulty is set to easy, select a random coordinate!
        if ("easy".equals(mode)) {
            System.out.println("Making move level \"easy\"");
            coordinate = randomSelection(currentState);

        }

        if ("medium".equals(mode)) {
            System.out.println("Making move level \"medium\"");
            if (winning(currentState, turn)) {
                coordinate = winningPosition.clone();
            } else if (opponentWinning(currentState, turn)) {
                coordinate = blockPosition.clone();
            } else {
                coordinate = randomSelection(currentState);
            }
        }


        board.makeMove(turn, coordinate[0], coordinate[1]);
        clearPositions();
    }


    /**
     * Finds a random empty coordinate to place a piece in
     *
     * @param currentState 2-D array containing the current state of the board
     * @return returns an array of integers with the random coordinates
     */
    public static int[] randomSelection(int[][] currentState) {
        int[] coordinate = new int[2];
        boolean found = false;
        int choice;
        Random random = new Random();
        while (!found) {
            choice = random.nextInt(3) + 1;
            coordinate[0] = choice;
            choice = random.nextInt(3) + 1;
            coordinate[1] = choice;
            if (currentState[coordinate[0] - 1][coordinate[1] - 1] == 0)
                found = true;
        }
        return coordinate;
    }

    /**
     * Checks if the opponent is in a winning position
     *
     * @param currentState 2-D array denoting the current state of the board
     * @param turn         integer representing whose turn it currently is
     * @return returns true if the opponent is in a winning pos and false if the opponent is not in a winning position
     */
    public static boolean opponentWinning(int[][] currentState, int turn) {
        int[] position = {-1, -1};
        int opponent = 1 - turn;
        int[] pieces = {1, 2};
        int flag;
        //Checks each row and finds the block position if it exists
        for (int r = 0; r < 3; r++) {
            flag = 0;
            position[0] = -1;
            position[1] = -1;
            for (int c = 0; c < 3; c++) {
                if (currentState[r][c] == pieces[opponent]) {
                    flag += pieces[opponent];
                } else if (currentState[r][c] == 0) {
                    position[0] = r + 1;
                    position[1] = c + 1;
                } else break;
            }
            if (flag == 2 * pieces[opponent] && position[0] != -1 && position[1] != -1) {
                blockPosition = position.clone();
                return true;
            }
        }

        //Checks each column and finds the block position if it exists
        for (int c = 0; c < 3; c++) {
            flag = 0;
            position[0] = -1;
            position[1] = -1;
            for (int r = 0; r < 3; r++) {
                if (currentState[r][c] == pieces[opponent]) {
                    flag += pieces[opponent];
                } else if (currentState[r][c] == 0) {
                    position[0] = r + 1;
                    position[1] = c + 1;
                } else break;
            }
            if (flag == 2 * pieces[opponent] && position[0] != -1 && position[1] != -1) {
                blockPosition = position.clone();
                return true;

            }
        }
        //Checks the first diagonal and finds the block position if it exists
        flag = 0;
        position[0] = -1;
        position[1] = -1;
        for (int i = 0; i < 3; i++) {
            if (currentState[i][i] == pieces[opponent]) {
                flag += pieces[opponent];
            } else if (currentState[i][i] == 0) {
                position[0] = i + 1;
                position[1] = i + 1;
            } else break;
        }
        if (flag == 2 * pieces[opponent] && position[0] != -1 && position[1] != -1) {
            blockPosition = position.clone();
            return true;

        }

        //Checks the second diagonal and finds the block position if it exists
        flag = 0;
        position[0] = -1;
        position[1] = -1;
        for (int i = 0; i < 3; i++) {
            if (currentState[i][2 - i] == pieces[opponent]) {
                flag += pieces[opponent];
            } else if (currentState[i][2 - i] == 0) {
                position[0] = i + 1;
                position[1] = 2 - i + 1;
            } else break;
        }
        if (flag == 2 * pieces[opponent] && position[0] != -1 && position[1] != -1) {
            blockPosition = position.clone();
            return true;

        }


        return false;
    }

    /**
     * Checks if the player is currently in a winning position
     *
     * @param currentState 2-D array representing the current board
     * @param turn         integer representing the current turn
     * @return returns true if the player is in a winning position and false if the player is not in a winning position
     */
    public static boolean winning(int[][] currentState, int turn) {
        int[] position = {-1, -1};
        int[] pieces = {1, 2};
        int flag;
        int i;

        //Checks each row and finds the winning position if it exists
        for (int r = 0; r < 3; r++) {
            flag = 0;
            position[0] = -1;
            position[1] = -1;
            for (int c = 0; c < 3; c++) {
                if (currentState[r][c] == pieces[turn]) {
                    flag += pieces[turn];
                } else if (currentState[r][c] == 0) {
                    position[0] = r + 1;
                    position[1] = c + 1;
                } else break;
            }
            if (flag == 2 * pieces[turn] && position[0] != -1 && position[1] != -1) {
                winningPosition = position.clone();
                return true;
            }
        }

        //Checks each column and finds the winning position if it exists
        for (int c = 0; c < 3; c++) {
            flag = 0;
            position[0] = -1;
            position[1] = -1;
            for (int r = 0; r < 3; r++) {
                if (currentState[r][c] == pieces[turn]) {
                    flag += pieces[turn];
                } else if (currentState[r][c] == 0) {
                    position[0] = r + 1;
                    position[1] = c + 1;
                } else break;
            }
            if (flag == 2 * pieces[turn] && position[0] != -1 && position[1] != -1) {
                winningPosition = position.clone();
                return true;

            }
        }

        //Checks the first diagonal and finds the winning position if it exists
        flag = 0;
        position[0] = -1;
        position[1] = -1;
        for (i = 0; i < 3; i++) {
            if (currentState[i][i] == pieces[turn]) {
                flag += pieces[turn];
            } else if (currentState[i][i] == 0) {
                position[0] = i + 1;
                position[1] = i + 1;
            } else break;

        }
        if (flag == 2 * pieces[turn] && position[0] != -1 && position[1] != -1) {
            winningPosition = position.clone();
            return true;

        }

        //Checks the second diagonal and finds the winning position if it exists
        flag = 0;
        position[0] = -1;
        position[1] = -1;
        for (i = 0; i < 3; i++) {
            if (currentState[i][2 - i] == pieces[turn]) {
                flag += pieces[turn];
            } else if (currentState[i][2 - i] == 0) {
                position[0] = i + 1;
                position[1] = 2 - i + 1;
            } else break;
        }
        if (flag == 2 * pieces[turn] && position[0] != -1 && position[1] != -1) {
            winningPosition = position.clone();
            return true;

        }


        return false;
    }


    /**
     * Wipes the block position and winning position variables to prepare for the next input
     */
    public static void clearPositions() {
        int[] clearArray = {-1, -1};
        blockPosition = clearArray.clone();
        winningPosition = clearArray.clone();
    }
}

