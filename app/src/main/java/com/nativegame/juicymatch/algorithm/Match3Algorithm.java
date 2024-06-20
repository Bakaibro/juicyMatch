package com.nativegame.juicymatch.algorithm;

 

public class Match3Algorithm {


    private Match3Algorithm() {
    }
    public static void findMatchTile(Match3Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 2; j++) {
                TileType type = tiles[i][j].getTileType();
                if (type == tiles[i][j + 1].getTileType()
                        && type == tiles[i][j + 2].getTileType()) {
                    for (int n = 0; n <= 2; n++) {
                        Match3Tile t = tiles[i][j + n];
                        if (t.isMatchable() && t.getTileState() == TileState.IDLE) {
                            t.matchTile();
                        }
                    }
                }
            }
        }

        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row - 2; i++) {
                TileType type = tiles[i][j].getTileType();
                if (type == tiles[i + 1][j].getTileType()
                        && type == tiles[i + 2][j].getTileType()) {
                    for (int n = 0; n <= 2; n++) {
                        Match3Tile t = tiles[i + n][j];
                        if (t.isMatchable() && t.getTileState() == TileState.IDLE) {
                            t.matchTile();
                        }
                    }
                }
            }
        }
    }

    public static void playTileEffect(Match3Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile t = tiles[i][j];
                if (t.getTileState() == TileState.MATCH) {
                    t.playTileEffect();
                }
            }
        }
    }

    public static void resetMatchTile(Match3Tile[][] tiles, int row, int col) {
        for (int j = 0; j < col; j++) {
            for (int i = row - 1; i >= 0; i--) {
                Match3Tile currentTile = tiles[i][j];
                if (currentTile.getTileState() != TileState.MATCH) {
                    continue;
                }
                for (int n = i - 1; n >= 0; n--) {
                    Match3Tile upperTile = tiles[n][j];
                    if (upperTile.isNegligible()) {
                        continue;
                    }
                    if (!upperTile.isSwappable()) {
                        currentTile.setTileState(TileState.WAITING);
                        currentTile.hideTile();
                        break;
                    }
                    if (upperTile.getTileState() == TileState.IDLE) {
                        swapTile(tiles, currentTile, upperTile);
                        break;
                    }
                }
            }
        }

        for (int j = 0; j < col; j++) {
            for (int i = row - 1, n = 1; i >= 0; i--) {
                Match3Tile t = tiles[i][j];
                if (t.getTileState() == TileState.MATCH) {
                    t.resetXByColumn(t.getColumn());
                    t.resetYByRow(-n++);
                    t.resetTile();
                }
            }
        }
    }

    public static void findUnreachableTile(Match3Tile[][] tiles, int row, int col) {
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile currentTile = tiles[i][j];
                if (currentTile.getTileState() != TileState.WAITING) {
                    continue;
                }
                for (int n = i - 1; n >= 0; n--) {
                    Match3Tile upperTile = tiles[n][j];
                    if (upperTile.isNegligible()) {
                        continue;
                    }

                    if ((j == 0 || !tiles[n][j - 1].isSwappable())
                            && !tiles[n][j].isSwappable()
                            && (j == col - 1 || !tiles[n][j + 1].isSwappable())) {
                        currentTile.setTileState(TileState.UNREACHABLE);
                    }

                    break;
                }
            }
        }
    }

    public static void checkUnreachableTile(Match3Tile[][] tiles, int row, int col) {
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile currentTile = tiles[i][j];
                if (currentTile.getTileState() != TileState.UNREACHABLE) {
                    continue;
                }
                for (int n = i - 1; n >= 0; n--) {
                    Match3Tile upperTile = tiles[n][j];
                    if (upperTile.isNegligible()) {
                        continue;
                    }

                    if ((j > 0 && tiles[n][j - 1].isSwappable())
                            || tiles[n][j].isSwappable()
                            || (j < col - 1 && tiles[n][j + 1].isSwappable())) {
                        currentTile.setTileState(TileState.MATCH);
                    }

                    break;
                }
            }
        }
    }

    public static void checkWaitingTile(Match3Tile[][] tiles, int row, int col) {
        for (int j = 0; j < col; j++) {
            for (int i = row - 1; i > 0; i--) {
                Match3Tile currentTile = tiles[i][j];
                if (currentTile.getTileState() != TileState.WAITING) {
                    continue;
                }
                for (int n = i - 1; n >= 0; n--) {
                    Match3Tile upperTile = tiles[n][j];
                    if (upperTile.isNegligible()) {
                        continue;
                    }
                    if (!upperTile.isSwappable()) {
                        if (j < col - 1) {
                            Match3Tile targetTile = tiles[n][j + 1];
                            if (targetTile.isSwappable()
                                    && !targetTile.isMoving()
                                    && targetTile.getTileState() != TileState.WAITING) {
                                currentTile.setTileState(TileState.MATCH);
                                swapTile(tiles, targetTile, currentTile);
                                break;
                            }
                        }


                        if (j > 0) {
                            Match3Tile targetTile = tiles[n][j - 1];
                            if (targetTile.isSwappable()
                                    && !targetTile.isMoving()
                                    && targetTile.getTileState() != TileState.WAITING) {
                                currentTile.setTileState(TileState.MATCH);
                                swapTile(tiles, targetTile, currentTile);
                            }
                        }

                        break;
                    }
                }
            }
        }
    }

    public static void swapTile(Match3Tile[][] tiles, Match3Tile tileA, Match3Tile tileB) {
        int rowA = tileA.getRow();
        int rowB = tileB.getRow();
        tileA.setRow(rowB);
        tileB.setRow(rowA);

        int colA = tileA.getColumn();
        int colB = tileB.getColumn();
        tileA.setColumn(colB);
        tileB.setColumn(colA);

        tiles[rowA][colA] = tileB;
        tiles[rowB][colB] = tileA;
    }

    public static void initTile(Match3Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile t = tiles[i][j];
                t.initTile();
            }
        }
    }

    public static void shuffleTile(Match3Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile t = tiles[i][j];
                if (t.isShufflable()) {
                    t.shuffleTile();
                }
            }
        }
    }

    public static void moveTile(Match3Tile[][] tiles, int row, int col, long elapsedMillis) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile t = tiles[i][j];
                t.moveTile(elapsedMillis);
            }
        }
    }

    public static boolean isMatch(Match3Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile t = tiles[i][j];
                if (t.getTileState() == TileState.MATCH) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isWaiting(Match3Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile t = tiles[i][j];
                if (t.getTileState() == TileState.WAITING) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isMoving(Match3Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Match3Tile t = tiles[i][j];
                if (t.isMoving()) {
                    return true;
                }
            }
        }

        return false;
    }

}
