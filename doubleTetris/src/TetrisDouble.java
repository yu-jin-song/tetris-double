import java.awt.*;
import javax.swing.*;

public class TetrisDouble {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TetrisFrame mainFrame = new TetrisFrame("Tetris");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setBackground(new Color(233, 225, 216));
		mainFrame.setSize(600, 430);
		mainFrame.setVisible(true);
	}

}
