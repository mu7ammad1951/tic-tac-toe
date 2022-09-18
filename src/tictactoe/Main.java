package tictactoe;

import java.util.*;

public class Main {
    public static List<String> difficulties = Arrays.asList("easy", "medium", "hard");

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            board.wipeBoard();
            int turn = 0;
            int count = 1;

            //Accepting parameters to start or exit and to select the difficulty or play as the user.
            System.out.print("Input command: ");
            String game = scanner.nextLine();
            String[] param = game.split(" ", 3);
            //Check for exit command
            if ("exit".equals(param[0]))
                break;

            //Tests for bad parameters.
            if(param.length < 3){
                System.out.println("Bad Parameters!");
                continue;
            }
            if (!"start".equals(param[0])) {
                System.out.println("Bad Parameters!");
                continue;
            }
            if (!"user".equals(param[1])&&!difficulties.contains(param[1]))
            {
                System.out.println("Bad Parameters!");
                continue;
            }
            if (!"user".equals(param[2])&&!difficulties.contains(param[2])) {
                System.out.println("Bad Parameters!");
                continue;
            }
            //Display an empty board
            board.display();

            while (board.gameOngoing) {
                if (count == 9) {
                    board.gameOngoing = false;
                }

                if (turn == 0)
                    GameBrain.coordinateInput(param[1], board, turn);
                else
                    GameBrain.coordinateInput(param[2], board, turn);
                turn = 1 - turn;
                count++;

            }
        }


    }
}

