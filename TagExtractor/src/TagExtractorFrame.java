import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TagExtractorFrame extends JFrame {
    JPanel mainPnl, topPnl, centerPnl, bottomPnl;
    JTextArea fileTA, resultTA;
    JScrollPane scrollPane;
    JButton saveBtn, quitBtn, submitBtn, browseBtn;

    public TagExtractorFrame()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int height= screenSize.height;
        int width = screenSize.width;
        setSize(800,600);
        setLocation((width/4),height/10);
        setTitle("Tag Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI()
    {
        mainPnl = new JPanel();

        createTopPanel();
        createCenterPanel();
        createBottomPanel();

        mainPnl.setLayout(new BorderLayout());
        mainPnl.add(topPnl, BorderLayout.NORTH);
        mainPnl.add(centerPnl, BorderLayout.CENTER);
        mainPnl.add(bottomPnl, BorderLayout.SOUTH);

        add(mainPnl);

    }

    private void createTopPanel()
    {
        topPnl = new JPanel();

        fileTA = new JTextArea(1,20);
        fileTA.setFont(new Font("Serif", Font.PLAIN,14));
        fileTA.setEditable(false);
        browseBtn = new JButton("Browse");
        browseBtn.setFont(new Font("Serif", Font.PLAIN, 12));
        browseBtn.addActionListener(e -> {
            FileMethod.fileBrowser(fileTA);
            resultTA.setText("");
        });
        topPnl.add(fileTA);
        topPnl.add(browseBtn);
    }

    private void createCenterPanel()
    {
        centerPnl = new JPanel();
        resultTA = new JTextArea(20,40);
        resultTA.setFont(new Font("Serif", Font.PLAIN, 16));
        resultTA.setEditable(false);

        scrollPane = new JScrollPane(resultTA);

        centerPnl.add(scrollPane);
    }

    private void createBottomPanel()
    {
        bottomPnl = new JPanel();

        bottomPnl.setLayout(new GridLayout(1,3));
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            File result = FileMethod.fileBrowser();
            if (result != null) {
                resultTA.setText("");
                FileMethod.fileFilter(resultTA, result);
            } else {
                System.out.println("There is nothing in this file");
            }
        });
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            FileMethod.fileSave(resultTA);
        });
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(e -> {
            System.exit(0);
        });

        bottomPnl.add(submitBtn);
        bottomPnl.add(saveBtn);
        bottomPnl.add(quitBtn);

    }
}