package pppdatadownloader.ui;

import pppdatadownloader.utils.RinexHeaderParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Window extends JFrame {
    private JButton inputButton = new JButton("Input");
    private JButton outputButton = new JButton("Output");
    private JLabel inputlabel = new JLabel();
    private JLabel outputlabel = new JLabel();
    private File[] files;

    public Window(){
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
                if (fileChooser.showDialog(null,"Open files") == JFileChooser.APPROVE_OPTION){
                    files = fileChooser.getSelectedFiles();
                    inputlabel.setText("Ok!");
                } else {
                    inputlabel.setText("Cancelled!");
                }
            }
        });

        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (File file: files){
                    System.out.println(file);
                    new RinexHeaderParser(file.toPath());
                }
                outputlabel.setText("Finished!");
            }
        });
    }
}
