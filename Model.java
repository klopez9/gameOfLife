package gameOfLife;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Observable;

import javax.swing.Timer;

public class Model extends Observable {
  private int maxRows = 50;
  private int maxCols = 50;
  private boolean animation = true;
  private Timer timer;
  private int interval = 100;
  private boolean wrapped = false;
  private boolean randomColor = false;
  private Color boxColor = Color.BLACK;

	private boolean[][] isAlive;
  private int[][] neighborCount;

  public Model() {
      isAlive =  new boolean[maxRows][maxCols];
      neighborCount = new int[maxRows][maxCols];
  }

  public void setAnimation(boolean animation) {
      this.animation = animation;
  }

  public void resetArraySizes(int newRows, int newCols) {
      maxRows = newRows;
      maxCols = newCols;
      boolean[][] newAlive = new boolean[maxRows][maxCols];
      int rowLimit = Math.min(newRows, isAlive.length);
      int colLimit = Math.min(newCols, isAlive[0].length);
      for(int i = 0; i < rowLimit; i++) {
          System.arraycopy(isAlive[i], 0, newAlive[i], 0, colLimit);
      }
      isAlive = newAlive;
      neighborCount = new int[maxRows][maxCols];
      setChanged();
      notifyObservers();
  }

  public void resetRows(int newRows) {
      maxRows = newRows;
      boolean[][] newAlive = new boolean[maxRows][maxCols];
      int rowLimit = Math.min(newRows, isAlive.length);
      for(int i = 0; i < rowLimit; i++) {
          System.arraycopy(isAlive[i], 0, newAlive[i], 0, maxCols);
      }
      isAlive = newAlive;
      neighborCount = new int[maxRows][maxCols];
      setChanged();
      notifyObservers();
  }

  public int getMaxRows() {
  	return maxRows;
	}
	
	public void toggleAlive(int row, int col) {
		isAlive[row][col] = !isAlive[row][col];
	}

	public void resetColumns(int newCols) {
      maxCols = newCols;
      boolean[][] newAlive = new boolean[maxRows][maxCols];
      int colLimit = Math.min(newCols, isAlive[0].length);
      for(int i = 0; i < maxRows; i++) {
          System.arraycopy(isAlive[i], 0, newAlive[i], 0, colLimit);
      }
      isAlive = newAlive;
      neighborCount = new int[maxRows][maxCols];
      setChanged();
      notifyObservers();
  }
	
	public int getMaxCols() {
		return maxCols;
	}

  public void step(boolean wrap) {
    //compute next live array from the current one
  	//wrap around
  	if (wrap) {
  		for (int i = 0; i < maxRows; i++) {
	  		for (int j = 0; j < maxCols; j++) {
	  			if(isAlive[(i+maxRows - 1)%maxRows][(j+maxCols - 1)%maxCols]) 	
	  				neighborCount[i][j]++;
	  			if(isAlive[i][(j+maxCols - 1)%maxCols])		
	  				neighborCount[i][j]++; 
	  			if(isAlive[(i+maxRows + 1)%maxRows][(j+maxCols - 1)%maxCols]) 	
	  				neighborCount[i][j]++; 
	  			if(isAlive[(i+maxRows - 1)%maxRows][j]) 		
	  				neighborCount[i][j]++;
	//  		if(isAlive[i][j]) 			
	//  			neighborCount[i][j]++; 
	  			if(isAlive[(i+maxRows + 1)%maxRows][j]) 		
	  				neighborCount[i][j]++; 
	  			if(isAlive[(i+maxRows - 1)%maxRows][(j+maxCols + 1)%maxCols]) 	
	  				neighborCount[i][j]++; 
	  			if(isAlive[i][(j+maxCols + 1)%maxCols]) 		
	  				neighborCount[i][j]++; 
	  			if(isAlive[(i+maxRows + 1)%maxRows][(j+maxCols + 1)%maxCols]) 	
	  				neighborCount[i][j]++; 
	  		}
	  	}
  	} else {
	  	for (int i = 0; i < maxRows; i++) {
	  		for (int j = 0; j < maxCols; j++) {
	  			if(i>0 && j>0 && isAlive[i-1][j-1]) 	
	  				neighborCount[i][j]++;
	  			if(j>0 && isAlive[i][j-1])		
	  				neighborCount[i][j]++; 
	  			if(i<maxRows-1 && j>0 && isAlive[i+1][j-1]) 	
	  				neighborCount[i][j]++; 
	  			if(i>0 && isAlive[i-1][j]) 		
	  				neighborCount[i][j]++;
	//  		if(isAlive[i][j]) 			
	//  			neighborCount[i][j]++; 
	  			if(i<maxRows-1 && isAlive[i+1][j]) 		
	  				neighborCount[i][j]++; 
	  			if(i>0 && j<maxCols-1 && isAlive[i-1][j+1]) 	
	  				neighborCount[i][j]++; 
	  			if(j<maxCols-1 && isAlive[i][j+1]) 		
	  				neighborCount[i][j]++; 
	  			if(i<maxRows-1 && j<maxCols-1 && isAlive[i+1][j+1]) 	
	  				neighborCount[i][j]++; 
	  		}
	  	}
  	}
  	 //change values in isAlive based on neighborCount
  	 for (int i = 0; i < maxRows; i++) {
  		 for (int j = 0; j < maxCols; j++) {
  			 /* Logic:
  			 The cell must become alive if it has 3 neighbors;
  			 The cell must die if it has <2 or >3 neighbors;
  			 The cell must not change its life/death status if
  			 	 it has 2 neighbors.
  			 Its current life/death status doesn't matter.
  			 Thus, the cell is only alive if neighborCount == 3
  			  and dead if neighborCount != 2 and != 3.*/
  			 if (neighborCount[i][j] == 3)
  				 isAlive[i][j] = true;
  			 else if (neighborCount[i][j] != 2)
  				 isAlive[i][j] = false;
  		 }
  	 }
  	 for (int i = 0; i < maxRows; i++) {
  		 for (int j = 0; j < maxCols; j++) {
  			 neighborCount[i][j] = 0;
  		 }
  	 }
     setChanged();
     notifyObservers();
  }

  public boolean[][] getLiveArray() {
      return isAlive;
  }

  /**
   * Generate a random layout of cells that are alive for use when
   * starting the Game of Life simulation
   */
  public void setupRandomStart() {
      java.util.Random random = new java.util.Random();
      for(int i = 0; i < maxRows; i++) {
          for(int j = 0; j < maxCols; j++) {
              isAlive[i][j] = random.nextBoolean();
          }
      }
  }

  /**
   * Initialize the live cells with a glider to test that
   * it works correctly. Assume there are at least 3x3 cells
   */
  public void setupGlider() {
      isAlive =  new boolean[maxRows][maxCols];
      isAlive[maxRows-3][1] = true;
      isAlive[maxRows-3][2] = true;
      isAlive[maxRows-3][3] = true;
      isAlive[maxRows-2][3] = true;
      isAlive[maxRows-1][2] = true;
  }
 
  /**
   * Initialize the live cells with a Gosper gun
   * from http://en.wikipedia.org/wiki/File:Game_of_life_glider_gun.svg
   */
  public void setupGosper() {
      isAlive =  new boolean[maxRows][maxCols];
      int cols = Math.max(38, maxCols);
      int rows = Math.max(20, maxRows);
      resetArraySizes(rows,cols);
      isAlive[5][1] = true;
      isAlive[5][2] = true;
      isAlive[6][1] = true;
      isAlive[6][2] = true;
      isAlive[1][25] = true;
      isAlive[2][23] = true;
      isAlive[2][25] = true;
      isAlive[3][13] = true;
      isAlive[3][14] = true;
      isAlive[3][21] = true;
      isAlive[3][22] = true;
      isAlive[3][35] = true;
      isAlive[3][36] = true;
      isAlive[4][12] = true;
      isAlive[4][16] = true;
      isAlive[4][21] = true;
      isAlive[4][22] = true;
      isAlive[4][35] = true;
      isAlive[4][36] = true;
      isAlive[5][11] = true;
      isAlive[5][17] = true;
      isAlive[5][21] = true;
      isAlive[5][22] = true;
      isAlive[6][11] = true;
      isAlive[6][15] = true;
      isAlive[6][17] = true;
      isAlive[6][18] = true;
      isAlive[6][23] = true;
      isAlive[6][25] = true;
      isAlive[7][11] = true;
      isAlive[7][17] = true;
      isAlive[7][25] = true;
      isAlive[8][12] = true;
      isAlive[8][16] = true;
      isAlive[9][13] = true;
      isAlive[9][14] = true;       
  }
  
	public void setupFaces() {
    isAlive =  new boolean[maxRows][maxCols];
    int cols = Math.max(21, maxCols);
    int rows = Math.max(27, maxRows);
    resetArraySizes(rows,cols);
    isAlive[7][10] = true;
    isAlive[8][9] = true;
    isAlive[8][11] = true;
    isAlive[9][10] = true;
    isAlive[10][10] = true;
    isAlive[11][10] = true;
    isAlive[12][9] = true;
    isAlive[12][10] = true;
    isAlive[12][11] = true;
	}
	
	public void setupFlower() {
    isAlive =  new boolean[maxRows][maxCols];
    int cols = Math.max(33, maxCols);
    int rows = Math.max(31, maxRows);
    resetArraySizes(rows,cols);
    isAlive[13][14] = true;
    isAlive[13][15] = true;
    isAlive[13][16] = true;
    isAlive[14][15] = true;
    isAlive[15][15] = true;
    isAlive[16][15] = true;
    isAlive[17][14] = true;
    isAlive[17][15] = true;
    isAlive[17][16] = true;
	}

  public boolean isWrapped() {
		return wrapped;
	}

	public void setWrapped(boolean wrapped) {
		this.wrapped = wrapped;
	}

  public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void startTimer() {
      timer = new Timer(interval, new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent arg0) {              
              if(animation) {
                  step(wrapped);
              }
          }       
      });
      timer.start();
  }
	
	public void stopTimer() {
		timer.stop();
	}

	public void clear() {
		isAlive = new boolean[maxRows][maxCols];
	}
	
	public Color getBoxColor() {
		return boxColor;
	}

	public void setBoxColor(Color boxColor) {
		this.boxColor = boxColor;
	}
	
	public void setBoxColor(String boxColor) {
		Color color;
		try {
		    Field field = Class.forName("java.awt.Color" +
		    		"").getField(boxColor.toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = Color.BLACK;
		}
		this.boxColor = color;
	}
	
	public void setBoxColor(int a, int b, int c) {
		this.boxColor = new Color(a, b, c);
	}

	public boolean isRandomColor() {
		return randomColor;
	}
	
	public void setRandomColor(boolean randomColor) {
		this.randomColor = randomColor;
	}

}