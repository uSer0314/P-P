import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Setting {
	
	// 各項目の縦の長さ
	private static final int ITEM_SIZE_Y = 25;

	// "YY年XX月ZZ日"を表示するラベルの設定
	public static void setLabel_date(JLabel label) {
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		label.setHorizontalAlignment(JLabel.CENTER);		// 中央揃えにする
		label.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));	// 横の長さの設定
	}

    // "追加（＋）ボタン"の設定
    public static void setButton_add(JButton btn){
    	btn.setText("＋");
    	btn.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));	// フォントとフォントサイズ
        btn.setBackground(Color.WHITE);		// 背景色
		btn.setPreferredSize(new Dimension(30, 30));	// サイズ
		btn.setMargin(new Insets(0, 0, 0, 0));

    }

    // "削除（×）ボタン"の設定
    public static void setButton_del(JButton btn){
    	btn.setIcon(new ImageIcon("image/batsu.png"));	// 画像ファイルの指定
    	//btn.setBorderPainted(false);		// 枠線を消す
    	btn.setBackground(Color.WHITE);		// 背景色
		btn.setPreferredSize(new Dimension(25, ITEM_SIZE_Y));	// 横の長さの設定
		btn.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// 縦の長さの設定
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setFocusPainted(false);
    }

    // "収入or支出"欄の設定	①
    public static void setLabel_type(JLabel label){
    	label.setPreferredSize(new Dimension(80, ITEM_SIZE_Y));		// 横の長さの設定
    	label.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// 縦の長さの設定
		label.setHorizontalAlignment(JLabel.CENTER);		// 中央揃えにする
    }

    // "金額"欄の設定	②
    public static void setLabel_money(JLabel label){
    	label.setPreferredSize(new Dimension(80, ITEM_SIZE_Y));		// 横の長さの設定
    	label.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// 縦の長さの設定
		label.setHorizontalAlignment(JLabel.RIGHT);			// 右揃えにする
    }

    // "費目"欄の設定	③
    public static void setLabel_use(JLabel label){
    	label.setPreferredSize(new Dimension(130, ITEM_SIZE_Y));	// 横の長さの設定
    	label.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// 縦の長さの設定
    }

    // "コメント"欄の設定		④
    public static void setLabel_comment(JLabel label){
		label.setMaximumSize(new Dimension(Short.MAX_VALUE, ITEM_SIZE_Y));	// コメントのサイズを可変長にする
    }

    // ①～④の共通設定
    public static void setLabel_common(JLabel label) {
    	label.setBorder(new LineBorder(Color.BLACK));		// 枠線をつける
		label.setOpaque(true);	// 背景の非透明化
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20)); // フォントとフォントサイズ
    }

    // パネル１(p1)の設定
    public static void setPanel_p1(JPanel p) {
    	p.setOpaque(false);	// 背景の透明化
    	// --- 枠線の設定群 --
    	Border oldBorder = p.getBorder();
    	Border redBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY);
    	Border newBorder = BorderFactory.createCompoundBorder(redBorder, oldBorder);
    	p.setBorder(newBorder);
    	// --------------------
    	//p.getInsets(new Insets(0, 0, 0, 0));	// 余白をなくす？
    }
    // パネル２(p2)の設定
    public static void setPanel_p2(JPanel p) {
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));	//	部品が上から順に並ぶようにする
		p.setOpaque(false);	// 背景の透明化
    }
    // パネル２_１(p2_1)の設定
    public static void setPanel_p2_1(JPanel p) {
    	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));	// 部品が左から順に並ぶようにする
    	p.setOpaque(false);	// 背景の透明化
    }
    // パネル２_２(p2_2)の設定
    public static void setPanel_p2_2(JPanel p) {
    	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));	//	部品が上から順に並ぶようにする
    	p.setOpaque(false);	// 背景の透明化
    }
    // パネル２_２_１(p2_2_1)の設定
    public static void setPanel_p2_2_1(JPanel p) {
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));	// 部品が左から順に並ぶようにする
		p.setOpaque(false);	// 背景の透明化
    }
    // パネル３(p3)の設定
    public static void setPanel_p3(JPanel p) {
		p.setOpaque(false);		// 背景の透明化
		p.setLayout(new FlowLayout(FlowLayout.RIGHT));	// 部品が右から順に並ぶようにする
    }
    // スクロールの設定
    public static void setScroll_sp(JScrollPane sp) {
		sp.setBorder(null);	// 枠線を消す
		sp.setPreferredSize(new Dimension(250, 130));	// サイズ
		sp.getViewport().setBackground(Color.white);		// 背景色
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    // 全体画面の設定
    public static void setFrame_cn(Container cn) {
    	cn.setBackground(Color.white);	// 背景色
    	
    }
}