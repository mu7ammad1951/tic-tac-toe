package tictactoe;

public class Board {


    private boolean gameOngoing = true;
    private final int[][] gameState = new int[3][3];

    public int[][] getGameState() {
        return gameState.clone();
    }

    public boolean isGameOngoing() {
        return gameOngoing;
    }

    public void setGameOngoing(boolean gameOngoing) {
        this.gameOngoing = gameOngoing;
    }

    /**
     * Display the current state of the board
     */
    public void display() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                if (gameState[i][j] == 0) {
                    System.out.print("  ");
                } else if (gameState[i][j] == 1) {
                    System.out.print("X ");
                } else if (gameState[i][j] == 2) {
                    System.out.print("O ");
                }
            }
            System.out.print("|\n");
        }
        System.out.println("---------");

    }

    /**
     * Makes a move in the given coordinates.
     * @param turn an integer denoting whose turn it is (X or O).
     * @param rowCoordinate integer denoting the row where the move is to be made.
     * @param colCoordinate integer denoting the column where the move is to be made.
     */
    public void makeMove(int turn, int rowCoordinate, int colCoordinate) {

        if (turn == 0)
            gameState[rowCoordinate - 1][colCoordinate - 1] = 1;
        else
            gameState[rowCoordinate - 1][colCoordinate - 1] = 2;

        display();
        checkWinner(turn, rowCoordinate, colCoordinate);

    }

    /**
     * Sets all positions to empty, and prepares for the next game.
     */
    public void wipeBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                gameState[i][j] = 0;
        gameOngoing = true;
    }


    /**
     * Checks if the last move made is a 3 in a row.
     * @param turn Denotes the move made (X or O).
     * @param rowCoordinate Row where the move was made
     * @param colCoordinate Column where the move was made
     */
    public void checkWinner(int turn, int rowCoordinate, int colCoordinate) {
        int winner = 0;
        String whoWon = "";
        //Check to see if X wins
        if (turn == 0) {
            //Checking the row
            for (int c = 0; c < 3; c++) {
                if (gameState[rowCoordinate - 1][c] == 1) {
                    winner += 1;
                }
                if (winner == 3) {
                    whoWon = "X";
                    gameOngoing = false;
                }
            }
            //Checking the column
            winner = 0;
            for (int r = 0; r < 3; r++) {
                if (gameState[r][colCoordinate - 1] == 1) {
                    winner += 1;
                }
                if (winner == 3) {
                    whoWon = "X";
                    gameOngoing = false;
                }
            }
            //Checking the first diagonal
            winner = 0;
            if (rowCoordinate == colCoordinate) {
                for (int r = 0; r < 3; r++) {
                    if (gameState[r][r] == 1)
                        winner += 1;
                    if (winner == 3) {
                        whoWon = "X";
                        gameOngoing = false;
                    }
                }
            }
            winner = 0;
            //Checking the second diagonal
            if (rowCoordinate + colCoordinate == 4) {
                int c = 2;
                for (int r = 0; r < 3; r++) {
                    if (gameState[r][c] == 1) {
                        winner += 1;
                    }
                    c -= 1;
                    if (winner == 3) {
                        whoWon = "X";
                        gameOngoing = false;
                    }
                }
            }

        }
        //Check to see if O wins
        if (turn == 1) {
            //Checking the row
            for (int c = 0; c < 3; c++) {
                if (gameState[rowCoordinate - 1][c] == 2) {
                    winner += 2;
                }
                if (winner == 6) {
                    whoWon = "Y";
                    gameOngoing = false;
                }
            }
            //Checking the column
            winner = 0;
            for (int r = 0; r < 3; r++) {
                if (gameState[r][colCoordinate - 1] == 2) {
                    winner += 2;
                }
                if (winner == 6) {
                    whoWon = "Y";
                    gameOngoing = false;
                }
            }
            //Checking the first diagonal
            winner = 0;
            if (rowCoordinate == colCoordinate) {
                for (int r = 0; r < 3; r++) {
                    if (gameState[r][r] == 2)
                        winner += 2;
                    if (winner == 6) {
                        whoWon = "Y";
                        gameOngoing = false;
                    }
                }
            }
            //Checking the first diagonal
            winner = 0;
            if (rowCoordinate + colCoordinate == 4) {
                int c = 2;
                for (int r = 0; r < 3; r++) {
                    if (gameState[r][c] == 2) {
                        winner += 2;
                    }
                    c -= 1;
                    if (winner == 6) {
                        whoWon = "Y";
                        gameOngoing = false;
                    }
                }
            }
        }
        String wins = whoWon.equals("X") ? "X wins" : whoWon.equals("Y") ? "O wins" : gameOngoing ? "" : "Draw";
        System.out.println(wins);
    }
}
