import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import javax.sound.sampled.ReverbType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;
import javax.swing.text.Document;
import javax.swing.border.*;
import javax.swing.ImageIcon;
import java.awt.Image;
public class CalendarTest extends JFrame implements ActionListener,
WindowListener,MouseListener{
CalendarTest() {

}

/*
* �J�����_�[�̍l�����̃��� get�ō��������߂��Bset�ŔC�ӂɐݒ�ł���B ���̏��߂̗j�������߂܂��B calendar.set(year,
* month - 1, 1); // ����: 1��: 0, 2��: 1, ... startDay =
* calendar.get(Calendar.DAY_OF_WEEK); �����̓��t�����߂܂��B
* calendar.add(Calendar.MONTH, 1); calendar.add(Calendar.DATE, -1);
* lastDate = calendar.get(Calendar.DATE);
*/

JPanel calendarpane, backpane;

JButton dayhozon;

// ���ԃJ�����_�[�̍쐬��
File yeardir;
File monthdir;
File daysfile;

static int a;// �Z�b�g����N
static int b;
static int c = 1;
static int state = 0;
// �N����
static File HMCalendar;
{
// �Z�b�g���錎

this.setSize(1280, 960);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
this.setLayout(new BorderLayout());
this.addWindowListener(this);
this.addComponentListener(null);

}

public CalendarTest(String title) { 
}

public void syori() {
String[] dayweek = { "���j��", "���j��", "�Ηj��", "���j��", "�ؗj��", "���j��", "�y�j��" };

// JPanel backpane = new JPanel(new BorderLayout()); // �w�i�p�E�E��Ԍ��̃p�l��
calendarpane = new JPanel(new BorderLayout()); // �w�i�p�E�E��Ԍ��̃p�l��
calendarpane.setOpaque(false);
backpane = new JPanel(new BorderLayout()); // �w�i�p�E�E���ԃJ�����_�[�̈�Ԍ��̃p�l��
backpane.setOpaque(false);
//g.drawLine(0,0,10,10);
this.add(calendarpane, BorderLayout.CENTER);
calendarpane.add(backpane, BorderLayout.CENTER);

JPanel infopane = new JPanel(new BorderLayout()); // �N�E���̌��Ȃǂ�j�����L������悤�̃p�l��
JPanel daybackpane = new JPanel(new GridLayout(0, 7,-2,0)); // ���t�p�̃o�b�N�p�l��
infopane.setBackground(Color.white);
backpane.add(infopane, BorderLayout.NORTH);
backpane.add(daybackpane, BorderLayout.CENTER);

int setyear = a; // �Z�b�g����N
int setmonth = b; // �Z�b�g���錎
int setday = c; // �Z�b�g������ɂ�

Calendar calendar = Calendar.getInstance();
calendar.set(setyear, setmonth - 1, 1); // ���t�̃Z�b�g(�����߂̓��t)

int year = calendar.get(Calendar.YEAR);// ���ŃZ�b�g���ꂽ�N��ǂݍ���
int dayofweek = calendar.get(Calendar.DAY_OF_WEEK); // �j���̐����B
// ���̃t�B�[���h�̒l��1����7�ŁASUNDAY(1)�AMONDAY(2)�ATUESDAY(3)�AWEDNESDAY(4)�ATHURSDAY(5)�AFRIDAY(6)�A�����SATURDAY(7)�ɂȂ�܂��B
// calendar.set�Ō����߂̓��t���Q�b�g���Ă���̂ŁA���̗j���͕K�������߂̗j���ƂȂ�B

// �e���x���E�p�l���̍쐬
JLabel yearLabel = new JLabel(year + "�N" + String.valueOf(setmonth) + "��");
yearLabel.setFont(new Font("MS �S�V�b�N",Font.PLAIN,24));
yearLabel.setHorizontalAlignment(JLabel.CENTER);// ���x���̕����𒆉��ɔz�u
infopane.add(yearLabel, BorderLayout.CENTER);// �Z�b�g�����N��infopane��CENTER�ɓ\��t���Ă���

if (setmonth == 12)
setmonth = 0;

//NEXT�{�^��
//���̌���\������{�^��
JButton nextmonth = new JButton("NEXT");
nextmonth.setCursor(new Cursor(Cursor.HAND_CURSOR));
nextmonth.setBorderPainted(false);
nextmonth.setFont(new Font("���C���I", Font.BOLD, 14));
nextmonth.setBackground(Color.WHITE);
nextmonth.setForeground(new Color(253,166,83));
nextmonth.setContentAreaFilled(false);
nextmonth.setFocusPainted(false);
nextmonth.addActionListener(this);

nextmonth.setActionCommand("next");
infopane.add(nextmonth, BorderLayout.EAST);

if (setmonth == 1)
setmonth = 13;
else if (setmonth == 0)
setmonth = 12;

//BACK�{�^��
//�O�̌���\������{�^��
JButton backmonth = new JButton("BACK");
backmonth.setFont(new Font("���C���I", Font.BOLD, 14));
backmonth.setBackground(Color.WHITE);
backmonth.setForeground(new Color(253,166,83));
backmonth.setContentAreaFilled(false);
backmonth.setBorderPainted(false);
backmonth.setFocusPainted(false);
backmonth.setCursor(new Cursor(Cursor.HAND_CURSOR));
infopane.add(backmonth, BorderLayout.WEST);
backmonth.addActionListener(this);
backmonth.setActionCommand("back");


JPanel dayname = new JPanel(new GridLayout(0, 7));
dayname.setBackground(Color.white);
infopane.add(dayname, BorderLayout.SOUTH);// �j���̃{�^����z�u���邽�߂̃p�l����infopane��SOUTH�ɓ\��t���Ă���
EtchedBorder border = new EtchedBorder(EtchedBorder.LOWERED,
Color.black, Color.white);// Border�N���X���g���ă{�[�_�[���쐬�B

// �j����z�u
for (int r = 0; r < 7; r++) {
JLabel dayNameLabel = new JLabel(dayweek[r]);
dayname.add(dayNameLabel);
if(r == 0) {
	dayNameLabel.setForeground(new Color(255,102,102));	
}else if(r == 6) {
	dayNameLabel.setForeground(new Color(51,153,255));
}

dayNameLabel.setHorizontalAlignment(JLabel.CENTER);
}

for (int j = 1; j < 2; j++) {// ���̒l��0-11������j�̒l��1�ŏ��������邱��
int month = calendar.get(Calendar.MONTH) + j;// ���̒l�����@0-11�œ���
int monthdays = 0;
if (month == 2) {
monthdays = 28;
} else if (month == 4 || month == 6 || month == 9 || month == 11) {
monthdays = 30;
} else {
monthdays = 31;
}
// �O���̏I�����擾���č쐬
calendar.add(Calendar.DATE, -(dayofweek - 1));
int bday = calendar.get(Calendar.DATE);
int bmonth = calendar.get(Calendar.MONTH) + 1;
for (int k = 1; k < dayofweek; k++) { // ���n�߂̗j���܂ŋ�p�l����z�u����B

//�O���̌J�z��z�u
JPanel karapane = new JPanel(new BorderLayout());
karapane.setBackground(Color.white);
daybackpane.add(karapane);
JButton karaButton = new JButton(String.valueOf((bday + (k - 1))));
karaButton.setFont(new Font("MS �S�V�b�N",Font.PLAIN,15));
karaButton.setForeground(Color.LIGHT_GRAY);
karaButton.setHorizontalAlignment(JLabel.CENTER);
karaButton.setBorderPainted(false);
karaButton.setContentAreaFilled(false);
karaButton.setFocusPainted(false);
karaButton.addActionListener(this);
karaButton.setActionCommand("createWindow");
karaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
karapane.add(karaButton, BorderLayout.NORTH);
JTextArea karamemo = new JTextArea(); 
karamemo.setEditable(false);
karamemo.setBackground(Color.WHITE);
karamemo.setOpaque(true);
karapane.add(karamemo, BorderLayout.CENTER);
}

// �J�����_�[�̖{��
calendar.set(setyear, setmonth - 1, 1); // �J�����_�[�̏������B���t�̃Z�b�g(�����߂̓��t)
for (int i = 0; i < monthdays; i++) {
int date = calendar.get(Calendar.DATE) + i;

// month�̃t�B�[���h�̒l��0����11

JPanel daypane = new JPanel(new BorderLayout());// ���t�p�̃p�l��
daybackpane.add(daypane);
JButton button = new JButton(String.valueOf(date));
button.setFont(new Font("MS �S�V�b�N",Font.PLAIN,15));
button.setHorizontalAlignment(JLabel.CENTER);
button.setBackground(Color.WHITE);
button.setBorderPainted(false);
button.setContentAreaFilled(false);
button.setFocusPainted(false);
button.addActionListener(this);
button.setActionCommand("createWindow");
button.setCursor(new Cursor(Cursor.HAND_CURSOR));
button.setOpaque(true);
daypane.add(button, BorderLayout.NORTH);

daysfile = new File("C:\\HMCalendar\\" + String.valueOf(a)
+ "\\" + String.valueOf(b) + "\\"
+ String.valueOf(date) + ".txt");
String key = "", setkey = key;

try {

BufferedReader br = new BufferedReader(new FileReader(
daysfile));

while ((key = br.readLine()) != null) {
if (setkey == null) {
setkey = key;
} else {
setkey = setkey + "\n" + key;
// System.out.println("strs="+strs);
}
}

br.close();

} catch (FileNotFoundException e) {
// System.out.println("�t�@�C����������܂���");
// System.out.println(daysfile);
// System.out.println("a=" + a + " b=" + b + " c=" + c);
} catch (IOException e) {
System.out.println("�G���[�ł�");
}

JTextArea memo = new JTextArea(setkey);
memo.setEditable(false);
//memo.setBorder(border);
memo.setBackground(Color.white);
memo.setOpaque(true);
daypane.add(memo, BorderLayout.CENTER);

}
// �����̗j����dayofweek�ɑ������
calendar.add(Calendar.MONTH, 1);
calendar.add(Calendar.DATE, -1);
dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
// ���̌��̓��ɂ����擾����
calendar.add(Calendar.DATE, 1);
int nday = calendar.get(Calendar.DATE);
int nmonth = calendar.get(Calendar.MONTH) + 1;

//�����̗j�������p�l����z�u
for (int i = 0; i < 7 - dayofweek; i++) {
JPanel karapane = new JPanel(new BorderLayout());
karapane.setBackground(Color.WHITE);
daybackpane.add(karapane);
JButton karaButton = new JButton(String.valueOf((nday + i)));
karaButton.setForeground(Color.LIGHT_GRAY);
karaButton.setFont(new Font("MS �S�V�b�N",Font.PLAIN,15));
karapane.add(karaButton, BorderLayout.NORTH);
karaButton.setHorizontalAlignment(JLabel.CENTER);
karaButton.setBorderPainted(false);
karaButton.setContentAreaFilled(false);
karaButton.setFocusPainted(false);
karaButton.addActionListener(this);
karaButton.setActionCommand("createWindow");
karaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
JTextArea karamemo = new JTextArea(); 
karamemo.setEditable(false);
karamemo.setBackground(Color.WHITE);
karamemo.setOpaque(true);
karapane.add(karamemo, BorderLayout.CENTER);
}
}
// System.out.println("year" + setyear + "" + "month" + setmonth);
//System.out.println("a=" + a + " b=" + b + " c=" + c);
if (state == 1) {
dailysyori();
} else {
yeardir();
}

this.setVisible(true);

}

void dailysyori() {
//
//
//
////todosyori();
}

//void todosyori() {
//dayhozon = new JButton("�ۑ�����");
//dayhozon.addActionListener(new ActionListener() {
//public void actionPerformed(ActionEvent event) {
//
//restart();
//
//}
//});
//
//}

void yeardir() {
yeardir = new File("C:\\HMCalendar\\" + String.valueOf(a));
if (yeardir.exists()) {

} else {
yeardir.mkdir();

}
monthdir();
}

void monthdir() {
monthdir = new File("C:\\HMCalendar\\" + String.valueOf(a) + "\\"
+ String.valueOf(b));
if (monthdir.exists()) {

} else {
monthdir.mkdir();
}
daysfile();
}

void daysfile() {
daysfile = new File("C:\\HMCalendar\\" + String.valueOf(a) + "\\"
+ String.valueOf(b) + "\\" + String.valueOf(c) + ".txt");
if (daysfile.exists()) {

} else {
try {
daysfile.createNewFile();
} catch (IOException e) {
// TODO �����������ꂽ catch �u���b�N
e.printStackTrace();
}

}
dailysyori();

}

public static void main(String[] args) {

// ���݂̔N�������擾����a,b,c�ɑ��
Calendar calendar = Calendar.getInstance();
a = calendar.get(Calendar.YEAR);
b = calendar.get(Calendar.MONTH) + 1;
c = calendar.get(Calendar.DATE);




//HMCalendar = new File("C:\\HMCalendar");
//if (HMCalendar.exists()) {
//// System.out.println("�t�@�C����������܂���");
//} else {
//// System.out.println("�t�@�C����������܂���A�쐬���܂��B");
//HMCalendar.mkdir();
//}
CalendarTest calendars = new CalendarTest("�J�����_�[");
calendars.syori();

}

void restart() {

calendarpane.removeAll();

syori();
}

public void actionPerformed(ActionEvent e) {
String btncmd = e.getActionCommand();


if (btncmd.equals("next")) { // next�{�^���������ꂽ�Ƃ��̃A�N�V����
if (b == 12) { // 12���Ȃ�N��+1���ĂP���ɖ߂��B
a = a + 1;
b = 1;
} else {
b = b + 1;
}

restart();

} else if (btncmd.equals("back")) {
if (b == 1) { // 1���Ȃ�N��-1����12���ɖ߂�
a = a - 1;
b = 12;
} else {
b = b - 1;
}

restart();





} else if(btncmd.equals("createWindow")) {
	createWindow();
	restart();
}

else if (Integer.parseInt(btncmd) != 0
&& Integer.parseInt(btncmd) <= 31) {
c = Integer.parseInt(btncmd);
restart();

}

}

void componentResized(ComponentEvent e) {
}

void componentMoved(ComponentEvent e) {
}

// �E�B���h�E���ŏ�������߂�Ƃ�
public void windowDeiconified(WindowEvent e) {
this.removeAll();
dispose();
CalendarTest calendars = new CalendarTest("�J�����_�[");
calendars.syori();

}

public void windowActivated(WindowEvent e) {

}

public void windowClosing(WindowEvent e) {

}

public void windowOpened(WindowEvent e) {

}

public void windowClosed(WindowEvent e) {

}

// �E�B���h�E���ŏ��������Ƃ�
public void windowIconified(WindowEvent e) {

}

public void windowDeactivated(WindowEvent e) {

	
//�V�����E�B���h�E�����
}
public void createWindow() {
	
	Calendar cl = Calendar.getInstance();
	a = cl.get(Calendar.YEAR);
	b = cl.get(Calendar.MONTH) + 1;
	c = cl.get(Calendar.DATE);
	cl.set(a,b - 1,c);

	Daily inst = new Daily(cl);
	inst.setVisible(true);
	
	
	

}


//�}�E�X�C�x���g

@Override
public void mouseClicked(MouseEvent e) {

}

@Override
public void mouseEntered(MouseEvent e) {
	new Cursor(Cursor.HAND_CURSOR);
}

@Override
public void mouseExited(MouseEvent e) {
}

@Override
public void mousePressed(MouseEvent e) {
}

@Override
public void mouseReleased(MouseEvent arg0) {
}

}
