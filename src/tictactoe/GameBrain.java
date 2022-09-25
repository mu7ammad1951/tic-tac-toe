package tictactoe;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameBrain {

    private static int[] position = new int[2];
    // private static int[] blockPosition = new int[2];


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


        //Accepting coordinates from user to make a move
        if ("user".equals(mode)) {
            coordinate = acceptUserInput(currentState);
        }


        //If the computer difficulty is set to easy, select a random coordinate!
        if ("easy".equals(mode)) {
            System.out.println("Making move level \"easy\"");
            coordinate = randomSelection(currentState);
        }


        if ("medium".equals(mode)) {
            System.out.println("Making move level \"medium\"");
            if (findWinningPosition(currentState, turn)) {
                coordinate = position.clone();
            } else if (findWinningPosition(currentState, 1 - turn)) {
                coordinate = position.clone();
            } else {
                coordinate = randomSelection(currentState);
            }
        }


        if ("hard".equals(mode)) {
            System.out.println("Making move level \"hard\"");
            int move = bestMove(currentState, turn);
            if (move == -1) {
                System.out.println("Error");
                System.exit(1);
            }
            coordinate[0] = move / 3 + 1;
            coordinate[1] = move % 3 + 1;
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
            if (currentState[coordinate[0] - 1][coordinate[1] - 1] == 0) found = true;
        }
        return coordinate;
    }

    public static int[] acceptUserInput(int[][] currentState) {
        int[] coordinate = new int[2];
        Scanner scanner = new Scanner(System.in);
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
        return coordinate;
    }

    /**
     * Checks if the player is currently in a winning position
     *
     * @param currentState 2-D array representing the current board
     * @param turn         integer representing the current turn
     * @return returns true if the player is in a winning position and false if the player is not in a winning position
     */
    public static boolean findWinningPosition(int[][] currentState, int turn) {
        int[] position = {-1, -1};
        int[] pieces = {1, 2};
        int flag = 0;
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
                GameBrain.position = position.clone();
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
                GameBrain.position = position.clone();
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
            GameBrain.position = position.clone();
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
            GameBrain.position = position.clone();
            return true;

        }


        return false;
    }


    /**
     * Wipes the blockposition and winning position variables to prepare for the next input
     */
    public static void clearPositions() {
        int[] clearArray = {-1, -1};
        // blockPosition = clearArray.clone();
        position = clearArray.clone();
    }


    public static ArrayList<Integer> emptyPositions(int[][] currentState) {
        ArrayList<Integer> emptyPositionsArray = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (currentState[i / 3][i % 3] == 0) {
                emptyPositionsArray.add(i);
            }
        }
        return emptyPositionsArray;
    }

    public static boolean isWinning(int[][] newBoard, int turn) {
        int[] pieces = {1, 2};
        int[][] currentState = newBoard;
        int check = 0;
        //check rows
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentState[i][j] == pieces[turn]) {
                    check += pieces[turn];
                }
            }
            if (check == 3 || check == 6) {
                return true;
            }
            check = 0;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentState[j][i] == pieces[turn]) {
                    check += pieces[turn];
                }
            }
            if (check == 3 || check == 6) {
                return true;
            }
            check = 0;
        }

        for (int i = 0; i < 3; i++) {
            if (currentState[i][i] == pieces[turn]) {
                check += pieces[turn];
            }
        }
        if (check == 3 || check == 6) {
            return true;
        }
        check = 0;

        for (int i = 0; i < 3; i++) {
            if (currentState[i][2 - i] == pieces[turn]) {
                check += pieces[turn];
            }
        }
        if (check == 3 || check == 6) {
            return true;
        }

        return false;
    }

    public static int minimax(int[][] newBoard, int turn, boolean isMaximising) {
        int[] pieces = {1, 2};
        int score = 0;
        int bestScore;
        ArrayList<Integer> emptyPlaces = emptyPositions(newBoard);
        if (isWinning(newBoard, turn)) {
            return 10;
        } else if (isWinning(newBoard, 1 - turn)) {
            return -10;
        } else if (emptyPlaces.isEmpty()) {
            return 0;
        }

        if (isMaximising) {
            bestScore = -10000;
            for (int i = 0; i < emptyPlaces.size(); i++) {
                int move = emptyPlaces.get(i);
                newBoard[move / 3][move % 3] = pieces[turn];
                score = minimax(newBoard, turn, false);
                newBoard[move / 3][move % 3] = 0;
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        } else {
            bestScore = 10000;
            for (int i = 0; i < emptyPlaces.size(); i++) {
                int move = emptyPlaces.get(i);
                newBoard[move / 3][move % 3] = pieces[1 - turn];
                score = minimax(newBoard, turn, true);
                newBoard[move / 3][move % 3] = 0;
                if (score < bestScore) {
                    bestScore = score;
                }
            }
        }
        return bestScore;
    }

    public static int bestMove(int[][] currentState, int turn) {
        int[] pieces = {1, 2};
        int bestScore = -10000;
        int move = -1;
        int score;
        ArrayList<Integer> emptyPlaces = emptyPositions(currentState);
        for (int i = 0; i < emptyPlaces.size(); i++) {
            int eMove = emptyPlaces.get(i);
            currentState[eMove / 3][eMove % 3] = pieces[turn];
            score = minimax(currentState, turn, false);
            currentState[eMove / 3][eMove % 3] = 0;
            if (score > bestScore) {
                bestScore = score;
                move = eMove;
            }
        }
        return move;
    }
}

