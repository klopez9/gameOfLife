package gameOfLife;

import java.awt.Color;
//import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

public class GridComponent extends JComponent 
implements Observer {

	private static final long serialVersionUID = 4908799747260667366L;
	private Model model;
	private Rectangle2D.Double currentRect = new Rectangle2D.Double();
	private Rectangle2D.Double rectToDraw = new Rectangle2D.Double();
	private Rectangle2D.Double previousRectDrawn = new Rectangle2D.Double();
	
	public GridComponent(Model model) {
		this.model = model;
		model.addObserver(this);
		addMouseListener(new MyMouseListener());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		boolean[][] liveArray = model.getLiveArray();
		int numRows = liveArray.length;
		int numCols = liveArray[0].length;
		double boxHt = getHeight()/(double)numRows;
		double boxWd = getWidth()/(double)numCols;
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if(liveArray[row][col]) {
					Shape drawObj = new Rectangle2D.Double(
							col*boxWd, row*boxHt,boxWd,boxHt);
					if (model.isRandomColor()) {
          	int rRand = (int) (Math.random() * 255);
          	int gRand = (int) (Math.random() * 255);
          	int bRand = (int) (Math.random() * 255);
						g2.setColor(new Color(rRand, gRand, bRand));
					} else {
						g2.setColor(model.getBoxColor());
					}
					g2.fill(drawObj);
					g2.setColor(Color.WHITE);
					g2.draw(drawObj);
				}				
			}
		}
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if(!liveArray[row][col]) {
					Shape drawObj = new Rectangle2D.Double(
							col*boxWd, row*boxHt,boxWd,boxHt);
					g2.setColor(Color.WHITE);
					g2.fill(drawObj);
					g2.setColor(Color.BLACK);
					g2.draw(drawObj);
				}				
			}
		}
		Shape drawObj = new Rectangle2D.Double(
				0, 0, getWidth()-1, getHeight()-1);
		g2.setColor(Color.BLACK);
		g2.draw(drawObj);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
	
	public void updateDrawableRect(int compWidth, int compHeight) {
		double x = currentRect.x;
		double y = currentRect.y;
		double width = currentRect.width;
		double height = currentRect.height;

		//Make the width and height positive, if necessary.
		if (width < 0) {
			width = 0 - width;
			x = x - width + 1; 
			if (x < 0) {
				width += x; 
				x = 0;
			}
		}
		if (height < 0) {
			height = 0 - height;
			y = y - height + 1; 
			if (y < 0) {
				height += y; 
				y = 0;
			}
		}

		//The rectangle shouldn't extend past the drawing area.
		if ((x + width) > compWidth) {
			width = compWidth - x;
		}
		if ((y + height) > compHeight) {
			height = compHeight - y;
		}

		//Update rectToDraw after saving old value.
		if (rectToDraw != null) {
			previousRectDrawn.setFrame(
					rectToDraw.x, rectToDraw.y, 
					rectToDraw.width, rectToDraw.height);
			rectToDraw.setFrame(x, y, width, height);
		} else {
			rectToDraw = new Rectangle2D.Double(x, y, width, height);
		}
	}

	/**
	 * Mouse Listener stops the simulation running and gets the cell position 
	 * that is clicked and calls to set that cell to be "alive"
	 * CHANGE THE CODE SO THAT IT FLIPS THE "alive" STATE OF THE CELL.
	 * i.e. add code so that if the cell is alive, its "alive" status is
	 * changed to false.
	 */
	class MyMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent mEvt) {
			model.setAnimation(false);
			int mouseX = mEvt.getX();
			int mouseY = mEvt.getY();
			int cellX = mouseX * model.getMaxCols()/getWidth();
			int cellY = mouseY * model.getMaxRows()/getHeight();;
			int row = cellY;
			int col = cellX;
			model.toggleAlive(row, col);
			repaint();
		}
	}

}
