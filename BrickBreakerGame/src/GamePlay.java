import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener  {
	private boolean play = false; //게임 실행 여부
	private int score = 0; //점수
	
	private int totalBricks = 34; //블럭 개수 
	
	//실행 간격 설정
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310; //플레어어 위치
	private int playerBarScale = 2; //플레어어바 크기
	
	private int ballposX = 120; //공위치
	private int ballposY = 350;
	private int ballXdir = -1; //공의 방향
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public GamePlay() { //게임 시작
		map = new MapGenerator(4, 9);
		addKeyListener(this);
		setFocusable(true); //키 이벤트의 포커스를 받을 수 있는 컴포넌트가 여러개 있을 때 우선적으로 입력받기 위해 설정
		setFocusTraversalKeysEnabled(false); //텍스트필드에서 탭키를 이용해 이동을 할 것인지를 설정
		timer = new Timer(delay, this);
		timer.start();
		
	}
	public void paint(Graphics g) { //그래픽 설정
		
		g.setColor(Color.white); //배경
		g.fillRect(1, 1, 692, 592);
		
		map.draw((Graphics2D)g); //블럭
		
		g.setColor(Color.yellow); //테두리
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		g.setColor(Color.blue); //플레이어
		g.fillRect(playerX, 550, 50 * playerBarScale, 8);
		
		g.setColor(Color.green); //공
		g.fillOval((int)ballposX, (int)ballposY, 20, 20);
		
		g.setColor(Color.black); //점수 폰트
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		if (totalBricks <= 0) { //블럭 개수가 0일때 중단하고 게임 클리어 + 재시작
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart.", 230, 350);
		}
		
		if(ballposY > 570) { //공이 바닥으로 떨어지면 게임오버
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart.", 230, 350);	
		} 
		
		g.dispose(); //현재 프레임 종료
	
	}

	//이벤트가 발생했을 때 제공할 기능 구현
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			// Ball - Pedal  interaction 
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 50 * playerBarScale, 8))) {
				ballYdir = - ballYdir; //공과 플레이어가 충돌시 공의 방향 반대로 설정
			}
			for( int i = 0; i<map.map.length; i++) { //공과 벽돌의 위치와 충돌 범위 설정
				for(int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80; //80
						int brickY = i*map.brickHeight + 50; //50
						int brickWidth= map.brickWidth;
						int brickHeight = map.brickHeight;						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect) ) { //공과 벽돌이 충돌시 해당 벽돌을 없애고 점수추가
							if(map.mapColor[i][j] != 4) {
								map.setBrickValue(0, i, j);
								totalBricks--;
								score+=5;
							}
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir; //벽의 옆면 부분에 공이 충돌시 공의 방향 반대로 설정
							}
							 else {
								ballYdir = -ballYdir; //벽의 윗면, 아랫면 부분에 공이 충돌시 공의 방향 반대로 설정
							}
						}
						
					}
					
				}
			}
			
			//현재 방향으로 계속 이동
			ballposX += ballXdir;
			ballposY += ballYdir;
			//화면 끝부분에 닿을시 방향 전환 
			if(ballposX < 0) { //공이 맨 왼쪽에 닿을시 방향전환
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) { //공이 맨 윗부분에 닿을시 방향전환
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) { //공이 맨 오른쪽에 닿을시 방향전환
				ballXdir = -ballXdir;  
			}
			if(score >= 100)
				playerBarScale = 1;
			
		}
		
		repaint(); //컴포넌트를 다시 페인트

	}
	
	//문자를 눌렀을 때 호출, 문자키에만 반응
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	//키보드를 눌렀을 때 호출, 모든 키보드에 반응
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) { //플레이어 오른쪽으로 이동
			if(playerX >= 600) { //벽에 막힘 설정
				if(playerBarScale == 1) 
					playerX = 650;
				else
					playerX = 600;
			} else {
				moveRight();
					
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT) { //플레이어 왼쪽으로 이동
			if(playerX < 10) { //벽에 막힘 설정
				playerX = 10;
			} else {
				moveLeft();
					
			}
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) { //Enter누를시 재시작 
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				score = 0;
				totalBricks = 34;
				playerBarScale = 2;
				map = new MapGenerator(4, 9);
				
				repaint();
			}
		}
		
	}	
		public void moveRight() { //플레이어 오른쪽으로 이동량 설정
			play = true;
			playerX += 20;
		}
		public void moveLeft() { //플레이어 왼쪽으로 이동량 설정
			play = true;
			playerX -= 20;
		}
	//키보드를 땟을 때 호출, 모든 키보드에 반응
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}