package HW03;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Battleship {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); //read keyboard input
        //Declarations
        boolean gameOver = false;
        int gameWinner = 0;
        int player1ShipCount = 5;
        int player2ShipCount = 5;

        char[][] player1Location = new char[5][5];
        // test stubs
/*        char[][] player1Location = { {'@', '-', '-', '-', '-'},
                                     {'-', '@', '-', '-', '-'},
                                     {'-', '-', '@', '-', '-'},
                                     {'-', '-', '-', '@', '-'},
                                     {'-', '-', '-', '-', '@'} };
*/
        char[][] player2Location = new char[5][5];
        //test stubs
/*        char[][] player2Location = { {'@', '-', '-', '-', '-'},
                                     {'-', '@', '-', '-', '-'},
                                     {'-', '-', '@', '-', '-'},
                                     {'-', '-', '-', '@', '-'},
                                     {'-', '-', '-', '-', '@'} };
*/
        char[][] player1TargetHistory = new char[5][5];
        char[][] player2TargetHistory = new char[5][5];

        //init game boards
        initGameBoards(player1Location);
        initGameBoards(player2Location);
        initGameBoards(player1TargetHistory);
        initGameBoards(player2TargetHistory);

        System.out.println("Welcome to Battleship!");
        System.out.println("");


        //collect input
        System.out.println("PLAYER 1, ENTER YOUR SHIPS' COORDINATES.");
        collectShipLocation(player1Location, input);
        printBattleShip(player1Location);
        
        //print 100 new lines to hide board from oppenant
        for (int i = 0; i < 100; i++){
            System.out.println(" ");
        }

        System.out.println("PLAYER 2, ENTER YOUR SHIPS' COORDINATES.");
        collectShipLocation(player2Location, input);        
        printBattleShip(player2Location);

        //print 100 new lines to hide board from oppenant
        for (int i = 0; i < 100; i++){
            System.out.println(" ");
        }    

        //begin game
        char playerNumber = '1';
        do{

            if (playerNumber == '1'){
                //player 1 turn
                if (collectTargetHistory(player1TargetHistory, player2Location, playerNumber, input)){
                    //if return is true then its a hit; reduce playerXshipcount
                    System.out.println("PLAYER 1 HIT PLAYER 2’s SHIP!");

                    player2ShipCount -=1;
                }
                else {//miss
                    System.out.println("PLAYER 1 MISSED!");
                }

                printBattleShip(player1TargetHistory);
                System.out.println("");
                playerNumber = '2';
            }  
            else{
                //player 2 turn
                if (collectTargetHistory(player2TargetHistory, player1Location, playerNumber, input)){
                    //if return is true then its a hit; reduce playerXshipcount
                    System.out.println("PLAYER 2 HIT PLAYER 1’s SHIP!");

                    player1ShipCount -=1;
                }
                else {//miss
                    System.out.println("PLAYER 2 MISSED!");
                }

                printBattleShip(player2TargetHistory);
                System.out.println("");
                playerNumber = '1';
            }

            //check if game winner
            if (player1ShipCount == 0 ){
                gameOver = true;
                gameWinner = 2;
            }
            else if (player2ShipCount == 0){
                gameOver = true;
                gameWinner = 1;
            }

        }while(gameOver == false);

        //game over
        System.out.println("PLAYER " + gameWinner + " WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!");
        System.out.println("");
        System.out.println("Final boards:");
        System.out.println("");

        printBattleShip(player1Location);
        System.out.println("");
        printBattleShip(player2Location);
    }

	private static boolean collectTargetHistory(char[][] playerAttack, char[][] playerDefend, char player, Scanner input) {
        boolean inputValid = false;
        String ERR_MSG_1 = "You already fired on this spot. Choose different coordinates.";
        String ERR_MSG_2 = "Invalid coordinates. Choose different coordinates.";

        //check for ints entered
        do{
//            System.out.println("Enter ship " + ship + " location:");
            System.out.println("Player " + player + ", enter hit row/column:");

            int rowInput = input.nextInt();
            int colInput = input.nextInt();
            if ((rowInput>= 0 && rowInput < 5) && (colInput >= 0 && colInput < 5)){//valid ints
                inputValid = true;
                //check if target previously fired upon
                if (playerAttack[rowInput][colInput] != '-'){
                    System.out.println(ERR_MSG_1);
                    inputValid = false;
                }
                else {
                    //check if hit 'X' or miss 'O'
                    if (playerDefend[rowInput][colInput] == '@'){// its a hit

                        //record target location in history
                        playerAttack[rowInput][colInput] = 'X';

                        //record hit in players board
                        playerDefend[rowInput][colInput] = 'X';

                        return true;// return hit = true
                    }
                    else{ //miss

                        //record target location in history
                        playerAttack[rowInput][colInput] = 'O';

                        //record hit in players board
                        playerDefend[rowInput][colInput] = 'O';
                    }
                }
            }
            else {
                System.out.println(ERR_MSG_2);
                inputValid = false;
            }

        } while (inputValid == false);
        return false;
	}
    
    private static void collectShipLocation(char[][] player, Scanner input) {
        String ERR_MSG_1 = "You already have a ship there. Choose different coordinates.";
        String ERR_MSG_2 = "Invalid coordinates. Choose different coordinates.";
        
        //Get ship location
        for (int ship = 1; ship < 6; ship++){
            Boolean inputValid = false;

            //check for ints entered
            do{
                System.out.println("Enter ship " + ship + " location:");

                int rowInput = input.nextInt();
                int colInput = input.nextInt();
                if ((rowInput>= 0 && rowInput < 5) && (colInput >= 0 && colInput < 5)){//valid ints
                    inputValid = true;
                    //check if ship already exists at location
                    if (player[rowInput][colInput] == '@'){
                        System.out.println(ERR_MSG_1);
                        inputValid = false;
                    }
                    else {//store ship location
                        player[rowInput][colInput] = '@';
                    }
                }
                else {
                    System.out.println(ERR_MSG_2);
                    inputValid = false;
                }

            } while (inputValid == false);
            //printBattleShip(player);
		}
	}
    
    // Use this method to init game boards.
	private static void initGameBoards(char[][] player) {
        //init the arrays
        for (int row = 0; row < 5; row++){
            for (int col = 0; col < 5; col++){
                player[row][col] = '-';
            }
		}
	}

    // Use this method to print game boards to the console.
	private static void printBattleShip(char[][] player) {
		System.out.print("  ");
		for (int row = -1; row < 5; row++) {
			if (row > -1) {
				System.out.print(row + " ");
			}
			for (int column = 0; column < 5; column++) {
				if (row == -1) {
					System.out.print(column + " ");
				} else {
					System.out.print(player[row][column] + " ");
				}
			}
			System.out.println("");
		}
	}

}