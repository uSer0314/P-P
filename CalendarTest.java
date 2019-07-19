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
* カレンダーの考え方のメモ getで今日を求めれる。setで任意に設定できる。 月の初めの曜日を求めます。 calendar.set(year,
* month - 1, 1); // 引数: 1月: 0, 2月: 1, ... startDay =
* calendar.get(Calendar.DAY_OF_WEEK); 月末の日付を求めます。
* calendar.add(Calendar.MONTH, 1); calendar.add(Calendar.DATE, -1);
* lastDate = calendar.get(Calendar.DATE);
*/

JPanel calendarpane, backpane;

JButton dayhozon;

// 月間カレンダーの作成時
File yeardir;
File monthdir;
File daysfile;

static int a;// セットする年
static int b;
static int c = 1;
static int state = 0;
// 起動時
static File HMCalendar;
{
// セットする月

this.setSize(1280, 960);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
this.setLayout(new BorderLayout());
this.addWindowListener(this);
this.addComponentListener(null);

}

public CalendarTest(String title) { 
}

public void syori() {
String[] dayweek = { "日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日" };

// JPanel backpane = new JPanel(new BorderLayout()); // 背景用・・一番後ろのパネル
calendarpane = new JPanel(new BorderLayout()); // 背景用・・一番後ろのパネル
calendarpane.setOpaque(false);
backpane = new JPanel(new BorderLayout()); // 背景用・・月間カレンダーの一番後ろのパネル
backpane.setOpaque(false);
//g.drawLine(0,0,10,10);
this.add(calendarpane, BorderLayout.CENTER);
calendarpane.add(backpane, BorderLayout.CENTER);

JPanel infopane = new JPanel(new BorderLayout()); // 年・次の月などや曜日を記入するようのパネル
JPanel daybackpane = new JPanel(new GridLayout(0, 7,-2,0)); // 日付用のバックパネル
infopane.setBackground(Color.white);
backpane.add(infopane, BorderLayout.NORTH);
backpane.add(daybackpane, BorderLayout.CENTER);

int setyear = a; // セットする年
int setmonth = b; // セットする月
int setday = c; // セットする日にち

Calendar calendar = Calendar.getInstance();
calendar.set(setyear, setmonth - 1, 1); // 日付のセット(月初めの日付)

int year = calendar.get(Calendar.YEAR);// ↑でセットされた年を読み込む
int dayofweek = calendar.get(Calendar.DAY_OF_WEEK); // 曜日の数字。
// このフィールドの値は1から7で、SUNDAY(1)、MONDAY(2)、TUESDAY(3)、WEDNESDAY(4)、THURSDAY(5)、FRIDAY(6)、およびSATURDAY(7)になります。
// calendar.setで月初めの日付をゲットしているので、この曜日は必ず月初めの曜日となる。

// 各ラベル・パネルの作成
JLabel yearLabel = new JLabel(year + "年" + String.valueOf(setmonth) + "月");
yearLabel.setFont(new Font("MS ゴシック",Font.PLAIN,24));
yearLabel.setHorizontalAlignment(JLabel.CENTER);// ラベルの文字を中央に配置
infopane.add(yearLabel, BorderLayout.CENTER);// セットした年をinfopaneのCENTERに貼り付けている

if (setmonth == 12)
setmonth = 0;

//NEXTボタン
//次の月を表示するボタン
JButton nextmonth = new JButton("NEXT");
nextmonth.setCursor(new Cursor(Cursor.HAND_CURSOR));
nextmonth.setBorderPainted(false);
nextmonth.setFont(new Font("メイリオ", Font.BOLD, 14));
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

//BACKボタン
//前の月を表示するボタン
JButton backmonth = new JButton("BACK");
backmonth.setFont(new Font("メイリオ", Font.BOLD, 14));
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
infopane.add(dayname, BorderLayout.SOUTH);// 曜日のボタンを配置するためのパネルをinfopaneのSOUTHに貼り付けている
EtchedBorder border = new EtchedBorder(EtchedBorder.LOWERED,
Color.black, Color.white);// Borderクラスを使ってボーダーを作成。

// 曜日を配置
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

for (int j = 1; j < 2; j++) {// つきの値は0-11だからjの値も1で初期化すること
int month = calendar.get(Calendar.MONTH) + j;// 月の値を取る　0-11で入る
int monthdays = 0;
if (month == 2) {
monthdays = 28;
} else if (month == 4 || month == 6 || month == 9 || month == 11) {
monthdays = 30;
} else {
monthdays = 31;
}
// 前月の終わりを取得して作成
calendar.add(Calendar.DATE, -(dayofweek - 1));
int bday = calendar.get(Calendar.DATE);
int bmonth = calendar.get(Calendar.MONTH) + 1;
for (int k = 1; k < dayofweek; k++) { // つき始めの曜日まで空パネルを配置する。

//前月の繰越を配置
JPanel karapane = new JPanel(new BorderLayout());
karapane.setBackground(Color.white);
daybackpane.add(karapane);
JButton karaButton = new JButton(String.valueOf((bday + (k - 1))));
karaButton.setFont(new Font("MS ゴシック",Font.PLAIN,15));
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

// カレンダーの本体
calendar.set(setyear, setmonth - 1, 1); // カレンダーの初期化。日付のセット(月初めの日付)
for (int i = 0; i < monthdays; i++) {
int date = calendar.get(Calendar.DATE) + i;

// monthのフィールドの値は0から11

JPanel daypane = new JPanel(new BorderLayout());// 日付用のパネル
daybackpane.add(daypane);
JButton button = new JButton(String.valueOf(date));
button.setFont(new Font("MS ゴシック",Font.PLAIN,15));
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
// System.out.println("ファイルが見つかりません");
// System.out.println(daysfile);
// System.out.println("a=" + a + " b=" + b + " c=" + c);
} catch (IOException e) {
System.out.println("エラーです");
}

JTextArea memo = new JTextArea(setkey);
memo.setEditable(false);
//memo.setBorder(border);
memo.setBackground(Color.white);
memo.setOpaque(true);
daypane.add(memo, BorderLayout.CENTER);

}
// 月末の曜日をdayofweekに代入する
calendar.add(Calendar.MONTH, 1);
calendar.add(Calendar.DATE, -1);
dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
// 次の月の日にちを取得する
calendar.add(Calendar.DATE, 1);
int nday = calendar.get(Calendar.DATE);
int nmonth = calendar.get(Calendar.MONTH) + 1;

//月末の曜日から空パネルを配置
for (int i = 0; i < 7 - dayofweek; i++) {
JPanel karapane = new JPanel(new BorderLayout());
karapane.setBackground(Color.WHITE);
daybackpane.add(karapane);
JButton karaButton = new JButton(String.valueOf((nday + i)));
karaButton.setForeground(Color.LIGHT_GRAY);
karaButton.setFont(new Font("MS ゴシック",Font.PLAIN,15));
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
//dayhozon = new JButton("保存する");
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
// TODO 自動生成された catch ブロック
e.printStackTrace();
}

}
dailysyori();

}

public static void main(String[] args) {

// 現在の年月日を取得してa,b,cに代入
Calendar calendar = Calendar.getInstance();
a = calendar.get(Calendar.YEAR);
b = calendar.get(Calendar.MONTH) + 1;
c = calendar.get(Calendar.DATE);




//HMCalendar = new File("C:\\HMCalendar");
//if (HMCalendar.exists()) {
//// System.out.println("ファイルが見つかりました");
//} else {
//// System.out.println("ファイルが見つかりません、作成します。");
//HMCalendar.mkdir();
//}
CalendarTest calendars = new CalendarTest("カレンダー");
calendars.syori();

}

void restart() {

calendarpane.removeAll();

syori();
}

public void actionPerformed(ActionEvent e) {
String btncmd = e.getActionCommand();


if (btncmd.equals("next")) { // nextボタンが押されたときのアクション
if (b == 12) { // 12月なら年に+1して１月に戻す。
a = a + 1;
b = 1;
} else {
b = b + 1;
}

restart();

} else if (btncmd.equals("back")) {
if (b == 1) { // 1月なら年に-1して12月に戻す
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

// ウィンドウが最小化から戻るとき
public void windowDeiconified(WindowEvent e) {
this.removeAll();
dispose();
CalendarTest calendars = new CalendarTest("カレンダー");
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

// ウィンドウが最小化されるとき
public void windowIconified(WindowEvent e) {

}

public void windowDeactivated(WindowEvent e) {

	
//新しいウィンドウを作る
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


//マウスイベント

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
