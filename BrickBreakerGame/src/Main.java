import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame obj = new JFrame();
		GamePlay gamePlay = new GamePlay();
		obj.setBounds(10, 10, 700, 600); //프레임 크기 설정
		obj.setTitle("Brick Breaker"); //제목 설정
		obj.setResizable(false); //창의 크기를 조절할 수 없도록 설정
		obj.setVisible(true); //창을 화면에 나타낼 것인지 설정
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //윈도우창 종료
		obj.add(gamePlay);
		
	}

}