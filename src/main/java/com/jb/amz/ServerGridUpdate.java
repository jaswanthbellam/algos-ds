package com.jb.amz;

import com.jb.exception.NoSingleServerWithUpdate;

import java.util.List;

/**
 * A Company has a 2D grid of cell towers. All servers need to be updated to the latest software version.
 * All servers need to be updated to the latest software version. Servers that already have the update should not
 * be updated again. Servers are in the form of a 2D array of 0 and 1 where the value 0 represents an out
 * of date server and the value of 1 represents an updated server.
 *
 * In a single, an updated server can push the update to each of its adjacent out of date servers.
 *
 * An adjacent server is either on the left, right, above or below a given server. Once the server receives
 * the update, it becomes updated and can push the update to its adjacent servers on teh following day. Given
 * the 2D array representing the current status of its server.
 *
 * This Class provides the method to determine the minimum number of days required to push the update
 * to all available servers.
 */
public class ServerGridUpdate {

    /**
     * Method which return no of days to update all servers.
     * Assumption is atleast one server is updated.
     * @param rows of grid
     * @param columns of grid
     * @param grid grid with server update info
     * @return no of days to update all servers
     */
    public static int numberOfDaysToUpdateServer(int rows, int columns,
                                                 List<List<Integer>> grid) throws NoSingleServerWithUpdate {
        int noOfDays = 0;
        int[][] arrayGrid = new int[rows][columns];
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                arrayGrid[i][j] = grid.get(i).get(j);
            }
        }
        return numberOfDaysToUpdateServer(rows, columns, arrayGrid, noOfDays);
    }

    /**
     * Recursive method to update and return no of days
     * @param rows of grid
     * @param columns of grid
     * @param grid updated
     * @param noOfDays updated after each day
     * @return total no of days till this day
     */
    private static int numberOfDaysToUpdateServer(int rows, int columns,
                                                  int[][] grid, int noOfDays) throws NoSingleServerWithUpdate {
        boolean anyZeros=false;
        boolean anyZerosLeft = false;
        boolean anyOnes = false;
        int[][] updatedGrid = new int[rows][columns];


        for(int row=0; row<rows; row++ ) {
            for(int column=0; column<columns; column++) {

                int value = grid[row][column];
                updatedGrid[row][column] = value;
                //check every server
                if(value == 0) {
                    anyZeros = true;
                    boolean updatedVal=false;
                    if(row!=0) {
                        //Check above server
                        if(grid[(row-1)][column] == 1) {
                            updatedGrid[row][column] = 1;
                            updatedVal = true;
                        }
                    }

                    if((!updatedVal) && (column!=0)) {
                        //check left server
                        if(grid[row][column-1] == 1) {
                            updatedGrid[row][column] = 1;
                            updatedVal = true;
                        }
                    }

                    if((!updatedVal) && (column != columns-1)) {
                        //check right server
                        if(grid[(row)][(column+1)] == 1) {
                            updatedGrid[row][column] = 1;
                            updatedVal =  true;
                        }
                    }

                    if((!updatedVal) && (row != rows-1)) {
                        //check below server
                        if(grid[(row+1)][(column)] == 1) {
                            updatedGrid[row][column] = 1;
                            updatedVal = true;
                        }
                    }

                    if(!updatedVal) {
                        anyZerosLeft =  true;
                    }
                } else {
                    anyOnes = true;
                }
            }
        }

        if(!anyOnes) {
            throw new NoSingleServerWithUpdate("No Server with update in the grid");
        }
        //condition to update days if any servers are updated in the given day
        if(anyZeros){
            noOfDays++;
        }

        //condition to check if any servers are still not updated
        // and call the function recursively to update it
        if(anyZerosLeft) {
           return numberOfDaysToUpdateServer(rows, columns, updatedGrid, noOfDays);
        }
        return noOfDays;
    }
}
