import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class Livelist extends JFrame {
    public JList list;
    public Vector<String> vec;
    private ImgPanel contentPane;
    JButton listAdd;
//    JButton
    private boolean isOdd;
//    listLoader();
//    public Livelist(String[] pre, Jplayer jP){
////        PlayList = new Vector<String>();
//////        Vector vt=new Vector();
////        JList myList = new JList();
////        PlayList.add("aaa.mp3");
////        PlayList.add("bbb.mp3");
//////        PlayList.set(3, "new one!");
////        myList.setListData(PlayList);
////        contentPane.add(myList);
////        myList.setBounds(0,0, 600,300);
//
//        isOdd = true;
//        vec = new Vector();
//        int i = 0;
//        for (i=0; i<pre.length; i++){
//            vec.add(pre[i]);
//        }
//
//        list = new JList(pre);
//
//        setTitle("Live-List");
//        setVisible(true);
//
//        setResizable(false);
//
//
//        setBounds(300, 200, 400, 435);
//
//        setContentPane(list);
//
//        list.addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//
//                isOdd = !isOdd;
//                if (isOdd){
//                    System.out.println( vec.get(list.getSelectedIndex()) );
////                    URL url = new URL(vec.get(list.getSelectedIndex()))
////                    String targetFile = vec.get(list.getSelectedIndex());
//
//                    jP.path = vec.get(list.getSelectedIndex());
//                    jP.jStatus.isPlay = false;
//
//                    if (jP.jStatus.isPlay){
//                        jP.mediaPlayer.stop();
//                    }
//                    jP.thePlay();
//                    jP.jStatus.isPlay = true;
////                    jP.mediaPlayer.stop();
//
////                    jP.mediaPlayer.play();
//
//                }
////                jp2other
//            }
//        });
//
//        setBackground(Color.WHITE);
//        this.setVisible(true);
//
//    }

    public void listLoader(){
        vec = new Vector();
        model = new DefaultListModel<>();
        String fileName;

//        model.addElement("Hello");
        String usrHome = System.getProperty("user.home");
        fileName = usrHome+"/FutureSoft/JP/LiveList.data";

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
//                vec.add(tempString);
                model.addElement(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {

                }
            }
        }
    }

    public Livelist(){
        vec = new Vector();
        listLoader();

        System.out.println(vec.get(1));
    }

    DefaultListModel<String> model;
    public Livelist(Jplayer jP){

        listLoader();

        list = new JList<>(model);
        isOdd = true;

//        list = new JList(vec);




        setTitle("Live-List");
        setVisible(true);

        setResizable(false);

        setBounds(300, 200, 400, 450);

        setContentPane(list);

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                isOdd = !isOdd;
                if (isOdd){
//                    System.out.println( vec.get(list.getSelectedIndex()) );
//                    URL url = new URL(vec.get(list.getSelectedIndex()))
//                    String targetFile = vec.get(list.getSelectedIndex());

//                    jP.path = list.get(list.getSelectedIndex());

                    jP.path = model.getElementAt(list.getSelectedIndex());


                    jP.jStatus.isPlay = false;
                    if (jP.jStatus.isPlay){
                        jP.mediaPlayer.stop();
                    }
                    jP.thePlay();
                }
            }
        });
        setBackground(Color.WHITE);



        listAdd = new JButton("+ add");

        listAdd.setBounds(0, 390, 80, 30);
        listAdd.setHorizontalAlignment(SwingConstants.CENTER);
        listAdd.setVerticalAlignment(SwingConstants.CENTER);

        listAdd.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                System.out.println("btn_playBtn click!");
//                String myStr = JOptionPane.showInputDialog("新歌曲在硬盘中的位置：");
                  // 弃用
//                System.out.println("@@@@ From Graph @@@@ " + myStr);
//                JFileChooser myFC = new JFileChooser();
//                new FileChooser(listAdd);
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jfc.showDialog(new JLabel(), "选择");
                File file=jfc.getSelectedFile();

                if (file.isDirectory()){
                    System.out.println("文件夹:"+file.getAbsolutePath());
                    return;
                } else if (file.isFile()){
                    System.out.println("文件:"+file.getAbsolutePath());
                    String willBePath = file.getAbsolutePath().replace("\\", "/");
                    model.addElement(willBePath);
                }
                list.repaint();
            }
        });

        list.add(listAdd);

        this.setVisible(true);
    }
    JButton open;

    public static void main(String[] args){
//        listLoader();
//        new Livelist();
    }

}