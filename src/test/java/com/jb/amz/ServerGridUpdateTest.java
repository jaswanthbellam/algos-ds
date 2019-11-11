package com.jb.amz;

import com.jb.exception.NoSingleServerWithUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ServerGridUpdateTest {

    @ParameterizedTest(name = "Run {index}:rows={0}, columns={1}, grid={2}, noOfDays={3}")
    @MethodSource("serverGridParameters")
    public void testNumberOfDays(int rows, int columns, List<List<Integer>> grid, int expectedNoOfDays) throws NoSingleServerWithUpdate {
        assertEquals(expectedNoOfDays,
                ServerGridUpdate.numberOfDaysToUpdateServer(rows,columns,grid));
    }

    @Test
    public void testGridWithoutAnyServerUpdate() {
        List<List<Integer>> grid = new ArrayList<>();
        grid.add(Arrays.asList(0,0,0,0));
        grid.add(Arrays.asList(0,0,0,0));
        assertThrows(NoSingleServerWithUpdate.class, ()->
                ServerGridUpdate.numberOfDaysToUpdateServer(2,4,grid));
    }


    static Stream<Arguments> serverGridParameters() {

        List<List<Integer>> grid1 = new ArrayList<>();
        grid1.add(Arrays.asList(0,0,0,0));
        grid1.add(Arrays.asList(0,0,0,0));
        grid1.add(Arrays.asList(0,0,0,0));
        grid1.add(Arrays.asList(0,0,0,0));
        grid1.add(Arrays.asList(1,0,0,0));

        List<List<Integer>> grid2 = new ArrayList<>();
        grid2.add(Arrays.asList(0,1,1,0,1));
        grid2.add(Arrays.asList(0,1,0,1,0));
        grid2.add(Arrays.asList(0,0,0,0,1));
        grid2.add(Arrays.asList(0,1,0,0,0));

        List<List<Integer>> grid3 = new ArrayList<>();
        grid3.add(Arrays.asList(0,1,0,1,0,0));
        grid3.add(Arrays.asList(0,0,0,0,0,0));
        grid3.add(Arrays.asList(0,1,0,0,0,0));
        grid3.add(Arrays.asList(0,0,0,1,0,0));
        grid3.add(Arrays.asList(0,0,1,0,0,0));

        return Stream.of(
                Arguments.of(5,4,grid1,7),
                Arguments.of(4,5,grid2,2),
                Arguments.of(5,6,grid3,3)
        );
    }
}
