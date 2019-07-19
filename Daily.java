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
 * @author 183107 米澤
 *
 *         日別の画面を表示するクラス
 *
 */
public class Daily  extends JFrame {

	// 利用するデータベースファイル
	String DBNAME = "jdbc:sqlite:"+ "database/kakeibo.db";
	// Arrayリストを使用
	List<String[]> test_list = new ArrayList<>();
	// データベースコネクション
	Connection conn;
	

	Daily(Calendar cl) {

		// データベースに接続する
        try {
        	conn = DriverManager.getConnection(DBNAME);
        } catch (SQLException err) {
            System.out.println(err);
        }
		setTitle("日別");		// タイトル
		setBounds(100, 100, 700, 500);		// ウィンドウの大きさ
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 日付データをSQL用に変換
        Date date = new Date(cl.getTime().getTime());
        
		// パネル1
		JPanel p1 = new JPanel();
		Setting.setPanel_p1(p1);
		// パネル2
		JPanel p2 = new JPanel();
		Setting.setPanel_p2(p2);
		// パネル2_1
		JPanel p2_1 = new JPanel();
		Setting.setPanel_p2_1(p2_1);
		// パネル2_2
		JPanel p2_2 = new JPanel();
		Setting.setPanel_p2_2(p2_2);
		// パネル3
		JPanel p3 = new JPanel();
		Setting.setPanel_p3(p3);
		// スクロールの設定
		JScrollPane sp = new JScrollPane(p2_2);
		Setting.setScroll_sp(sp);
		
		p2.add(p2_1);
		p2.add(sp);
		
		// 項目名の設定
		JLabel label[] = { new JLabel("           "), new JLabel("タイプ"), new JLabel("金額"),
				new JLabel("費目"), new JLabel("メモ"), new JLabel("                 ") };
		// 設定の反映
		Setting.setLabel_type(label[1]);
		Setting.setLabel_money(label[2]);
		Setting.setLabel_use(label[3]);
		Setting.setLabel_comment(label[4]);
		
		for(JLabel l : label) {
			p2_1.add(l);
		}
		for(int i=1; i<5; i++) {
			label[i].setHorizontalAlignment(JLabel.CENTER);		// 中央揃えにする
			label[i].setFont(new Font("",Font.BOLD, 17));
			label[i].setBorder(new LineBorder(Color.BLACK));
		}


		// 日付フォーマットの設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		JLabel label_date = new JLabel(sdf.format(cl.getTime()));
		Setting.setLabel_date(label_date);
		p1.add(label_date);


		// 追加(＋)ボタンのクリック時設定
		JButton btn_add = new JButton();
		Setting.setButton_add(btn_add);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selectvalues[] = {"収入","支出"};

				Object value = JOptionPane.showInputDialog(p2_2, "種類を選択してください", "追加",
						JOptionPane.ERROR_MESSAGE,
						new ImageIcon(),selectvalues, selectvalues[0]);
				String yen = JOptionPane.showInputDialog(p2_2, "金額を入力してください");
				String usage = JOptionPane.showInputDialog(p2_2, "用途を入力してください");
				String comment = JOptionPane.showInputDialog(p2_2, "コメントを入力してください");

				yen = yen.equals("")?"0円":yen;
				usage = usage.equals("")?"　":usage;

				// データベースにレコード情報を追加
		        try {
		            java.sql.Statement stmt = conn.createStatement();
		            //　次の連番を取得
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

				// 再描画
				revalidate();
			}
		});
		p3.add(new JLabel("                 "));
		p3.add(btn_add);

		// データベースから必要なデータを取得
        try {
            java.sql.Statement stmt = conn.createStatement();
            String sql = "SELECT id, type, amount_of_money, use, comment from Daily";
            sql = sql + String.format(" WHERE date = '%s'", date);
            ResultSet rs = stmt.executeQuery(sql);
            // sqlを実行した結果をArraylistに追加
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
	 * レコードを追加するときに呼び出すメソッド
	 * @param data
	 * @return JPanel
	 */
	JPanel addRecode(String[] data) {
		JPanel p2_2_1 = new JPanel();
		Setting.setPanel_p2_2_1(p2_2_1);

		// 各コンポーネントの生成
		JComponent component[] = { new JLabel("           "), new JLabel(data[1]), new JLabel(data[2]),
				new JLabel(data[3]), new JLabel(data[4]), new JButton(),
				new JLabel("           ") };
		
		(new myListener()).update_component(p2_2_1 ,component);

		// 各ラベルの背景色や枠線の設定
		for (int j = 1; j < 5; j++) {
			if(data[1].equals("収入")) {
				component[j].setBackground(new Color(220,220,255));

			}else {
				component[j].setBackground(new Color(255,220,220));

			}
			Setting.setLabel_common((JLabel)component[j]);
		}

		// 設定の反映
		Setting.setLabel_type((JLabel)component[1]);
		Setting.setLabel_money((JLabel)component[2]);
		Setting.setLabel_use((JLabel)component[3]);
		Setting.setLabel_comment((JLabel)component[4]);
		Setting.setButton_del((JButton)component[5]);

		// 削除ボタン押したときの処理
		((JButton)component[5]).addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e) {

				  int option = JOptionPane.showConfirmDialog(p2_2_1, "本当に削除してもよろしいですか？");
				  if (option == JOptionPane.YES_OPTION){

						// データベースからデータを削除
				        try  {
				            java.sql.Statement stmt = conn.createStatement();
				            String sql = String.format("Delete from Daily WHERE id = %s", data[0]);
				            stmt.executeUpdate(sql);
				        } catch (SQLException err) {
				            System.out.println(err);
				        }

					  	// 削除
				        p2_2_1.removeAll();
						// 再描画
						revalidate();
				  }else if (option == JOptionPane.NO_OPTION){

				  }else if (option == JOptionPane.CANCEL_OPTION){

				  }
			  }
		});

		// 各コンポーネントをパネルに追加
		for (JComponent l : component) {
			p2_2_1.add(l);
		}
		return p2_2_1;
	}
	
	// 枠線を設定するメソッド
	public static void my_setBorder(JComponent component, int a, int b, int c, int d, Color color) {
    	Border oldBorder = component.getBorder();
    	Border redBorder = BorderFactory.createMatteBorder(a, b, c, d, color);
    	Border newBorder = BorderFactory.createCompoundBorder(redBorder, oldBorder);
    	component.setBorder(newBorder);
	}
	
	// マウスイベントの設定
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

		    	
				// 各ラベルの背景色や枠線の設定
				for (int j = 1; j < 5; j++) {
					
					if(((JLabel)c[1]).getText().equals("収入")) {
						c[j].setBackground(new Color(200,200,255));

					}else {
						c[j].setBackground(new Color(255,200,200));
					}
				}
				// 枠線の太さ
				int b = 1;
				Color color = Color.BLACK;
				my_setBorder(c[1],b, b, b, 0, color);
				my_setBorder(c[2],b, 0, b, 0, color);
				my_setBorder(c[3],b, 0, b, 0, color);
				my_setBorder(c[4],b, 0, b, b, color);
		    }
		    public void mouseExited(MouseEvent e){
				// 各ラベルの背景色や枠線の設定
				for (int j = 1; j < 5; j++) {
				
					if(((JLabel)c[1]).getText().equals("収入")) {
						c[j].setBackground(new Color(220,220,255));

					}else {
						c[j].setBackground(new Color(255,220,220));

					}
					
					c[j].setBorder(new LineBorder(Color.BLACK));
				}
		      }
	}

}