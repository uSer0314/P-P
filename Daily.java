import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author 183107 ���V
 *
 *         ���ʂ̉�ʂ�\������N���X
 *
 */
public class Daily  extends JFrame {

	// ���p����f�[�^�x�[�X�t�@�C��
	String DBNAME = "jdbc:sqlite:"+ "database/kakeibo.db";
	// Array���X�g���g�p
	List<String[]> test_list = new ArrayList<>();
	// �f�[�^�x�[�X�R�l�N�V����
	Connection conn;
	

	Daily(Calendar cl) {

		// �f�[�^�x�[�X�ɐڑ�����
        try {
        	conn = DriverManager.getConnection(DBNAME);
        } catch (SQLException err) {
            System.out.println(err);
        }
		setTitle("����");		// �^�C�g��
		setBounds(100, 100, 700, 500);		// �E�B���h�E�̑傫��
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ���t�f�[�^��SQL�p�ɕϊ�
        Date date = new Date(cl.getTime().getTime());
        
		// �p�l��1
		JPanel p1 = new JPanel();
		Setting.setPanel_p1(p1);
		// �p�l��2
		JPanel p2 = new JPanel();
		Setting.setPanel_p2(p2);
		// �p�l��2_1
		JPanel p2_1 = new JPanel();
		Setting.setPanel_p2_1(p2_1);
		// �p�l��2_2
		JPanel p2_2 = new JPanel();
		Setting.setPanel_p2_2(p2_2);
		// �p�l��3
		JPanel p3 = new JPanel();
		Setting.setPanel_p3(p3);
		// �X�N���[���̐ݒ�
		JScrollPane sp = new JScrollPane(p2_2);
		Setting.setScroll_sp(sp);
		
		p2.add(p2_1);
		p2.add(sp);
		
		// ���ږ��̐ݒ�
		JLabel label[] = { new JLabel("           "), new JLabel("�^�C�v"), new JLabel("���z"),
				new JLabel("���"), new JLabel("����"), new JLabel("                 ") };
		// �ݒ�̔��f
		Setting.setLabel_type(label[1]);
		Setting.setLabel_money(label[2]);
		Setting.setLabel_use(label[3]);
		Setting.setLabel_comment(label[4]);
		
		for(JLabel l : label) {
			p2_1.add(l);
		}
		for(int i=1; i<5; i++) {
			label[i].setHorizontalAlignment(JLabel.CENTER);		// ���������ɂ���
			label[i].setFont(new Font("",Font.BOLD, 17));
			label[i].setBorder(new LineBorder(Color.BLACK));
		}


		// ���t�t�H�[�}�b�g�̐ݒ�
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy�NMM��dd��");
		JLabel label_date = new JLabel(sdf.format(cl.getTime()));
		Setting.setLabel_date(label_date);
		p1.add(label_date);


		// �ǉ�(�{)�{�^���̃N���b�N���ݒ�
		JButton btn_add = new JButton();
		Setting.setButton_add(btn_add);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selectvalues[] = {"����","�x�o"};

				Object value = JOptionPane.showInputDialog(p2_2, "��ނ�I�����Ă�������", "�ǉ�",
						JOptionPane.ERROR_MESSAGE,
						new ImageIcon(),selectvalues, selectvalues[0]);
				String yen = JOptionPane.showInputDialog(p2_2, "���z����͂��Ă�������");
				String usage = JOptionPane.showInputDialog(p2_2, "�p�r����͂��Ă�������");
				String comment = JOptionPane.showInputDialog(p2_2, "�R�����g����͂��Ă�������");

				yen = yen.equals("")?"0�~":yen;
				usage = usage.equals("")?"�@":usage;

				// �f�[�^�x�[�X�Ƀ��R�[�h����ǉ�
		        try {
		            java.sql.Statement stmt = conn.createStatement();
		            //�@���̘A�Ԃ��擾
		            ResultSet rs = stmt.executeQuery("Select seq from sqlite_sequence;");
		            Integer num = rs.getInt("seq") + 1;
		            String sql = "INSERT INTO Daily(date,type,amount_of_money,use,comment,create_time) "
		            		+String.format("Values('%s','%s',%s,'%s','%s',CURRENT_TIMESTAMP);",date,(String)value,yen,usage,comment);

		            stmt.executeUpdate(sql);

					String[] recode = {num.toString(),(String)value,yen,usage,comment};
					test_list.add(recode);
					p2_2.add(addRecode(recode));

		        } catch (SQLException err) {
		            System.out.println(err);
		        }

				// �ĕ`��
				revalidate();
			}
		});
		p3.add(new JLabel("                 "));
		p3.add(btn_add);

		// �f�[�^�x�[�X����K�v�ȃf�[�^���擾
        try {
            java.sql.Statement stmt = conn.createStatement();
            String sql = "SELECT id, type, amount_of_money, use, comment from Daily";
            sql = sql + String.format(" WHERE date = '%s'", date);
            ResultSet rs = stmt.executeQuery(sql);
            // sql�����s�������ʂ�Arraylist�ɒǉ�
            while(rs.next()) {
            	String[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
            	test_list.add(data);
            }
        } catch (SQLException err) {
            System.out.println(err);
        }

		for (int i = 0; i < test_list.size(); i++) {
			p2_2.add(addRecode(test_list.get(i)));
		}

		p3.add(btn_add);
		Setting.setFrame_cn(getContentPane());
		getContentPane().add(p1, BorderLayout.NORTH);
		getContentPane().add(p2, BorderLayout.CENTER);
		getContentPane().add(p3, BorderLayout.SOUTH);
	}

	/**
	 * ���R�[�h��ǉ�����Ƃ��ɌĂяo�����\�b�h
	 * @param data
	 * @return JPanel
	 */
	JPanel addRecode(String[] data) {
		JPanel p2_2_1 = new JPanel();
		Setting.setPanel_p2_2_1(p2_2_1);

		// �e�R���|�[�l���g�̐���
		JComponent component[] = { new JLabel("           "), new JLabel(data[1]), new JLabel(data[2]),
				new JLabel(data[3]), new JLabel(data[4]), new JButton(),
				new JLabel("           ") };
		
		(new myListener()).update_component(p2_2_1 ,component);

		// �e���x���̔w�i�F��g���̐ݒ�
		for (int j = 1; j < 5; j++) {
			if(data[1].equals("����")) {
				component[j].setBackground(new Color(220,220,255));

			}else {
				component[j].setBackground(new Color(255,220,220));

			}
			Setting.setLabel_common((JLabel)component[j]);
		}

		// �ݒ�̔��f
		Setting.setLabel_type((JLabel)component[1]);
		Setting.setLabel_money((JLabel)component[2]);
		Setting.setLabel_use((JLabel)component[3]);
		Setting.setLabel_comment((JLabel)component[4]);
		Setting.setButton_del((JButton)component[5]);

		// �폜�{�^���������Ƃ��̏���
		((JButton)component[5]).addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e) {

				  int option = JOptionPane.showConfirmDialog(p2_2_1, "�{���ɍ폜���Ă���낵���ł����H");
				  if (option == JOptionPane.YES_OPTION){

						// �f�[�^�x�[�X����f�[�^���폜
				        try  {
				            java.sql.Statement stmt = conn.createStatement();
				            String sql = String.format("Delete from Daily WHERE id = %s", data[0]);
				            stmt.executeUpdate(sql);
				        } catch (SQLException err) {
				            System.out.println(err);
				        }

					  	// �폜
				        p2_2_1.removeAll();
						// �ĕ`��
						revalidate();
				  }else if (option == JOptionPane.NO_OPTION){

				  }else if (option == JOptionPane.CANCEL_OPTION){

				  }
			  }
		});

		// �e�R���|�[�l���g���p�l���ɒǉ�
		for (JComponent l : component) {
			p2_2_1.add(l);
		}
		return p2_2_1;
	}
	
	// �g����ݒ肷�郁�\�b�h
	public static void my_setBorder(JComponent component, int a, int b, int c, int d, Color color) {
    	Border oldBorder = component.getBorder();
    	Border redBorder = BorderFactory.createMatteBorder(a, b, c, d, color);
    	Border newBorder = BorderFactory.createCompoundBorder(redBorder, oldBorder);
    	component.setBorder(newBorder);
	}
	
	// �}�E�X�C�x���g�̐ݒ�
	public class myListener extends MouseAdapter{
		
			JComponent[] c;
			public void update_component(JPanel p,JComponent[] c) {
				this.c = c;
				//p.addMouseListener(this);
				for(int i=1;i<c.length-1; i++) {
					c[i].addMouseListener(this);
				}
				
			}
		    public void mouseEntered(MouseEvent e){

		    	
				// �e���x���̔w�i�F��g���̐ݒ�
				for (int j = 1; j < 5; j++) {
					
					if(((JLabel)c[1]).getText().equals("����")) {
						c[j].setBackground(new Color(200,200,255));

					}else {
						c[j].setBackground(new Color(255,200,200));
					}
				}
				// �g���̑���
				int b = 1;
				Color color = Color.BLACK;
				my_setBorder(c[1],b, b, b, 0, color);
				my_setBorder(c[2],b, 0, b, 0, color);
				my_setBorder(c[3],b, 0, b, 0, color);
				my_setBorder(c[4],b, 0, b, b, color);
		    }
		    public void mouseExited(MouseEvent e){
				// �e���x���̔w�i�F��g���̐ݒ�
				for (int j = 1; j < 5; j++) {
				
					if(((JLabel)c[1]).getText().equals("����")) {
						c[j].setBackground(new Color(220,220,255));

					}else {
						c[j].setBackground(new Color(255,220,220));

					}
					
					c[j].setBorder(new LineBorder(Color.BLACK));
				}
		      }
	}

}