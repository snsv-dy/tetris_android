package app.tets.gameObjects;

/**
 * Created by Jacek on 2018-09-08.
 */

public class Shape{
    public boolean data[][][];
    public int[][] block_starting;

    public Shape(boolean data[][][]){
        this.data = data;
    }

    public boolean[][] getRotation(int r){
        r = r % 4;
        return this.data[r];
    }

    public void addData(boolean[][][] dat){

        this.data = dat;
        setBlocksStarting();
    }

    public int[] getBlocksStarting(int rotation){
        return this.block_starting[rotation];
    }

    public void setBlocksStarting(){
        this.block_starting = new int[4][2];
        for(int k = 0; k < 4; k++) {
            int lstarting = 100;
            int rstarting = 0;

            for (int i = 0; i < this.data[0].length; i++) {
                for (int j = 0; j < this.data[0][0].length; j++) {
                    if (this.data[k][i][j]) {
                        lstarting = Math.min(lstarting, j);
                        rstarting = Math.max(rstarting, j);
                    }
                }
            }
            this.block_starting[k][0] = lstarting;
            this.block_starting[k][1] = rstarting;
        }
    }
}