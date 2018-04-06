package pppdatadownloader;

import pppdatadownloader.utils.RinexHeaderParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame {
    private JButton inputButton = new JButton("Input");
    private JButton outputButton = new JButton("Output");
    private JLabel inputlabel = new JLabel();
    private JLabel outputlabel = new JLabel();
    private File[] files;

    public Main(){
        super("PPP Data Downloader");
        setBounds(100, 100, 250,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,2,2,2));
        container.add(inputlabel);
        container.add(inputButton);
        container.add(outputlabel);
        container.add(outputButton);

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                files = fileChooser.getSelectedFiles();
            }
        });

        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (File file: files){
                    new RinexHeaderParser(file.toPath());
                }
            }
        });
    }

    public static void main(String[] args) {
        //new FTPConnection().getConnection();

        RinexHeaderParser parser = new RinexHeaderParser(new File("C:/TMP/2017_06_16_VOLX_g101b10149_f001_.17o").toPath());
        parser.print();

        Main app = new Main();
        app.setVisible(true);

    }

}
