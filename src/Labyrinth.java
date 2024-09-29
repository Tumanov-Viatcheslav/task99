import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Labyrinth {

    private static InputData read(String fileName) {
        InputData data = new InputData();
        try (BufferedReader input = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] sizesStr = input.readLine().split(" ");
            data.labyrinth = new int[Integer.parseInt(sizesStr[0])][Integer.parseInt(sizesStr[2])][Integer.parseInt(sizesStr[1])];
            for (int h = 0; h < data.labyrinth.length; h++) {
                for (int m = 0; m < data.labyrinth[0].length; m++) {
                    line = input.readLine();
                    for (int n = 0; n < data.labyrinth[0][0].length; n++) {
                        if (line.charAt(n) == '1') {
                            data.start = new Position3(h, m, n);
                            data.labyrinth[h][m][n] = Integer.MAX_VALUE;
                        }
                        if (line.charAt(n) == '2') {
                            data.end = new Position3(h, m, n);
                            data.labyrinth[h][m][n] = Integer.MAX_VALUE;
                        }
                        if (line.charAt(n) == '.')
                            data.labyrinth[h][m][n] = Integer.MAX_VALUE;
                        if (line.charAt(n) == 'o')
                            data.labyrinth[h][m][n] = -1;
                    }
                }
                input.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    private static void putPossibleMovementsInQueue(Queue<Position3> queue, int[][][] labyrinth, Position3 currentPosition, int sizeN, int sizeM, int sizeH) {
        //Add East
        if (currentPosition.n + 1 < sizeN &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n + 1] != -1 &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1 < labyrinth[currentPosition.h][currentPosition.m][currentPosition.n + 1]
        ) {
            labyrinth[currentPosition.h][currentPosition.m][currentPosition.n + 1] = labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1;
            queue.add(new Position3(currentPosition.h, currentPosition.m, currentPosition.n + 1));
        }

        //Add North
        if (currentPosition.m + 1 < sizeM &&
                labyrinth[currentPosition.h][currentPosition.m + 1][currentPosition.n] != -1 &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1 < labyrinth[currentPosition.h][currentPosition.m + 1][currentPosition.n]
        ) {
            labyrinth[currentPosition.h][currentPosition.m + 1][currentPosition.n] = labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1;
            queue.add(new Position3(currentPosition.h, currentPosition.m + 1, currentPosition.n));
        }

        //Add West
        if (currentPosition.n - 1 >= 0 &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n - 1] != -1 &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1 < labyrinth[currentPosition.h][currentPosition.m][currentPosition.n - 1]
        ) {
            labyrinth[currentPosition.h][currentPosition.m][currentPosition.n - 1] = labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1;
            queue.add(new Position3(currentPosition.h, currentPosition.m, currentPosition.n - 1));
        }

        //Add South
        if (currentPosition.m - 1 >= 0 &&
                labyrinth[currentPosition.h][currentPosition.m - 1][currentPosition.n] != -1 &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1 < labyrinth[currentPosition.h][currentPosition.m - 1][currentPosition.n]
        ) {
            labyrinth[currentPosition.h][currentPosition.m - 1][currentPosition.n] = labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1;
            queue.add(new Position3(currentPosition.h, currentPosition.m - 1, currentPosition.n));
        }

        //Add Up
        if (currentPosition.h + 1 < sizeH &&
                labyrinth[currentPosition.h + 1][currentPosition.m][currentPosition.n] != -1 &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1 < labyrinth[currentPosition.h + 1][currentPosition.m][currentPosition.n]
        ) {
            labyrinth[currentPosition.h + 1][currentPosition.m][currentPosition.n] = labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1;
            queue.add(new Position3(currentPosition.h + 1, currentPosition.m, currentPosition.n));
        }

        //Add Down
        if (currentPosition.h - 1 >= 0 &&
                labyrinth[currentPosition.h - 1][currentPosition.m][currentPosition.n] != -1 &&
                labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1 < labyrinth[currentPosition.h - 1][currentPosition.m][currentPosition.n]
        ) {
            labyrinth[currentPosition.h - 1][currentPosition.m][currentPosition.n] = labyrinth[currentPosition.h][currentPosition.m][currentPosition.n] + 1;
            queue.add(new Position3(currentPosition.h - 1, currentPosition.m, currentPosition.n));
        }
    }

    public static int findPathBFS(int[][][] labyrinth, Position3 start, Position3 end) {
        final int MOVEMENT_TIME = 5, sizeH = labyrinth.length, sizeM = labyrinth[0].length, sizeN = labyrinth[0][0].length;
        Position3 currentPosition;
        Queue<Position3> queue = new LinkedList<>();
        labyrinth[start.h][start.m][start.n] = 0;
        queue.add(start);


        while (!queue.isEmpty()) {
            currentPosition = queue.poll();
            putPossibleMovementsInQueue(queue, labyrinth, currentPosition, sizeN, sizeM, sizeH);
        }
        return MOVEMENT_TIME * labyrinth[end.h][end.m][end.n];
    }

    public static void main(String[] args) {
        InputData data = read("input.txt");
        int result = findPathBFS(data.labyrinth, data.start, data.end);
        System.out.println(result);
    }

    static class InputData {
        int[][][] labyrinth;
        Position3 start, end;
    }
}