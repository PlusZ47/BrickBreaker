import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	
	public int map [][];
	public int brickWidth;
	public int brickHeight;
	
	public MapGenerator(int row, int col) { //블럭 생성
		map = new int [row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j=0; j< map[0].length;j++) {
				map[i][j] = 1;
			}
		}
		
		brickWidth = 540/col; //블럭 너비
		brickHeight = 150/row; //블럭 높이
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j=0; j< map[0].length;j++) {
				if(map[i][j] > 0) { //map[i][j]이 0보다 클때 블럭 위치와 크기 설정
					g.setColor(Color.black);
					g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3)); //블럭 외각선 설정
					g.setColor(Color.white);
					g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
				}
			}
			
		}
		
		}
	//해당 블럭 값 설정	
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}

}
