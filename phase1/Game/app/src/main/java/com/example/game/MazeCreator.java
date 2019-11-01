package com.example.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * mazeGenerator code was inspired by https://www.youtube.com/watch?v=kiG1BUa34lc.
 * Uses recursive backtracking method to generate random mazes.
 */

class MazeCreator {


  private Cell[][] cells;
  private static final int COLS = 4, ROWS = 8;
  private Paint paint;
  private int cellSize;
  private Rect finishLine;
  //keep track of all walls for collision detection
  private ArrayList<Rect> walls;
  private Random random;

  MazeCreator() {
    cells = new Cell[COLS][ROWS];
    paint = new Paint();
    paint.setColor(Color.BLACK);
    paint.setStrokeWidth(3);
    walls = new ArrayList<>();
    random = new Random();
    mazeGenerator();
  }

  void drawMaze(Canvas canvas) {
    int width = canvas.getWidth();
    int height = canvas.getHeight();
    int hMargin, vMargin, cellSize;
    cellSize = width / (COLS + 1);

    hMargin = (width - COLS * cellSize) / 2;
    vMargin = (height - ROWS * cellSize) / 2;


    drawCells(canvas, vMargin, hMargin, cellSize);
  }

  private void drawCells(Canvas canvas, int vMargin, int hMargin, int cellSize) {

    canvas.translate(hMargin, vMargin);
    for (int x = 0; x < COLS; x++) {
      for (int y = 0; y < ROWS; y++) {
        if (x == COLS - 1 && y == ROWS - 1) {
          Paint paint2 = new Paint();
          paint2.setColor(Color.RED);
          Rect finish = new Rect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize);
          canvas.drawRect(finish, paint2);
          finishLine = finish;
        }
        if (cells[x][y].topWall) {
          Rect r = new Rect(x * cellSize, y * cellSize, (x + 1) * cellSize, y * cellSize + 9);
          canvas.drawRect(r, paint);
          walls.add(r);
        }
        if (cells[x][y].botWall) {
          Rect r = new Rect(x * cellSize, (y + 1) * cellSize, (x + 1) * cellSize, (y + 1) * cellSize + 9);
          canvas.drawRect(r, paint);
          walls.add(r);
        }
        if (cells[x][y].rightWall) {
          Rect r = new Rect((x + 1) * cellSize, y * cellSize, (x + 1) * cellSize + 9, (y + 1) * cellSize);
          canvas.drawRect(r, paint);
          walls.add(r);
        }
        if (cells[x][y].leftWall) {
          Rect r = new Rect(x * cellSize, y * cellSize, x * cellSize + 9, (y + 1) * cellSize);
          canvas.drawRect(r, paint);
          walls.add(r);
        }

      }
    }
  }


  private void mazeGenerator() {
    Stack<Cell> stack = new Stack<>();
    Cell current, next;

    cells = new Cell[COLS][ROWS];

    for (int x = 0; x < COLS; x++) {
      for (int y = 0; y < ROWS; y++) {
        cells[x][y] = new Cell(x, y);
      }
    }

    current = cells[0][0];
    current.visited = true;
    do {
      next = getNeighbour(current);
      if (next != null) {
        removeWall(current, next);
        stack.push(current);
        current = next;
        current.visited = true;
      } else {
        current = stack.pop();
      }
    } while (!stack.isEmpty());
  }

  private Cell getNeighbour(Cell cell) {

    ArrayList<Cell> neighbours = new ArrayList<>();

    //left neighbour
    if (cell.col > 0) {
      if (!cells[cell.col - 1][cell.row].visited) {
        neighbours.add(cells[cell.col - 1][cell.row]);
      }
    }
    //right neighbour
    if (cell.col < COLS - 1) {
      if (!cells[cell.col + 1][cell.row].visited) {
        neighbours.add(cells[cell.col + 1][cell.row]);
      }
    }

    //top neighbour
    if (cell.row > 0) {
      if (!cells[cell.col][cell.row - 1].visited) {
        neighbours.add(cells[cell.col][cell.row - 1]);
      }
    }

    //bottom neighbour
    if (cell.row < ROWS - 1) {
      if (!cells[cell.col][cell.row + 1].visited) {
        neighbours.add(cells[cell.col][cell.row + 1]);
      }
    }
    if (neighbours.size() > 0) {
      int index = random.nextInt(neighbours.size());
      System.out.println(index);
      return neighbours.get(index);
    }
    return null;
  }

  private void removeWall(Cell current, Cell next) {
    if (current.col == next.col && current.row == next.row + 1) {
      current.topWall = false;
      next.botWall = false;
    }
    if (current.col == next.col && current.row == next.row - 1) {
      current.botWall = false;
      next.topWall = false;
    }
    if (current.col == next.col + 1 && current.row == next.row) {
      current.leftWall = false;
      next.rightWall = false;
    }
    if (current.col == next.col - 1 && current.row == next.row) {
      current.rightWall = false;
      next.leftWall = false;
    }
  }

  boolean checkCollisions(Player player) {
    if (walls.size() > 0) {
      return MazeCollisions.checkCollisions(walls, player);
    }
    return false;
  }

  boolean checkFinished(Player player) {
    if (walls.size() > 0) {
      return MazeCollisions.checkFinished(player, finishLine);
    }
    return false;
  }


  private class Cell {
    boolean topWall = true, leftWall = true, botWall = true, rightWall = true, visited = false;
    int col, row;


    Cell(int col, int row) {
      this.col = col;
      this.row = row;
    }
  }
}
