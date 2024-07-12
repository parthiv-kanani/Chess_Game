package chess;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;
import chess.Chess.Player;

public class Piece {

    private static ReturnPiece[][] savedBoard;
    private static ReturnPiece enPassantTargetCopy, whiteKingCopy, blackKingCopy;

    private Piece() {}

    public static boolean canCapture(PieceType selectedPieceType, PieceType targetPieceType) {
        switch (selectedPieceType) {

            case WP: case WR: case WN: case WB: case WQ: case WK:

                switch (targetPieceType) {
                    case WP: case WR: case WN: case WB: case WQ: case WK:
                        return false;
                    default:
                        return true;
                }

            case BP: case BR: case BN: case BB: case BQ: case BK:

                switch (targetPieceType) {
                    case BP: case BR: case BN: case BB: case BQ: case BK:
                        return false;
                    default:
                        return true;
                }
        }

        return false;
    }

    public static int isChecked(ReturnPiece[][] currentBoard, Player ownColor, int checkFile, int checkRank, boolean ignoreOwnKing) {

        int totalChecks = 0;

        PieceType oppPawn, oppRook, oppKnight, oppBishop, oppQueen, oppKing, ownKing;
        if (ownColor == Player.white) {
            oppPawn = PieceType.BP;
            oppRook = PieceType.BR;
            oppKnight = PieceType.BN;
            oppBishop = PieceType.BB;
            oppQueen = PieceType.BQ;
            oppKing = PieceType.BK;

            ownKing = PieceType.WK;
        } else {
            oppPawn = PieceType.WP;
            oppRook = PieceType.WR;
            oppKnight = PieceType.WN;
            oppBishop = PieceType.WB;
            oppQueen = PieceType.WQ;
            oppKing = PieceType.WK;

            ownKing = PieceType.BK;
        }

        for (int r = checkRank - 1; r >= 0; r--) { 
            if (currentBoard[checkFile][r] != null && !((ignoreOwnKing) && (currentBoard[checkFile][r].pieceType == ownKing))) {
                if ((currentBoard[checkFile][r].pieceType == oppRook) || (currentBoard[checkFile][r].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        for (int r = checkRank + 1; r <= 7; r++) { 
            if (currentBoard[checkFile][r] != null && !((ignoreOwnKing) && (currentBoard[checkFile][r].pieceType == ownKing))) {
                if ((currentBoard[checkFile][r].pieceType == oppRook) || (currentBoard[checkFile][r].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        for (int f = checkFile + 1; f <= 7; f++) { 
            if (currentBoard[f][checkRank] != null && !((ignoreOwnKing) && (currentBoard[f][checkRank].pieceType == ownKing))) {
                if ((currentBoard[f][checkRank].pieceType == oppRook) || (currentBoard[f][checkRank].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        for (int f = checkFile - 1; f >= 0; f--) { 
            if (currentBoard[f][checkRank] != null && !((ignoreOwnKing) && (currentBoard[f][checkRank].pieceType == ownKing))) {
                if ((currentBoard[f][checkRank].pieceType == oppRook) || (currentBoard[f][checkRank].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        for (int i = 1; ((checkFile + i) <= 7) && ((checkRank - i) >= 0); i++) { 
            if (currentBoard[checkFile + i][checkRank - i] != null && !((ignoreOwnKing) && (currentBoard[checkFile + i][checkRank - i].pieceType == ownKing))) {
                if ((currentBoard[checkFile + i][checkRank - i].pieceType == oppBishop) || (currentBoard[checkFile + i][checkRank - i].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        for (int i = 1; ((checkFile - i) >= 0) && ((checkRank - i) >= 0); i++) { 
            if (currentBoard[checkFile - i][checkRank - i] != null && !((ignoreOwnKing) && (currentBoard[checkFile - i][checkRank - i].pieceType == ownKing))) {
                if ((currentBoard[checkFile - i][checkRank - i].pieceType == oppBishop) || (currentBoard[checkFile - i][checkRank - i].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        for (int i = 1; ((checkFile + i) <= 7) && ((checkRank + i) <= 7); i++) { 
            if (currentBoard[checkFile + i][checkRank + i] != null && !((ignoreOwnKing) && (currentBoard[checkFile + i][checkRank + i].pieceType == ownKing))) {
                if ((currentBoard[checkFile + i][checkRank + i].pieceType == oppBishop) || (currentBoard[checkFile + i][checkRank + i].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        for (int i = 1; ((checkFile - i) >= 0) && ((checkRank + i) <= 7); i++) { 
            if (currentBoard[checkFile - i][checkRank + i] != null && !((ignoreOwnKing) && (currentBoard[checkFile - i][checkRank + i].pieceType == ownKing))) {
                if ((currentBoard[checkFile - i][checkRank + i].pieceType == oppBishop) || (currentBoard[checkFile - i][checkRank + i].pieceType == oppQueen)) {
                    totalChecks++;
                }
                break;
            }
        }

        if (((checkFile + 1) <= 7) && ((checkRank - 2) >= 0) && 
            (currentBoard[checkFile + 1][checkRank - 2] != null ) && 
            (currentBoard[checkFile + 1][checkRank - 2].pieceType == oppKnight)) 
                totalChecks++; 
        if (((checkFile - 1) >= 0) && ((checkRank - 2) >= 0) && 
            (currentBoard[checkFile - 1][checkRank - 2] != null ) && 
            (currentBoard[checkFile - 1][checkRank - 2].pieceType == oppKnight)) 
                totalChecks++; 
        if (((checkFile + 1) <= 7) && ((checkRank + 2) <= 7) && 
            (currentBoard[checkFile + 1][checkRank + 2] != null ) && 
            (currentBoard[checkFile + 1][checkRank + 2].pieceType == oppKnight)) 
                totalChecks++; 
        if (((checkFile - 1) >= 0) && ((checkRank + 2) <= 7) && 
            (currentBoard[checkFile - 1][checkRank + 2] != null ) && 
            (currentBoard[checkFile - 1][checkRank + 2].pieceType == oppKnight)) 
                totalChecks++; 
        if (((checkFile + 2) <= 7) && ((checkRank - 1) >= 0) && 
            (currentBoard[checkFile + 2][checkRank - 1] != null ) && 
            (currentBoard[checkFile + 2][checkRank - 1].pieceType == oppKnight)) 
                totalChecks++; 
        if (((checkFile - 2) >= 0) && ((checkRank - 1) >= 0) && 
            (currentBoard[checkFile - 2][checkRank - 1] != null ) && 
            (currentBoard[checkFile - 2][checkRank - 1].pieceType == oppKnight)) 
                totalChecks++; 
        if (((checkFile + 2) <= 7) && ((checkRank + 1) <= 7) && 
            (currentBoard[checkFile + 2][checkRank + 1] != null ) && 
            (currentBoard[checkFile + 2][checkRank + 1].pieceType == oppKnight)) 
                totalChecks++; 
        if (((checkFile - 2) >= 0) && ((checkRank + 1) <= 7) && 
            (currentBoard[checkFile - 2][checkRank + 1] != null ) && 
            (currentBoard[checkFile - 2][checkRank + 1].pieceType == oppKnight)) 
                totalChecks++; 

        if (oppPawn == PieceType.BP) { 
            if (((checkFile + 1) <= 7) && ((checkRank - 1) >= 0) && 
                (currentBoard[checkFile + 1][checkRank - 1] != null ) && 
                (currentBoard[checkFile + 1][checkRank - 1].pieceType == oppPawn)) 
                    totalChecks++; 
            if (((checkFile - 1) >= 0) && ((checkRank - 1) >= 0) && 
                (currentBoard[checkFile - 1][checkRank - 1] != null ) && 
                (currentBoard[checkFile - 1][checkRank - 1].pieceType == oppPawn)) 
                    totalChecks++; 
        } else {
            if (((checkFile + 1) <= 7) && ((checkRank + 1) <= 7) && 
                (currentBoard[checkFile + 1][checkRank + 1] != null ) && 
                (currentBoard[checkFile + 1][checkRank + 1].pieceType == oppPawn)) 
                    totalChecks++; 
            if (((checkFile - 1) >= 0) && ((checkRank + 1) <= 7) && 
                (currentBoard[checkFile - 1][checkRank + 1] != null ) && 
                (currentBoard[checkFile - 1][checkRank + 1].pieceType == oppPawn)) 
                    totalChecks++; 
        }

        for (int r = -1; r < 2; r++) { 
            boolean calledBreak = false;
            if (!((checkRank + r) >= 0) || !((checkRank + r) <= 7)) continue;
            for (int f = -1; f < 2; f++) { 
                if ((r == 0) && (f == 0)) continue; 
                if (!((checkFile + f) >= 0) || !((checkFile + f) <= 7)) continue;
                if (currentBoard[checkFile + f][checkRank + r] == null) continue;

                if (currentBoard[checkFile + f][checkRank + r].pieceType == oppKing) {
                    totalChecks++;
                    calledBreak = true;
                    break;
                }
            }

            if (calledBreak) break;
        }

        return totalChecks;
    }

    private static ReturnPiece copyPiece (ReturnPiece originalPiece) {
        Rook rookCopy;
        King kingCopy;

        switch (originalPiece.pieceType) {

            case WP:
                return new Pawn(Player.white, originalPiece.pieceFile, originalPiece.pieceRank);
            case WR:
                rookCopy = new Rook(Player.white, originalPiece.pieceFile, originalPiece.pieceRank);
                rookCopy.canCastle = ((Rook) originalPiece).canCastle;
                return rookCopy;
            case WN:
                return new Knight(Player.white, originalPiece.pieceFile, originalPiece.pieceRank);
            case WB:
                return new Bishop(Player.white, originalPiece.pieceFile, originalPiece.pieceRank);
            case WQ:
                return new Queen(Player.white, originalPiece.pieceFile, originalPiece.pieceRank);
            case WK:
                kingCopy = new King(Player.white, originalPiece.pieceFile, originalPiece.pieceRank);
                kingCopy.canCastle = ((King) originalPiece).canCastle;
                return kingCopy;

            case BP:
                return new Pawn(Player.black, originalPiece.pieceFile, originalPiece.pieceRank);
            case BR:
                rookCopy = new Rook(Player.black, originalPiece.pieceFile, originalPiece.pieceRank);
                rookCopy.canCastle = ((Rook) originalPiece).canCastle;
                return rookCopy;
            case BN:
                return new Knight(Player.black, originalPiece.pieceFile, originalPiece.pieceRank);
            case BB:
                return new Bishop(Player.black, originalPiece.pieceFile, originalPiece.pieceRank);
            case BQ:
                return new Queen(Player.black, originalPiece.pieceFile, originalPiece.pieceRank);
            case BK:
                kingCopy = new King(Player.black, originalPiece.pieceFile, originalPiece.pieceRank);
                kingCopy.canCastle = ((King) originalPiece).canCastle;
                return kingCopy;
        }

        return new ReturnPiece();
    }

    public static void saveBoard(ReturnPiece[][] currentBoard) {
        savedBoard = new ReturnPiece[8][8];

        for (int f = 0; f < 8; f++) {
            for (int r = 0; r < 8; r++) {
                if (currentBoard[f][r] == null) continue;

                savedBoard[f][r] = copyPiece(currentBoard[f][r]);
            }
        }

        int targetFile, targetRank;

        if (Pawn.enPassantTarget != null) {
            targetFile = Chess.getFile(Pawn.enPassantTarget.pieceFile);
            targetRank = Chess.getRank(Pawn.enPassantTarget.pieceRank);
            enPassantTargetCopy = savedBoard[targetFile][targetRank];
        } else enPassantTargetCopy = null;

        if (King.whiteKing != null) {
            targetFile = Chess.getFile(King.whiteKing.pieceFile);
            targetRank = Chess.getRank(King.whiteKing.pieceRank);
            whiteKingCopy = savedBoard[targetFile][targetRank];
        } else whiteKingCopy = null;

        if (King.blackKing != null) {
            targetFile = Chess.getFile(King.blackKing.pieceFile);
            targetRank = Chess.getRank(King.blackKing.pieceRank);
            blackKingCopy = savedBoard[targetFile][targetRank];
        } else blackKingCopy = null;
    }

    public static void revertBoard(ReturnPiece[][] currentBoard) {

        for (int f = 0; f < 8; f++) {
            for (int r = 0; r < 8; r++) { 

                currentBoard[f][r] = null;

                if (savedBoard[f][r] == null) continue;

                currentBoard[f][r] = copyPiece(savedBoard[f][r]);
            }
        }

        int targetFile, targetRank;

        if (enPassantTargetCopy != null) {
            targetFile = Chess.getFile(enPassantTargetCopy.pieceFile);
            targetRank = Chess.getRank(enPassantTargetCopy.pieceRank);
            Pawn.enPassantTarget = currentBoard[targetFile][targetRank];
        } else Pawn.enPassantTarget = null;

        if (whiteKingCopy != null) {
            targetFile = Chess.getFile(whiteKingCopy.pieceFile);
            targetRank = Chess.getRank(whiteKingCopy.pieceRank);
            King.whiteKing = currentBoard[targetFile][targetRank];
        } else King.whiteKing = null;

        if (blackKingCopy != null) {
            targetFile = Chess.getFile(blackKingCopy.pieceFile);
            targetRank = Chess.getRank(blackKingCopy.pieceRank);
            King.blackKing = currentBoard[targetFile][targetRank];
        } else King.blackKing = null;
    }

    public static boolean isCheckmate (ReturnPiece[][] currentBoard, Player ownColor) {

        ReturnPiece ownKing = (ownColor == Player.white) ? King.whiteKing : King.blackKing;
        int ownKingFile = Chess.getFile(ownKing.pieceFile);
        int ownKingRank = Chess.getRank(ownKing.pieceRank);

        for (int r = -1; r < 2; r++) { 
            if (!((ownKingRank + r) >= 0) || !((ownKingRank + r) <= 7)) continue;
            for (int f = -1; f < 2; f++) { 
                if ((r == 0) && (f == 0)) continue; 
                if (!((ownKingFile + f) >= 0) || !((ownKingFile + f) <= 7)) continue;

                if ((currentBoard[ownKingFile + f][ownKingRank + r] != null) && 
                    (!canCapture(ownKing.pieceType, currentBoard[ownKingFile + f][ownKingRank + r].pieceType))) continue;

                if (isChecked(currentBoard, ownColor, ownKingFile + f, ownKingRank + r, true) > 0) continue;
                return false;
            }
        }

        if (isChecked(currentBoard, ownColor, ownKingFile, ownKingRank, false) > 1) return true;

        ReturnPiece checkingPiece = getCheckingPiece(currentBoard, ownColor, ownKingFile, ownKingRank, false);

        Player oppositeColor = (ownColor == Player.white) ? Player.black : Player.white;
        int checkingPieceFile = Chess.getFile(checkingPiece.pieceFile);
        int checkingPieceRank = Chess.getRank(checkingPiece.pieceRank);
        int counterAttacks = isChecked(currentBoard, oppositeColor, checkingPieceFile, checkingPieceRank, false);
        ReturnPiece counterPiece;

        if (counterAttacks > 0) {
            if (counterAttacks > 2) return false;
            counterPiece = getCheckingPiece(currentBoard, oppositeColor, checkingPieceFile, checkingPieceRank, false);

            Piece.saveBoard(currentBoard);

            int counterPieceFile = Chess.getFile(counterPiece.pieceFile);
            int counterPieceRank = Chess.getRank(counterPiece.pieceRank);
            currentBoard[checkingPieceFile][checkingPieceRank] = counterPiece;
            currentBoard[counterPieceFile][counterPieceRank] = null;

            if (isChecked(currentBoard, ownColor, checkingPieceFile, checkingPieceRank, false) < 1) {
                Piece.revertBoard(currentBoard);
                return false;
            }

            Piece.revertBoard(currentBoard);
        }

        if (checkingPiece.pieceType == PieceType.BP) { 
            if (checkingPiece.equals(Pawn.enPassantTarget)) {
                if (((checkingPieceFile + 1) <= 7) && 
                    (currentBoard[checkingPieceFile + 1][checkingPieceRank] != null) && 
                    (currentBoard[checkingPieceFile + 1][checkingPieceRank].pieceType == PieceType.WP)) {

                        for (int i = 2; ((checkingPieceFile + i) <= 7) && ((checkingPieceRank - i) >= 0); i++) { 
                            if (currentBoard[checkingPieceFile + i][checkingPieceRank - i] != null) {
                                if ((currentBoard[checkingPieceFile + i][checkingPieceRank - i].pieceType == PieceType.BB) || 
                                    (currentBoard[checkingPieceFile + i][checkingPieceRank - i].pieceType == PieceType.BQ)) {
                                        return true;
                                }
                                break;
                            }
                        }

                        return false; 
                    }
                if (((checkingPieceFile - 1) >= 0) &&
                    (currentBoard[checkingPieceFile - 1][checkingPieceRank] != null) && 
                    (currentBoard[checkingPieceFile - 1][checkingPieceRank].pieceType == PieceType.WP)) {

                        for (int i = 2; ((checkingPieceFile - i) >= 0) && ((checkingPieceRank - i) >= 0); i++) { 
                            if (currentBoard[checkingPieceFile - i][checkingPieceRank - i] != null) {
                                if ((currentBoard[checkingPieceFile - i][checkingPieceRank - i].pieceType == PieceType.BB) || 
                                    (currentBoard[checkingPieceFile - i][checkingPieceRank - i].pieceType == PieceType.BQ)) {
                                        return true;
                                }
                                break;
                            }
                        }

                        return false; 
                    }
            }
        } else if (checkingPiece.pieceType == PieceType.WP) { 
            if (((checkingPieceFile + 1) <= 7) && 
                    (currentBoard[checkingPieceFile + 1][checkingPieceRank] != null) && 
                    (currentBoard[checkingPieceFile + 1][checkingPieceRank].pieceType == PieceType.BP)) {

                        for (int i = 2; ((checkingPieceFile + i) <= 7) && ((checkingPieceRank + i) <= 7); i++) { 
                            if (currentBoard[checkingPieceFile + i][checkingPieceRank + i] != null) {
                                if ((currentBoard[checkingPieceFile + i][checkingPieceRank + i].pieceType == PieceType.BB) || 
                                    (currentBoard[checkingPieceFile + i][checkingPieceRank + i].pieceType == PieceType.BQ)) {
                                        return true;
                                }
                                break;
                            }
                        }

                        return false; 
                    }
                if (((checkingPieceFile - 1) >= 0) &&
                    (currentBoard[checkingPieceFile - 1][checkingPieceRank] != null) && 
                    (currentBoard[checkingPieceFile - 1][checkingPieceRank].pieceType == PieceType.BP)) {

                        for (int i = 2; ((checkingPieceFile - i) >= 0) && ((checkingPieceRank + i) <= 7); i++) { 
                            if (currentBoard[checkingPieceFile - i][checkingPieceRank + i] != null) {
                                if ((currentBoard[checkingPieceFile - i][checkingPieceRank + i].pieceType == PieceType.BB) || 
                                    (currentBoard[checkingPieceFile - i][checkingPieceRank + i].pieceType == PieceType.BQ)) {
                                        return true;
                                }
                                break;
                            }
                        }

                        return false; 
                    }
        }

        if ((checkingPieceFile == ownKingFile) && (checkingPieceRank < ownKingRank)) { 
            for (int r = ownKingRank - 1; r > checkingPieceRank; r--) {
                if (canBeReached(currentBoard, ownColor, ownKingFile, r)) return false;
            }
        } else if ((checkingPieceFile == ownKingFile) && (checkingPieceRank > ownKingRank)) { 
            for (int r = ownKingRank + 1; r < checkingPieceRank; r++) {
                if (canBeReached(currentBoard, ownColor, ownKingFile, r)) return false;
            }
        } else if ((checkingPieceFile > ownKingFile) && (checkingPieceRank == ownKingRank)) { 
            for (int f = ownKingFile + 1; f < checkingPieceRank; f++) {
                if (canBeReached(currentBoard, ownColor, f, ownKingRank)) return false;
            }
        } else if ((checkingPieceFile < ownKingFile) && (checkingPieceRank == ownKingRank)) { 
            for (int f = ownKingFile - 1; f > checkingPieceRank; f--) {
                if (canBeReached(currentBoard, ownColor, f, ownKingRank)) return false;
            }
        } else if ((checkingPieceFile > ownKingFile) && (checkingPieceRank < ownKingRank)) { 
            for (int i = 1; (((ownKingFile + i) < checkingPieceFile) && ((ownKingRank - i) > checkingPieceRank)); i++) {
                if (canBeReached(currentBoard, ownColor, ownKingFile + i, ownKingRank - i)) return false;
            }
        } else if ((checkingPieceFile > ownKingFile) && (checkingPieceRank > ownKingRank)) { 
            for (int i = 1; (((ownKingFile + i) < checkingPieceFile) && ((ownKingRank + i) < checkingPieceRank)); i++) {
                if (canBeReached(currentBoard, ownColor, ownKingFile + i, ownKingRank + i)) return false;
            }
        } else if ((checkingPieceFile < ownKingFile) && (checkingPieceRank < ownKingRank)) { 
            for (int i = 1; (((ownKingFile - i) > checkingPieceFile) && ((ownKingRank - i) > checkingPieceRank)); i++) {
                if (canBeReached(currentBoard, ownColor, ownKingFile - i, ownKingRank - i)) return false;
            }
        } else if ((checkingPieceFile < ownKingFile) && (checkingPieceRank > ownKingRank)) { 
            for (int i = 1; (((ownKingFile - i) > checkingPieceFile) && ((ownKingRank + i) < checkingPieceRank)); i++) {
                if (canBeReached(currentBoard, ownColor, ownKingFile - i, ownKingRank + i)) return false;
            }
        }

        return true;
    }

    private static ReturnPiece getCheckingPiece(ReturnPiece[][] currentBoard, Player ownColor, int checkFile, int checkRank, boolean ignoreOwnKing) {

        PieceType oppPawn, oppRook, oppKnight, oppBishop, oppQueen, oppKing, ownKing;
        if (ownColor == Player.white) {
            oppPawn = PieceType.BP;
            oppRook = PieceType.BR;
            oppKnight = PieceType.BN;
            oppBishop = PieceType.BB;
            oppQueen = PieceType.BQ;
            oppKing = PieceType.BK;

            ownKing = PieceType.WK;
        } else {
            oppPawn = PieceType.WP;
            oppRook = PieceType.WR;
            oppKnight = PieceType.WN;
            oppBishop = PieceType.WB;
            oppQueen = PieceType.WQ;
            oppKing = PieceType.WK;

            ownKing = PieceType.BK;
        }

        for (int r = checkRank - 1; r >= 0; r--) { 
            if (currentBoard[checkFile][r] != null && !((ignoreOwnKing) && (currentBoard[checkFile][r].pieceType == ownKing))) {
                if ((currentBoard[checkFile][r].pieceType == oppRook) || (currentBoard[checkFile][r].pieceType == oppQueen)) {
                    return currentBoard[checkFile][r];
                }
                break;}}

        for (int r = checkRank + 1; r <= 7; r++) { 
            if (currentBoard[checkFile][r] != null && !((ignoreOwnKing) && (currentBoard[checkFile][r].pieceType == ownKing))) {
                if ((currentBoard[checkFile][r].pieceType == oppRook) || (currentBoard[checkFile][r].pieceType == oppQueen)) {
                    return currentBoard[checkFile][r];
                }
                break;}}

        for (int f = checkFile + 1; f <= 7; f++) { 
            if (currentBoard[f][checkRank] != null && !((ignoreOwnKing) && (currentBoard[f][checkRank].pieceType == ownKing))) {
                if ((currentBoard[f][checkRank].pieceType == oppRook) || (currentBoard[f][checkRank].pieceType == oppQueen)) {
                    return currentBoard[f][checkRank];
                }
                break;}}

        for (int f = checkFile - 1; f >= 0; f--) { 
            if (currentBoard[f][checkRank] != null && !((ignoreOwnKing) && (currentBoard[f][checkRank].pieceType == ownKing))) {
                if ((currentBoard[f][checkRank].pieceType == oppRook) || (currentBoard[f][checkRank].pieceType == oppQueen)) {
                    return currentBoard[f][checkRank];
                }
                break;}}

        for (int i = 1; ((checkFile + i) <= 7) && ((checkRank - i) >= 0); i++) { 
            if (currentBoard[checkFile + i][checkRank - i] != null && !((ignoreOwnKing) && (currentBoard[checkFile + i][checkRank - i].pieceType == ownKing))) {
                if ((currentBoard[checkFile + i][checkRank - i].pieceType == oppBishop) || (currentBoard[checkFile + i][checkRank - i].pieceType == oppQueen)) {
                    return currentBoard[checkFile + i][checkRank - i];
                }
                break;}}

        for (int i = 1; ((checkFile - i) >= 0) && ((checkRank - i) >= 0); i++) { 
            if (currentBoard[checkFile - i][checkRank - i] != null && !((ignoreOwnKing) && (currentBoard[checkFile - i][checkRank - i].pieceType == ownKing))) {
                if ((currentBoard[checkFile - i][checkRank - i].pieceType == oppBishop) || (currentBoard[checkFile - i][checkRank - i].pieceType == oppQueen)) {
                    return currentBoard[checkFile - i][checkRank - i];
                }
                break;}}

        for (int i = 1; ((checkFile + i) <= 7) && ((checkRank + i) <= 7); i++) { 
            if (currentBoard[checkFile + i][checkRank + i] != null && !((ignoreOwnKing) && (currentBoard[checkFile + i][checkRank + i].pieceType == ownKing))) {
                if ((currentBoard[checkFile + i][checkRank + i].pieceType == oppBishop) || (currentBoard[checkFile + i][checkRank + i].pieceType == oppQueen)) {
                    return currentBoard[checkFile + i][checkRank + i];
                }
                break;}}

        for (int i = 1; ((checkFile - i) >= 0) && ((checkRank + i) <= 7); i++) { 
            if (currentBoard[checkFile - i][checkRank + i] != null && !((ignoreOwnKing) && (currentBoard[checkFile - i][checkRank + i].pieceType == ownKing))) {
                if ((currentBoard[checkFile - i][checkRank + i].pieceType == oppBishop) || (currentBoard[checkFile - i][checkRank + i].pieceType == oppQueen)) {
                    return currentBoard[checkFile - i][checkRank + i];
                }
                break;}}


        if (((checkFile + 1) <= 7) && ((checkRank - 2) >= 0) && 
            (currentBoard[checkFile + 1][checkRank - 2] != null ) && 
            (currentBoard[checkFile + 1][checkRank - 2].pieceType == oppKnight)) 
                return currentBoard[checkFile + 1][checkRank - 2]; 
        if (((checkFile - 1) >= 0) && ((checkRank - 2) >= 0) && 
            (currentBoard[checkFile - 1][checkRank - 2] != null ) && 
            (currentBoard[checkFile - 1][checkRank - 2].pieceType == oppKnight)) 
                return currentBoard[checkFile - 1][checkRank - 2]; 
        if (((checkFile + 1) <= 7) && ((checkRank + 2) <= 7) && 
            (currentBoard[checkFile + 1][checkRank + 2] != null ) && 
            (currentBoard[checkFile + 1][checkRank + 2].pieceType == oppKnight)) 
                return currentBoard[checkFile + 1][checkRank + 2]; 
        if (((checkFile - 1) >= 0) && ((checkRank + 2) <= 7) && 
            (currentBoard[checkFile - 1][checkRank + 2] != null ) && 
            (currentBoard[checkFile - 1][checkRank + 2].pieceType == oppKnight)) 
                return currentBoard[checkFile - 1][checkRank + 2]; 
        if (((checkFile + 2) <= 7) && ((checkRank - 1) >= 0) && 
            (currentBoard[checkFile + 2][checkRank - 1] != null ) && 
            (currentBoard[checkFile + 2][checkRank - 1].pieceType == oppKnight)) 
                return currentBoard[checkFile + 2][checkRank - 1]; 
        if (((checkFile - 2) >= 0) && ((checkRank - 1) >= 0) && 
            (currentBoard[checkFile - 2][checkRank - 1] != null ) && 
            (currentBoard[checkFile - 2][checkRank - 1].pieceType == oppKnight)) 
                return currentBoard[checkFile - 2][checkRank - 1]; 
        if (((checkFile + 2) <= 7) && ((checkRank + 1) <= 7) && 
            (currentBoard[checkFile + 2][checkRank + 1] != null ) && 
            (currentBoard[checkFile + 2][checkRank + 1].pieceType == oppKnight)) 
                return currentBoard[checkFile + 2][checkRank + 1]; 
        if (((checkFile - 2) >= 0) && ((checkRank + 1) <= 7) && 
            (currentBoard[checkFile - 2][checkRank + 1] != null ) && 
            (currentBoard[checkFile - 2][checkRank + 1].pieceType == oppKnight)) 
                return currentBoard[checkFile - 2][checkRank + 1]; 

        if (oppPawn == PieceType.BP) { 
            if (((checkFile + 1) <= 7) && ((checkRank - 1) >= 0) && 
                (currentBoard[checkFile + 1][checkRank - 1] != null ) && 
                (currentBoard[checkFile + 1][checkRank - 1].pieceType == oppPawn)) 
                    return currentBoard[checkFile + 1][checkRank - 1]; 
            if (((checkFile - 1) >= 0) && ((checkRank - 1) >= 0) && 
                (currentBoard[checkFile - 1][checkRank - 1] != null ) && 
                (currentBoard[checkFile - 1][checkRank - 1].pieceType == oppPawn)) 
                    return currentBoard[checkFile - 1][checkRank - 1]; 
        } else {
            if (((checkFile + 1) <= 7) && ((checkRank + 1) <= 7) && 
                (currentBoard[checkFile + 1][checkRank + 1] != null ) && 
                (currentBoard[checkFile + 1][checkRank + 1].pieceType == oppPawn)) 
                    return currentBoard[checkFile + 1][checkRank + 1]; 
            if (((checkFile - 1) >= 0) && ((checkRank + 1) <= 7) && 
                (currentBoard[checkFile - 1][checkRank + 1] != null ) && 
                (currentBoard[checkFile - 1][checkRank + 1].pieceType == oppPawn)) 
                    return currentBoard[checkFile - 1][checkRank + 1]; 
        }

        for (int r = -1; r < 2; r++) { 
            if (!((checkRank + r) >= 0) || !((checkRank + r) <= 7)) continue;
            for (int f = -1; f < 2; f++) { 
                if ((r == 0) && (f == 0)) continue; 
                if (!((checkFile + f) >= 0) || !((checkFile + f) <= 7)) continue;
                if (currentBoard[checkFile + f][checkRank + r] == null) continue;

                if (currentBoard[checkFile + f][checkRank + r].pieceType == oppKing) {
                    return currentBoard[checkFile + f][checkRank + r];
                }
            }
        }

        return null; 
    }

    private static boolean canBeReached(ReturnPiece[][] currentBoard, Player ownColor, int targetFile, int targetRank) {

        PieceType ownPawn, ownRook, ownKnight, ownBishop, ownQueen;
        if (ownColor == Player.white) {
            ownPawn = PieceType.WP;
            ownRook = PieceType.WR;
            ownKnight = PieceType.WN;
            ownBishop = PieceType.WB;
            ownQueen = PieceType.WQ;
        } else {
            ownPawn = PieceType.BP;
            ownRook = PieceType.BR;
            ownKnight = PieceType.BN;
            ownBishop = PieceType.BB;
            ownQueen = PieceType.BQ;
        }

        for (int r = targetRank - 1; r >= 0; r--) { 
            if (currentBoard[targetFile][r] != null) {
                if ((currentBoard[targetFile][r].pieceType == ownRook) || (currentBoard[targetFile][r].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        for (int r = targetRank + 1; r <= 7; r++) { 
            if (currentBoard[targetFile][r] != null) {
                if ((currentBoard[targetFile][r].pieceType == ownRook) || (currentBoard[targetFile][r].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        for (int f = targetFile + 1; f <= 7; f++) { 
            if (currentBoard[f][targetRank] != null) {
                if ((currentBoard[f][targetRank].pieceType == ownRook) || (currentBoard[f][targetRank].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        for (int f = targetFile - 1; f >= 0; f--) { 
            if (currentBoard[f][targetRank] != null) {
                if ((currentBoard[f][targetRank].pieceType == ownRook) || (currentBoard[f][targetRank].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        for (int i = 1; ((targetFile + i) <= 7) && ((targetRank - i) >= 0); i++) { 
            if (currentBoard[targetFile + i][targetRank - i] != null) {
                if ((currentBoard[targetFile + i][targetRank - i].pieceType == ownBishop) || (currentBoard[targetFile + i][targetRank - i].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        for (int i = 1; ((targetFile - i) >= 0) && ((targetRank - i) >= 0); i++) { 
           if (currentBoard[targetFile - i][targetRank - i] != null) {
                if ((currentBoard[targetFile - i][targetRank - i].pieceType == ownBishop) || (currentBoard[targetFile - i][targetRank - i].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        for (int i = 1; ((targetFile + i) <= 7) && ((targetRank + i) <= 7); i++) { 
            if (currentBoard[targetFile + i][targetRank + i] != null) {
                if ((currentBoard[targetFile + i][targetRank + i].pieceType == ownBishop) || (currentBoard[targetFile + i][targetRank + i].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        for (int i = 1; ((targetFile - i) >= 0) && ((targetRank + i) <= 7); i++) { 
            if (currentBoard[targetFile - i][targetRank + i] != null) {
                if ((currentBoard[targetFile - i][targetRank + i].pieceType == ownBishop) || (currentBoard[targetFile - i][targetRank + i].pieceType == ownQueen)) {
                    return true;
                }
                break;
            }
        }

        if (((targetFile + 1) <= 7) && ((targetRank - 2) >= 0) && 
            (currentBoard[targetFile + 1][targetRank - 2] != null ) && 
            (currentBoard[targetFile + 1][targetRank - 2].pieceType == ownKnight)) 
                return true; 
        if (((targetFile - 1) >= 0) && ((targetRank - 2) >= 0) && 
            (currentBoard[targetFile - 1][targetRank - 2] != null ) && 
            (currentBoard[targetFile - 1][targetRank - 2].pieceType == ownKnight)) 
                return true; 
        if (((targetFile + 1) <= 7) && ((targetRank + 2) <= 7) && 
            (currentBoard[targetFile + 1][targetRank + 2] != null ) && 
            (currentBoard[targetFile + 1][targetRank + 2].pieceType == ownKnight)) 
                return true; 
        if (((targetFile - 1) >= 0) && ((targetRank + 2) <= 7) && 
            (currentBoard[targetFile - 1][targetRank + 2] != null ) && 
            (currentBoard[targetFile - 1][targetRank + 2].pieceType == ownKnight)) 
                return true; 
        if (((targetFile + 2) <= 7) && ((targetRank - 1) >= 0) && 
            (currentBoard[targetFile + 2][targetRank - 1] != null ) && 
            (currentBoard[targetFile + 2][targetRank - 1].pieceType == ownKnight)) 
                return true; 
        if (((targetFile - 2) >= 0) && ((targetRank - 1) >= 0) && 
            (currentBoard[targetFile - 2][targetRank - 1] != null ) && 
            (currentBoard[targetFile - 2][targetRank - 1].pieceType == ownKnight)) 
                return true; 
        if (((targetFile + 2) <= 7) && ((targetRank + 1) <= 7) && 
            (currentBoard[targetFile + 2][targetRank + 1] != null ) && 
            (currentBoard[targetFile + 2][targetRank + 1].pieceType == ownKnight)) 
                return true; 
        if (((targetFile - 2) >= 0) && ((targetRank + 1) <= 7) && 
            (currentBoard[targetFile - 2][targetRank + 1] != null ) && 
            (currentBoard[targetFile - 2][targetRank + 1].pieceType == ownKnight)) 
                return true; 

        if (ownPawn == PieceType.BP) { 
            if (((targetRank - 1) >= 0) && 
                (currentBoard[targetFile][targetRank - 1] != null ) && 
                (currentBoard[targetFile][targetRank - 1].pieceType == ownPawn)) 
                    return true; 
            if (((targetRank - 2) == 1) && 
                (currentBoard[targetFile][targetRank - 2] != null ) && 
                (currentBoard[targetFile][targetRank - 2].pieceType == ownPawn)) 
                    return true; 
        } else {
            if (((targetRank + 1) >= 0) && 
                (currentBoard[targetFile][targetRank + 1] != null ) && 
                (currentBoard[targetFile][targetRank + 1].pieceType == ownPawn)) 
                    return true; 
            if (((targetRank + 2) == 6) && 
                (currentBoard[targetFile][targetRank + 2] != null ) && 
                (currentBoard[targetFile][targetRank + 2].pieceType == ownPawn)) 
                    return true; 
        }

        return false; 
    }
}