import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class TetrisPanel extends JPanel{

	Thread gameThread;
	BufferedImage off;
	Graphics offG;
	
	Random r;
	int [][] map;
	Color [] colorType;
	
	int blockType;
	int [] blockX;
	int [] blockY;
	int blockPos;
	int xx, yy;
	int score;
	int delayTime;
	int runGame;
	boolean bGame;
	JLabel strScore;
	
	public TetrisPanel() {	}
	public TetrisPanel(Color [] c)
	{
		
		this.colorType = c;
		setLayout(null);
		
		strScore = new JLabel("0");
		strScore.setForeground(Color.white);
		strScore.setBounds(0, 315, 150, 56);
		
		strScore.setHorizontalAlignment(JLabel.RIGHT);
		
		add(strScore); 
		
	}
	
	void initForm()
	{
		
		off = new BufferedImage(15*12+1, 15*21+1, BufferedImage.TYPE_INT_RGB);
		offG = off.getGraphics();
		
		map = new int[12][21];
		blockX = new int[4];
		blockY = new int[4];
		r = new Random();
		
		drawMap();
		drawGrid();
		drawTitle();
		
	}
	public void drawMap()
	{
		for(int i=0; i<12; i++)
		{
			for(int j=0; j<21; j++)
			{
				offG.setColor(colorType[map[i][j]]);
				offG.fillRect(i*15, j*15, 15, 15);
			}
		}
	}
	public void drawGrid()
	{
		offG.setColor(new Color(200, 200, 200));
		
		for(int i=0; i<12; i++)
		{
			for(int j=0; j<21; j++)
			{
				offG.drawRect(i*15, j*15, 15, 15);
			}
		}
	}
	
	public void drawTitle()
	{
		offG.setColor(new Color(230, 230, 230));
		offG.fillRect(27, 121, 126, 69);
		
		offG.setColor(Color.black);
		offG.fillRect(31, 125, 120, 60);
		
		offG.setColor(Color.red);
		offG.drawString("TETRIS", 70, 150);
		
		offG.setColor(Color.yellow);
		offG.drawString("Press START button", 35, 170);
	}

	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(off, 0, 0, this);
	}
	
	
	public void setBlockXY(int type)
	{
		int initData[][][] = {	{ {0,0,0,0},{0,0,0,0} },	{ {6,5,6,7},{0,1,1,1} },
								{ {7,5,6,7},{0,1,1,1} },	{ {5,5,6,7},{0,1,1,1} },
								{ {5,5,6,6},{0,1,1,2} },	{ {6,5,6,5},{0,1,1,2} },
								{ {4,5,6,7},{0,0,0,0} },	{ {5,6,5,6},{0,0,1,1} }	};
		
		for(int i=0; i<4; i++)
		{
			blockX[i] = initData[type][0][i];
			blockY[i] = initData[type][1][i];
		}
	}
	

	
	public void dropBlock()
	{
		removeBlock();
		
		if(checkDrop())
		{
			for(int i=0; i<4; i++)
			{
				blockY[i] = blockY[i]+1;
			}
		}
		else
		{
			drawBlock();
			nextBlock();
		}
		
		score++;
		strScore.setText(String.valueOf(score));
	}
	
	public void delLine()
	{
		boolean delOk;
		for(int row=20; row>=0; row--)
		{
			delOk = true;
			
			for(int col=0; col<12; col++)
			{
				if(map[col][row]==0)
					delOk=false;
			}
			
			if(delOk)
			{
				score+=10;
				strScore.setText(String.valueOf(score));
				
				if(score<1000)
					delayTime = 1000-score;
				else
					delayTime=0;
				
				for(int delRow=row; delRow>0; delRow--)
				{
					for(int delCol=0; delCol<12; delCol++)
					{
						map[delCol][delRow] = map[delCol][delRow-1];
					}
				}
				for(int i=0; i<12; i++)
				{
					map[0][i]=0;
				}
				row++;
			}
			
		}
	}
	public void nextBlock()
	{
		delLine();
		
		blockType = Math.abs(r.nextInt()%7)+1;
		blockPos = 0;
		setBlockXY(blockType);
		checkGameOver();
	}
	public void checkGameOver()
	{
		for(int i=0; i<4; i++)
		{
			if(map[blockX[i]][blockY[i]] != 0)
			{
				bGame = false;
				gameOver();
			}
		}
	}
	
	public void removeBlock()
	{
		for(int i=0; i<4; i++)
		{
			map[blockX[i]][blockY[i]] = 0;
		}
	}
	
	public void drawBlock()
	{
		for(int i=0; i<4; i++)
		{
			map[blockX[i]][blockY[i]] = blockType;
		}
	}
	
	public boolean checkMove(int dir)
	{
		boolean moveOk=true;
		removeBlock();
		
		for(int i=0; i<4; i++)
		{
			if((blockX[i]+dir)>=0 && ((blockX[i]+dir)<12))
			{
				if(map[blockX[i]+dir][blockY[i]]!=0)
					moveOk = false;
			}
			else
			{
				moveOk = false;
			}
		}
		
		if(!moveOk)
			drawBlock();
		
		return moveOk;
	}
	public boolean checkDrop()
	{
		boolean dropOk = true;
		
		for(int i=0; i<4; i++)
		{
			if((blockY[i]+1)<21)
			{
				if(map[blockX[i]][blockY[i]+1]!=0)
					dropOk = false;
			}
			else
			{
				dropOk = false;
			}
		}
		
		return dropOk;
	}
	public boolean checkTurn()
	{
		boolean turnOk = true;
		for(int i=0; i<4; i++)
		{
			if((blockX[i]>=0) && (blockX[i]<12) && (blockY[i]>=0) && blockY[i]<21)
			{
				if(map[blockX[i]][blockY[i]]!=0)
					turnOk = false;
			}
			else
				turnOk = false;
		}
		return turnOk;
	}
	
	public void keyPressed(int keyCode)
	{
		if(keyCode==KeyEvent.VK_LEFT || keyCode==KeyEvent.VK_A)
		{
			if(checkMove(-1))
			{
				for(int i=0; i<4; i++)
				{
					blockX[i] = blockX[i]-1;
				}
			}
		}
		else if(keyCode==KeyEvent.VK_RIGHT || keyCode==KeyEvent.VK_D)
		{
			if(checkMove(1))
			{
				for(int i=0; i<4; i++)
				{
					blockX[i] = blockX[i]+1;
				}
			}
		}
		else if(keyCode==KeyEvent.VK_DOWN || keyCode==KeyEvent.VK_S)
		{
			removeBlock();
			score+=2;
			strScore.setText(String.valueOf(score));
			
			if(checkDrop())
			{
				for(int i=0; i<4; i++)
				{
					blockY[i] = blockY[i]+1;
				}
			}
			else
			{
				drawBlock();
			}
		}
		else if(keyCode==KeyEvent.VK_UP || keyCode==KeyEvent.VK_W)
		{
			int [] tempX = new int[4];
			int [] tempY = new int[4];
			
			for(int i=0; i<4; i++)
			{
				tempX[i] = blockX[i];
				tempY[i] = blockY[i];
			}
			removeBlock();
			
			turnBlock(blockType);
			if(checkTurn())
			{
				if(blockPos<3)
					blockPos++;
				else
					blockPos=0;
			}
			else
			{
				for(int i=0; i<4; i++)
				{
					blockX[i] = tempX[i];
					blockY[i] = tempY[i];
					map[blockX[i]][blockY[i]] = blockType;
				}
			}
		}
		
		drawBlock();
		drawMap();
		drawGrid();
		repaint();
	}
	
	public void turnBlock(int type)
	{
		int turnDataX[][][] = 
			{	{	{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}	},
				{	{0,0,0,-1}, {-1,1,1,0}, {1,0,0,0}, {0,-1,-1,1}	},
				{	{-2,1,0,-1}, {0,0,1,-1}, {1,0,-1,2}, {1,-1,0,0}	},
				{	{1,1,-1,-1}, {-2,-1,1,0}, {1,1,-1,-1}, {0,-1,1,2}	},
				{	{1,2,-1,0}, {-1,-2,1,0}, {1,2,-1,0}, {-1,-2,1,0}	},
				{	{-1,1,0,2}, {1,-1,0,-2}, {-1,1,0,2}, {1,-1,0,-2}	},
				{	{2,1,0,-1}, {-2,-1,0,1}, {2,1,0,-1}, {-2,-1,0,1}	},
				{	{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}	}	};
		
		int turnDataY[][][] = 
			{	{	{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}	},
				{	{0,0,0,1}, {0,-1,-1,-1}, {0,1,1,1}, {0,0,0,-1}	},
				{	{0,-1,0,1}, {0,0,-1,-1}, {0,1,2,1}, {0,0,-1,-1}	},
				{	{0,0,1,1}, {0,-1,-2,-1}, {0,0,1,1}, {0,1,0,-1}	},
				{	{0,-1,0,-1}, {0,1,0,1}, {0,-1,0,-1}, {0,1,0,1}	},
				{	{0,-1,0,-1}, {0,1,0,1}, {0,-1,0,-1}, {0,1,0,1}	},
				{	{0,1,2,3}, {0,-1,-2,-3}, {0,1,2,3}, {0,-1,-2,-3}	},
				{	{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}	}	};
		
		for(int i=0; i<4; i++)
		{
			blockX[i] += turnDataX[type][blockPos][i];
			blockY[i] += turnDataY[type][blockPos][i];
		}
	}
	
	public void gameStart()
	{
		blockType = Math.abs(r.nextInt()%7)+1;
		setBlockXY(blockType);
		blockPos=0;
		
		for(int i=0; i<12; i++)
		{
			for(int j=0; j<21; j++)
			{
				map[i][j]=0;
			}
		}
		
		drawBlock();
		drawMap();
		drawGrid();
		
		score=0;
		delayTime=1000;
		runGame = 1;
		
		gameThread = new game1();
		gameThread.start();
		
		bGame=true;
	}
	public void gameStop()
	{
		bGame = false;
	}
	
	public void gameOver()
	{
		offG.setColor(Color.white);
		offG.fillRect(35, 120, 110, 70);
		offG.setColor(Color.black);
		offG.drawRect(40, 125, 100, 60);
		offG.setColor(Color.red);
		offG.drawString("Game Over !", 56, 150);
		offG.setColor(Color.blue);
		offG.drawString("Score: "+ score, 56, 170);
	}
	
	class game1 extends Thread
	{
		public void run()
		{
			while(bGame)
			{
				try
				{
					Thread.sleep(delayTime);
				}
				catch(InterruptedException ie) {	}
				
				dropBlock();
				drawBlock();
				drawMap();
				drawGrid();
				repaint();
			}
			gameOver();
		}
	}
}
