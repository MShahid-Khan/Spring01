package com.objava.maven01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Chess {

    private enum Chessmen {
        WHITE_KING, WHITE_QUEEN, WHITE_ROOK, WHITE_BISHOP, WHITE_KNIGHT, WHITE_PAWN, BLACK_KING, BLACK_QUEEN, BLACK_ROOK, BLACK_BISHOP, BLACK_KNIGHT, BLACK_PAWN, EMPTY
    }

    private static Boolean isAutoSave = false;

    public static void populateChessboard(Chessmen[][] aChessboard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (i == 0) {
                    switch (j) {
                        case 0:
                        case 7:
                            aChessboard[i][j] = Chessmen.BLACK_ROOK;
                            break;
                        case 1:
                        case 6:
                            aChessboard[i][j] = Chessmen.BLACK_KNIGHT;
                            break;
                        case 2:
                        case 5:
                            aChessboard[i][j] = Chessmen.BLACK_BISHOP;
                            break;
                        case 3:
                            aChessboard[i][j] = Chessmen.BLACK_QUEEN;
                            break;
                        case 4:
                            aChessboard[i][j] = Chessmen.BLACK_KING;
                            break;
                    }
                } else if (i == 1) {
                    aChessboard[i][j] = Chessmen.BLACK_PAWN;
                } else if (i > 1 && i < 6) {
                    aChessboard[i][j] = Chessmen.EMPTY;
                } else if (i == 6) {
                    aChessboard[i][j] = Chessmen.WHITE_PAWN;
                } else if (i == 7) {
                    switch (j) {
                        case 0:
                        case 7:
                            aChessboard[i][j] = Chessmen.WHITE_ROOK;
                            break;
                        case 1:
                        case 6:
                            aChessboard[i][j] = Chessmen.WHITE_KNIGHT;
                            break;
                        case 2:
                        case 5:
                            aChessboard[i][j] = Chessmen.WHITE_BISHOP;
                            break;
                        case 3:
                            aChessboard[i][j] = Chessmen.WHITE_QUEEN;
                            break;
                        case 4:
                            aChessboard[i][j] = Chessmen.WHITE_KING;
                            break;
                    }
                }
            }

        }

    }

    public static void printBoard(Chessmen[][] aChessboard) {
        char column = 'A';
        System.out.println("\n\n\nWelcome to Spring-001 [Chess by Muhammad Shahid Khan].\n"
                + "To play in \"Auto Save\" mode, please type \"Auto\".\n"
                + "To save game at any stage, please type \"Save\".\n"
                + "To load last saved game at any stage, please type \"Load\".\n"
                + "To close the game, type \"Exit\".\n");
        System.out.print("     ");
        for (int l = 0; l < 8; l++) {
            System.out.print(column + "\u3000\u205F");
            column++;
        }
        System.out.println("\r");

        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + ".  ");

            for (int j = 0; j < 8; j++) {

                switch (aChessboard[i][j]) {

                    case BLACK_PAWN:
                        System.out.print("\u265F\u2002"); // + "         "
                        break;
                    case BLACK_ROOK:
                        System.out.print("\u265C\u2002");
                        break;
                    case BLACK_KNIGHT:
                        System.out.print("\u265E\u2002");
                        break;
                    case BLACK_BISHOP:
                        System.out.print("\u265D\u2002");
                        break;
                    case BLACK_QUEEN:
                        System.out.print("\u265B\u2002");
                        break;
                    case BLACK_KING:
                        System.out.print("\u265A\u2002");
                        break;
                    case WHITE_PAWN:
                        System.out.print("\u2659\u2002");
                        break;
                    case WHITE_ROOK:
                        System.out.print("\u2656\u2002");
                        break;
                    case WHITE_KNIGHT:
                        System.out.print("\u2658\u2002");
                        break;
                    case WHITE_BISHOP:
                        System.out.print("\u2657\u2002");
                        break;
                    case WHITE_QUEEN:
                        System.out.print("\u2655\u2002");
                        break;
                    case WHITE_KING:
                        System.out.print("\u2654\u2002");
                        break;
                    case EMPTY:
                        System.out.print("\u2002\u2002");
                        break;
                }
            }
            System.out.println("\r");
        }
    }

    public static void move(Chessmen[][] aChessboard, String aPiece, String aPosition) {
        //String[] components = aPiece.split(" ");

        // validates user input
        if (aPiece.length() != 2 || aPosition.length() != 2) {
            System.err.println("\rPlease provide valid move!");
        } else if (aPiece.toUpperCase().charAt(0) < 'A' || aPiece.toUpperCase().charAt(0) > 'H'
                || aPiece.toUpperCase().charAt(1) < '1' || aPiece.toUpperCase().charAt(1) > '8') {
            System.err.println("\rPlease provide valid move!");
        } else if (aPosition.toUpperCase().charAt(0) < 'A' || aPosition.toUpperCase().charAt(0) > 'H'
                || aPosition.toUpperCase().charAt(1) < '1' || aPosition.toUpperCase().charAt(1) > '8') {
            System.err.println("\rPlease provide valid move!");
        } else {
            // make the move: replace original position with Chessmen.
            int col = aPiece.toUpperCase().charAt(0) - 65;
            int row = Math.abs(aPiece.charAt(1) - 49 - 7);

            //and place the piece into the new position
            int nCol = aPosition.toUpperCase().charAt(0) - 65;
            int nRow = Math.abs(aPosition.charAt(1) - 49 - 7);

            //System.out.println(col+ " " + row + " " + nCol + " " + nRow);
            if (isValid(aChessboard, row, col, nRow, nCol)) {
                aChessboard[nRow][+nCol] = aChessboard[row][+col];
                aChessboard[row][+col] = Chessmen.EMPTY;
                if (isAutoSave) {
                    saveGame(aChessboard);
                }

            } else {
                System.err.println("Move not allowed.\n");
            }

        }
    }

    public static Boolean isValid(Chessmen[][] aChessboard, int aRow, int aCol, int aNewRow, int aNewCol) {

        switch (aChessboard[aRow][aCol]) {

            case BLACK_PAWN:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_PAWN)
                        && (aNewCol == aCol + 1 || aNewCol == aCol - 1)) {
                    return true;
                } else if (aChessboard[aNewRow][aNewCol] != Chessmen.EMPTY) {
                    return false;
                } else if (aRow + 1 == aNewRow || (aRow == 1 && aRow + 2 == aNewRow)) {
                    return true;
                }
                break;
            case WHITE_PAWN:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_PAWN)
                        && (aNewCol == aCol + 1 || aNewCol == aCol - 1)) {
                    return true;
                } else if (aChessboard[aNewRow][aNewCol] != Chessmen.EMPTY) {
                    return false;
                } else if (aRow - 1 == aNewRow || (aRow == 6 && aRow - 2 == aNewRow)) {
                    return true;
                }
                break;
            case BLACK_ROOK:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && (aNewCol == aCol || aNewRow == aRow)) {
                    return true;
                }
                break;
            case WHITE_ROOK:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && (aNewCol == aCol || aNewRow == aRow)) {
                    return true;
                }
                break;
            case BLACK_KNIGHT:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && ((aNewCol == (aCol + 2) && ((aNewRow == (aRow - 1)) || (aNewRow == (aRow + 1))))
                        || (aNewCol == (aCol - 2) && ((aNewRow == (aRow - 1)) || (aNewRow == (aRow + 1))))
                        || (aNewCol == (aCol + 1) && ((aNewRow == (aRow - 2)) || (aNewRow == (aRow + 2))))
                        || (aNewCol == (aCol - 1) && ((aNewRow == (aRow - 2)) || (aNewRow == (aRow + 2)))))) {
                    return true;
                }
                break;
            case WHITE_KNIGHT:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && ((aNewCol == (aCol + 2) && ((aNewRow == (aRow - 1)) || (aNewRow == (aRow + 1))))
                        || (aNewCol == (aCol - 2) && ((aNewRow == (aRow - 1)) || (aNewRow == (aRow + 1))))
                        || (aNewCol == (aCol + 1) && ((aNewRow == (aRow - 2)) || (aNewRow == (aRow + 2))))
                        || (aNewCol == (aCol - 1) && ((aNewRow == (aRow - 2)) || (aNewRow == (aRow + 2)))))) {
                    return true;
                }
                break;
            case BLACK_BISHOP:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && (Math.abs(aNewCol - aCol) != Math.abs(aNewRow - aRow))) {
                    return true;
                }
                break;
            case WHITE_BISHOP:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && (Math.abs(aNewCol - aCol) != Math.abs(aNewRow - aRow))) {
                    return true;
                }
                break;
            case BLACK_QUEEN:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && ((Math.abs(aNewCol - aCol) != Math.abs(aNewRow - aRow))
                        || (aNewCol == aCol || aNewRow == aRow))) {
                    return true;
                }
                break;
            case WHITE_QUEEN:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && ((Math.abs(aNewCol - aCol) != Math.abs(aNewRow - aRow))
                        || (aNewCol == aCol || aNewRow == aRow))) {
                    return true;
                }
                break;
            case BLACK_KING:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.WHITE_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && ((aNewCol == aCol + 1 || aNewCol == aCol || aNewCol == aCol - 1)
                        && (aNewRow == aRow + 1 || aNewRow == aRow || aNewRow == aRow - 1))) {
                    return true;
                }
                break;
            case WHITE_KING:
                if ((aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_ROOK
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KNIGHT
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_BISHOP
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_QUEEN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_KING
                        || aChessboard[aNewRow][aNewCol] == Chessmen.BLACK_PAWN
                        || aChessboard[aNewRow][aNewCol] == Chessmen.EMPTY)
                        && ((aNewCol == aCol + 1 || aNewCol == aCol || aNewCol == aCol - 1)
                        && (aNewRow == aRow + 1 || aNewRow == aRow || aNewRow == aRow - 1))) {
                    return true;
                }
                break;
            case EMPTY:
                return false;
            default:
                return false;
        }
        return false;

    }

    public static void main(String[] args) {
        Boolean loop = true;
        Scanner sc = new Scanner(System.in);
        Chessmen[][] chessboard = new Chessmen[8][8]; // creates chessboard of size 8 x 8
        String piece = "", toPosition = "";

        try {
            populateChessboard(chessboard);

            while (loop) {
                printBoard(chessboard);
                System.out.println("Piece to move: \n");
                piece = sc.nextLine();
                switch (piece.toLowerCase()) {
                    case "exit":
                        System.exit(0);
                    case "auto":
                        isAutoSave = true;
                        break;
                    case "save":
                        if (!isAutoSave) {
                            saveGame(chessboard);
                        }
                        break;
                    case "load":
                        loadGame(chessboard);
                        break;
                    default:
                        System.out.println("to Position: \n");
                        toPosition = sc.nextLine();
                        switch (toPosition.toLowerCase()) {
                            case "exit":
                                System.exit(0);
                            case "auto":
                                isAutoSave = true;
                                break;
                            case "save":
                                if (!isAutoSave) {
                                    saveGame(chessboard);
                                }
                                break;
                            case "load":
                                loadGame(chessboard);
                                break;
                            default:
                                move(chessboard, piece, toPosition);
                                break;
                        }
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            sc.close();
        }
    }

    public static void saveGame(Chessmen[][] aChessboard) {
        BufferedWriter writer = null;
        try {
            File gameFile = new File("SavedGame.txt");

            writer = new BufferedWriter(new FileWriter(gameFile));
            for (int iOuterIndex = 0; iOuterIndex < 8; iOuterIndex++) {
                for (int iInnerIndex = 0; iInnerIndex < 8; iInnerIndex++) {
                    writer.append(iOuterIndex + "~" + iInnerIndex + "~" + aChessboard[iOuterIndex][iInnerIndex].toString());
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }

    public static void loadGame(Chessmen[][] aChessboard) {
        try {
            File gameFile = new File("SavedGame.txt");
            if (!gameFile.exists()) {
                System.err.println("No saved game found!");
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(gameFile));

            String saveData;
            while ((saveData = br.readLine()) != null) {
                if(saveData.split("~").length == 3)
                aChessboard[Integer.parseInt(saveData.split("~")[0])][Integer.parseInt(saveData.split("~")[1])] = Chessmen.valueOf(saveData.split("~")[2]);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        } 
    }
}
