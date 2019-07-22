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
	
	// �e���ڂ̏c�̒���
	private static final int ITEM_SIZE_Y = 25;

	// "YY�NXX��ZZ��"��\�����郉�x���̐ݒ�
	public static void setLabel_date(JLabel label) {
		label.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 20));
		label.setHorizontalAlignment(JLabel.CENTER);		// ���������ɂ���
		label.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));	// ���̒����̐ݒ�
	}

    // "�ǉ��i�{�j�{�^��"�̐ݒ�
    public static void setButton_add(JButton btn){
    	btn.setText("�{");
    	btn.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 20));	// �t�H���g�ƃt�H���g�T�C�Y
        btn.setBackground(Color.WHITE);		// �w�i�F
		btn.setPreferredSize(new Dimension(30, 30));	// �T�C�Y
		btn.setMargin(new Insets(0, 0, 0, 0));

    }

    // "�폜�i�~�j�{�^��"�̐ݒ�
    public static void setButton_del(JButton btn){
    	btn.setIcon(new ImageIcon("image/batsu.png"));	// �摜�t�@�C���̎w��
    	//btn.setBorderPainted(false);		// �g��������
    	btn.setBackground(Color.WHITE);		// �w�i�F
		btn.setPreferredSize(new Dimension(25, ITEM_SIZE_Y));	// ���̒����̐ݒ�
		btn.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// �c�̒����̐ݒ�
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setFocusPainted(false);
    }

    // "����or�x�o"���̐ݒ�	�@
    public static void setLabel_type(JLabel label){
    	label.setPreferredSize(new Dimension(80, ITEM_SIZE_Y));		// ���̒����̐ݒ�
    	label.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// �c�̒����̐ݒ�
		label.setHorizontalAlignment(JLabel.CENTER);		// ���������ɂ���
    }

    // "���z"���̐ݒ�	�A
    public static void setLabel_money(JLabel label){
    	label.setPreferredSize(new Dimension(80, ITEM_SIZE_Y));		// ���̒����̐ݒ�
    	label.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// �c�̒����̐ݒ�
		label.setHorizontalAlignment(JLabel.RIGHT);			// �E�����ɂ���
    }

    // "���"���̐ݒ�	�B
    public static void setLabel_use(JLabel label){
    	label.setPreferredSize(new Dimension(130, ITEM_SIZE_Y));	// ���̒����̐ݒ�
    	label.setMaximumSize(new Dimension(0, ITEM_SIZE_Y));		// �c�̒����̐ݒ�
    }

    // "�R�����g"���̐ݒ�		�C
    public static void setLabel_comment(JLabel label){
		label.setMaximumSize(new Dimension(Short.MAX_VALUE, ITEM_SIZE_Y));	// �R�����g�̃T�C�Y���ϒ��ɂ���
    }

    // �@�`�C�̋��ʐݒ�
    public static void setLabel_common(JLabel label) {
    	label.setBorder(new LineBorder(Color.BLACK));		// �g��������
		label.setOpaque(true);	// �w�i�̔񓧖���
		label.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 20)); // �t�H���g�ƃt�H���g�T�C�Y
    }

    // �p�l���P(p1)�̐ݒ�
    public static void setPanel_p1(JPanel p) {
    	p.setOpaque(false);	// �w�i�̓�����
    	// --- �g���̐ݒ�Q --
    	Border oldBorder = p.getBorder();
    	Border redBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY);
    	Border newBorder = BorderFactory.createCompoundBorder(redBorder, oldBorder);
    	p.setBorder(newBorder);
    	// --------------------
    	//p.getInsets(new Insets(0, 0, 0, 0));	// �]�����Ȃ����H
    }
    // �p�l���Q(p2)�̐ݒ�
    public static void setPanel_p2(JPanel p) {
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));	//	���i���ォ�珇�ɕ��Ԃ悤�ɂ���
		p.setOpaque(false);	// �w�i�̓�����
    }
    // �p�l���Q_�P(p2_1)�̐ݒ�
    public static void setPanel_p2_1(JPanel p) {
    	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));	// ���i�������珇�ɕ��Ԃ悤�ɂ���
    	p.setOpaque(false);	// �w�i�̓�����
    }
    // �p�l���Q_�Q(p2_2)�̐ݒ�
    public static void setPanel_p2_2(JPanel p) {
    	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));	//	���i���ォ�珇�ɕ��Ԃ悤�ɂ���
    	p.setOpaque(false);	// �w�i�̓�����
    }
    // �p�l���Q_�Q_�P(p2_2_1)�̐ݒ�
    public static void setPanel_p2_2_1(JPanel p) {
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));	// ���i�������珇�ɕ��Ԃ悤�ɂ���
		p.setOpaque(false);	// �w�i�̓�����
    }
    // �p�l���R(p3)�̐ݒ�
    public static void setPanel_p3(JPanel p) {
		p.setOpaque(false);		// �w�i�̓�����
		p.setLayout(new FlowLayout(FlowLayout.RIGHT));	// ���i���E���珇�ɕ��Ԃ悤�ɂ���
    }
    // �X�N���[���̐ݒ�
    public static void setScroll_sp(JScrollPane sp) {
		sp.setBorder(null);	// �g��������
		sp.setPreferredSize(new Dimension(250, 130));	// �T�C�Y
		sp.getViewport().setBackground(Color.white);		// �w�i�F
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    // �S�̉�ʂ̐ݒ�
    public static void setFrame_cn(Container cn) {
    	cn.setBackground(Color.white);	// �w�i�F
    	
    }
}