import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame obj = new JFrame();
		GamePlay gamePlay = new GamePlay();
		obj.setBounds(10, 10, 700, 600); //������ ũ�� ����
		obj.setTitle("Brick Breaker"); //���� ����
		obj.setResizable(false); //â�� ũ�⸦ ������ �� ������ ����
		obj.setVisible(true); //â�� ȭ�鿡 ��Ÿ�� ������ ����
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������â ����
		obj.add(gamePlay);
		
	}

}