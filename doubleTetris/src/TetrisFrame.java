import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisFrame extends JFrame implements ActionListener{
	JButton gameStartBt;
	JButton gameStopBt;
	JLabel imgLabel;
	JPanel topPanel;
	JPanel bottomPanel;
	TetrisPanel leftPanel;
	TetrisPanel rightPanel;
	Color [] colorType;
	
	
	public TetrisFrame() {	}
	public TetrisFrame(String title)
	{
		super(title);
		colorType = new Color[8];
		setColorType();
		
		setLayout(null);
		
		imgLabel = new JLabel();
		
		ImageIcon logo = new ImageIcon(TetrisFrame.class.getResource("/image/logo.png"));
		
		Image img = logo.getImage();
		Image changeImg = img.getScaledInstance(165, 100, Image.SCALE_SMOOTH);
		ImageIcon changeLogo = new ImageIcon(changeImg);
		
		imgLabel.setIcon(changeLogo);
		
		imgLabel.setBounds(210, 30, 165, 150);
		imgLabel.setHorizontalAlignment(JLabel.CENTER);
		
		topPanel = new JPanel();
		topPanel.setBounds(210, 30, 165, 150);
		topPanel.setBackground(getForeground());
		topPanel.add(imgLabel);
		
		getContentPane().add(topPanel);
		
		leftPanel = new TetrisPanel(colorType);
		leftPanel.setBounds(10, 10, 183, 370);
		leftPanel.setBackground(new Color(158, 144, 135));
		leftPanel.initForm();
		getContentPane().add(leftPanel);
		
		rightPanel = new TetrisPanel(colorType);
		rightPanel.setBounds(394, 10, 183, 370);
		rightPanel.setBackground(new Color(134, 118, 108));
		rightPanel.initForm();
		getContentPane().add(rightPanel);
		
		bottomPanel = new JPanel();
		bottomPanel.setBounds(237, 280, 110, 70);
		bottomPanel.setBackground(new Color(239,234,227));	
		
		gameStartBt = new JButton("Game Start");
		gameStartBt.addActionListener(this);
		gameStartBt.setBackground(new Color(247, 210, 121));
		bottomPanel.add(gameStartBt);
		
		gameStopBt = new JButton("Game Stop");
		gameStopBt.addActionListener(this);
		gameStopBt.setBackground(new Color(247, 210, 121));
		bottomPanel.add(gameStopBt);

		getContentPane().add(bottomPanel);
		
		addKeyListener(new MyKeyHandler1());
	}
	
	void setColorType()
	{
		colorType[0] = new Color(77, 73, 73);
		colorType[1] = new Color(212, 166, 145);
		colorType[2] = new Color(170, 200, 199);
		colorType[3] = new Color(119, 171 , 131);
		colorType[4] = new Color(200, 170, 171);
		colorType[5] = new Color(135, 149, 158);
		colorType[6] = new Color(190, 183, 114);
		colorType[7] = new Color(173, 150, 176);
	}
	void setEnabledStartButton(Boolean bool)
	{
		gameStartBt.setEnabled(bool);
		gameStopBt.setEnabled(!bool);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == gameStartBt)
		{
			leftPanel.gameStart();
			rightPanel.gameStart();
			this.requestFocus();
		}
		else
		{
			leftPanel.gameStop();
			rightPanel.gameStop();
		}
	}
	
	class MyKeyHandler1 extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			int keyCode = (int)e.getKeyCode();
			
			if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_W)
			{
				leftPanel.keyPressed(keyCode);
			}
			
			if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP)
			{
				rightPanel.keyPressed(keyCode);
			}
			
		}
	}
}
