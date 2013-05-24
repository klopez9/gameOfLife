package gameOfLife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuSetup {
    private static Model model;
    private static JMenuItem wrapAround;
     
    public static void setupBar(JMenuBar menuBar, Model model) {
        MenuSetup.model = model;
        MenuListener suspender = new Suspender();

        JMenu pattern = new JMenu("Pattern");
        pattern.setMnemonic(KeyEvent.VK_P);
        menuBar.add(pattern);
        populatePattern(pattern);   
        pattern.addMenuListener(suspender);
       
        JMenu dimensions = new JMenu("Dimensions");
        pattern.setMnemonic(KeyEvent.VK_D);
        menuBar.add(dimensions);
        populateDimesions(dimensions);
        dimensions.addMenuListener(suspender);
 
        JMenu run = new JMenu("Control");
        pattern.setMnemonic(KeyEvent.VK_R);
        menuBar.add(run);
        populateRun(run);
        run.addMenuListener(suspender);
        
        JMenu options = new JMenu("Options");
        pattern.setMnemonic(KeyEvent.VK_O);
        menuBar.add(options);
        populateOptions(options);
        options.addMenuListener(suspender);
      }
   
    private static void populateOptions(JMenu menu) {
    	// Options should have
    	// Wrap Around Borders (accelerator is Ctrl-W)
    	// Step Time (accelerator is Ctrl-S)
    	// Color Scheme (accelerator is Ctrl-Shift-C)
    	
    	wrapAround = new JMenuItem("Wrap Around Borders");
      wrapAround.setMnemonic(KeyEvent.VK_W);
      wrapAround.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_W, ActionEvent.CTRL_MASK));
      wrapAround.addActionListener(new WrapListener());
      menu.add(wrapAround);
      
      JMenuItem stepTime = new JMenuItem("Step Time");
      stepTime.setMnemonic(KeyEvent.VK_T);
      stepTime.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_T, ActionEvent.CTRL_MASK));
      stepTime.addActionListener(new StepListener());
      menu.add(stepTime);
      
      JMenuItem colorScheme = new JMenuItem("Color Scheme");
      colorScheme.setMnemonic(KeyEvent.VK_L);
      colorScheme.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_C, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
      colorScheme.addActionListener(new ColorSchemeListener());
      menu.add(colorScheme);
      
      JMenuItem about = new JMenuItem("About");
      about.setMnemonic(KeyEvent.VK_A);
      about.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_A, ActionEvent.CTRL_MASK));
      about.addActionListener(new AboutListener());
      menu.add(about);
		}

		private static void populateDimesions(JMenu menu) {
      //UNDER DIMENSION, you should see
      //Change Rows     (accelerator is Ctrl-R)
      //Change Columns  (accelerator is Ctrl-C)
      //The animation must be suspended and
      //you must pop up a JOptionPane.showInputDialog
      //and read the new number of rows or columns
      //respectively. The listener continues by
      //calling model.resetRows or model.resetColumns
      //Please verify that resetRows and resetColumns
      //actually work correctly when you test your code
    	
    	JMenuItem changeRows = new JMenuItem("Change Rows");
      changeRows.setMnemonic(KeyEvent.VK_R);
      changeRows.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_R, ActionEvent.CTRL_MASK));
      changeRows.addActionListener(new ChangeRowsListener());
      menu.add(changeRows);
     
      JMenuItem changeCols = new JMenuItem("Change Columns");
      changeCols.setMnemonic(KeyEvent.VK_C);
      changeCols.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_C, ActionEvent.CTRL_MASK));
      changeCols.addActionListener(new ChangeColsListener());
      menu.add(changeCols);
			
		}

		private static void populatePattern(JMenu menu) {
				
	      JMenuItem random = new JMenuItem("Random");
	      random.setMnemonic(KeyEvent.VK_Z);
	      random.setAccelerator(KeyStroke.getKeyStroke(
	              KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
	      random.addActionListener(new RandomListener());
	      menu.add(random);
	      
        JMenuItem glider = new JMenuItem("Glider");
        glider.setMnemonic(KeyEvent.VK_L);
        glider.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        glider.addActionListener(new GliderListener());
        menu.add(glider);
       
        JMenuItem gosper = new JMenuItem("Gosper Gun");
        gosper.setMnemonic(KeyEvent.VK_G);
        gosper.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        gosper.addActionListener(new GosperListener());
        menu.add(gosper);
        
        JMenuItem faces = new JMenuItem("Faces");
        faces.setMnemonic(KeyEvent.VK_F);
        faces.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        faces.addActionListener(new FacesListener());
        menu.add(faces);
        
        JMenuItem flower = new JMenuItem("Flower");
        flower.setMnemonic(KeyEvent.VK_F);
        flower.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        flower.addActionListener(new FlowerListener());
        menu.add(flower);
       
    }

    private static void populateRun(JMenu menu) {
        JMenuItem pause = new JMenuItem("Pause");
        pause.setMnemonic(KeyEvent.VK_P);
        pause.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        pause.addActionListener(new SuspenderAction());
        menu.add(pause);
       
        JMenuItem go = new JMenuItem("Go");
        go.setMnemonic(KeyEvent.VK_G);
        go.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        go.addActionListener(new GoAgainAction());
        menu.add(go);

        JMenuItem clear = new JMenuItem("Clear");
        clear.setMnemonic(KeyEvent.VK_C);
        clear.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        clear.addActionListener(new ClearAction());
        menu.add(clear);
       
    }

    private static class SuspenderAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
            model.setAnimation(false);           
		}
    	
    }

    private static class GoAgainAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
            model.setAnimation(true);           
		}
    	
    }

    private static class ClearAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
            model.clear();           
		}
    	
    }

    private static class Suspender implements MenuListener {
        @Override
        public void menuCanceled(MenuEvent e) {
            model.setAnimation(true);           
        }

        @Override
        public void menuDeselected(MenuEvent e) {
            model.setAnimation(true);           
        }

        @Override
        public void menuSelected(MenuEvent e) {
            model.setAnimation(false);           
        }
    }
   
    private static class GliderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setAnimation(false);
            model.setupGlider();
            model.setAnimation(true);
        }
       
    }
    private static class RandomListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setAnimation(false);
            model.setupRandomStart();
            model.setAnimation(true);
        }       
    }
    
    private static class GosperListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setAnimation(false);
            model.setupGosper();
            model.setAnimation(true);
        }       
    }
    private static class FacesListener implements ActionListener {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	          model.setAnimation(false);
	          model.setupFaces();
	          model.setAnimation(true);
	      }       
	  }
    private static class FlowerListener implements ActionListener {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	          model.setAnimation(false);
	          model.setupFlower();
	          model.setAnimation(true);
	      }       
	  }
    private static class ChangeRowsListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          model.setAnimation(false);
          try {
          	String s = JOptionPane.showInputDialog("Please input a new row value: ");
          	int r = Integer.parseInt(s);
            model.resetRows(r);
          } catch (NumberFormatException n) {
          	JOptionPane.showMessageDialog(null, "That is not a number!");
          }
          model.setAnimation(true);
      }
    }
    private static class ChangeColsListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          model.setAnimation(false);
          try {
          	String s = JOptionPane.showInputDialog("Please input a new column value: ");
          	int c = Integer.parseInt(s);
            model.resetColumns(c);
          } catch (NumberFormatException n) {
          	JOptionPane.showMessageDialog(null, "That is not a number!");
          }
          model.setAnimation(true);
      }       
    }
    private static class WrapListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          model.setAnimation(false);
          model.setWrapped(!model.isWrapped());
        	String title;
        	if (model.isWrapped()) {
        		title = "Unwrap Borders";
        	} else {
        		title = "Wrap Around Borders";
        	}
          wrapAround.setText(title);
          model.setAnimation(true);
      }       
    }
    private static class StepListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          model.setAnimation(false);
          model.stopTimer();
          try {
            String s = JOptionPane.showInputDialog("Please input a new update speed (in milliseconds): ");
            model.setInterval(Integer.parseInt(s));
          } catch (NumberFormatException n) {
          	JOptionPane.showMessageDialog(null, "Illegal Argument. The update speed\n" +
        				"factor must be a number.", "Error", JOptionPane.WARNING_MESSAGE);
          }
          model.setAnimation(true);
          model.startTimer();
      }       
    }
    private static class ColorSchemeListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          model.setAnimation(false);
          model.setRandomColor(false);

          Object[] colors = {"Black", "Red", "Pink", "Orange",
          		"Yellow", "Green", "Cyan", "Blue", "White",  "Rainbow",
          		"Custom...", "Surprise Me!",};
          
          String s = (String)JOptionPane.showInputDialog(
                              null,
                              "Pick a color: ",
                              "Color Scheme",
                              JOptionPane.PLAIN_MESSAGE,
                              null,
                              colors,
                              colors[0]);
          if (s == "Rainbow") {
          	model.setRandomColor(true);
          } else if (s == "Custom...") {
          	try {
              String r = JOptionPane.showInputDialog("Please input the red factor: ");
              String g = JOptionPane.showInputDialog("Please input the green factor: ");
              String b = JOptionPane.showInputDialog("Please input the blue factor: ");
              model.setBoxColor(Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b));
          	} catch (IllegalArgumentException ex) {
          		JOptionPane.showMessageDialog(null, "Illegal Argument. The primary color factors\n" +
          				"must be numbers between 0 and 255.", "Error", JOptionPane.WARNING_MESSAGE);
          	}
          } else if (s == "Surprise Me!") {
          	int r = (int) (Math.random() * 255);
          	int g = (int) (Math.random() * 255);
          	int b = (int) (Math.random() * 255);
          	model.setBoxColor(r, g, b);
          } else {
            model.setBoxColor(s);
          }
          
          model.setAnimation(true);
      }       
    }
    private static class AboutListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
          model.setAnimation(false);
          JOptionPane.showMessageDialog(null,
          		"Game of Life created by John Horton Conway\n" +
          		"Program created by Kevin 'K-LO' Lopez\n" +
          		"Special Thanks to Phil Dexter and Prof. Lander", "About",
          		JOptionPane.PLAIN_MESSAGE);
          model.setAnimation(true);
      }       
	  }
}