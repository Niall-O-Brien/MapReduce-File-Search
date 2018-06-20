package IR;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class GUI {

    private JFrame frmInformationRetrievalSystem;
    private JTextField directory;
    private JTextField queryDirectory;
    private JTextField query;
    private JLabel lblQuery;
    private JTextArea results;
    JFileChooser chooser;

    /**
     * Create the application.
     */
    public GUI() throws FileNotFoundException {
        initialize();
        this.frmInformationRetrievalSystem.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws FileNotFoundException {
        frmInformationRetrievalSystem = new JFrame();
        Container content = frmInformationRetrievalSystem.getContentPane();

        frmInformationRetrievalSystem.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\gnome_text_x_generic.png"));
        frmInformationRetrievalSystem.setTitle("Information  Retrieval System");
        frmInformationRetrievalSystem.setBounds(100, 100, 700, 600);
        frmInformationRetrievalSystem.setResizable(false);
        frmInformationRetrievalSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(null);

        JLabel lblDirectory = new JLabel("Directory:");
        lblDirectory.setBounds(17, 40, 80, 17);
        lblDirectory.setFont(new Font("Consolas", Font.PLAIN, 14));
        content.add(lblDirectory);

        directory = new JTextField();
        directory.setFont(new Font("Consolas", Font.PLAIN, 14));
        directory.setBounds(100, 33, 524, 30);
        directory.setText("documents");
        content.add(directory);
        directory.setColumns(10);


        JLabel queryDirLabel = new JLabel("Query Dir:");
        queryDirLabel.setBounds(17, 170, 80, 17);
        queryDirLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
        content.add(queryDirLabel);

        queryDirectory = new JTextField();
        queryDirectory.setFont(new Font("Consolas", Font.PLAIN, 14));
        queryDirectory.setBounds(100, 163, 524, 30);
        queryDirectory.setText("queries");
        content.add(queryDirectory);
        queryDirectory.setColumns(10);   		

        // final JProgressBar progressBar = new JProgressBar();
        // progressBar.setBounds(100, 131, 530, 23);
        // content.add(progressBar);          

        lblQuery = new JLabel("Query:");
        lblQuery.setFont(new Font("Consolas", Font.PLAIN, 14));
        lblQuery.setBounds(17, 87, 80, 17);
        content.add(lblQuery);

        results = new JTextArea();
        results.setFont(new Font("Consolas", Font.PLAIN, 14));
        results.setEditable(false);
        results.setLineWrap(true);
        results.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(results);
        scroll.setBounds(17, 250, 653, 290);
        content.add(scroll);


        // documents browse
        JButton Browse = new JButton("...");
        Browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {			    
                String choosertitle = "Directory";

                chooser = new JFileChooser(); 
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // disable the "All files" option.
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(frmInformationRetrievalSystem) == JFileChooser.APPROVE_OPTION) {
                    directory.setText(""+chooser.getSelectedFile());
                    if (Files.exists(chooser.getSelectedFile().toPath())) {
                        directory.setBackground(Color.GREEN);
                    }
                    else{
                        directory.setBackground(Color.RED);
                    }
                }
            }
        });

        Browse.setBounds(636, 33, 32, 30);
        content.add(Browse);

        final JButton searchBtn = new JButton("");
        searchBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    Path path = Paths.get(directory.getText());
                    if (Files.exists(path)) {
			File file = new File(directory.getText());
                        if(file.list().length>0) {//fix this!!!!!!!!!!!!!!!!!!!!!!!
                            directory.setBackground(Color.GREEN);
                            if(query.getText().length()>=1) {
                                query.setBackground(Color.GREEN);
                                String queryString = query.getText();
                                String filePath = directory.getText();
                                // call the main function
                                Main.querySystem(queryString, filePath);                                   
                            }
                        else{
                            query.setBackground(Color.RED);
                        }
                    }
                    else{
                        directory.setBackground(Color.RED);
                    }
                    }
                }
                catch(Exception ex){
                    directory.setBackground(Color.RED);
                }
            }
        });
        searchBtn.setIcon(new ImageIcon("images\\search-button-green-icon.png"));
        searchBtn.setFont(new Font("Consolas", Font.PLAIN, 11));
        searchBtn.setBounds(636, 80, 32, 30);
        content.add(searchBtn);

        query = new JTextField();
        query.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        try{
                            Path path = Paths.get(directory.getText());
                            if (Files.exists(path)) {
                                directory.setBackground(Color.GREEN);
                                if(query.getText().length()>=1){
                                    query.setBackground(Color.GREEN);
                                    String queryString = query.getText();
                                    String filePath = directory.getText();
									try{

										Main.querySystem(queryString, filePath);
									}
                                    catch(Exception ex){}
                                }
                                else{
                                    query.setBackground(Color.RED);
                                }
                            }
                            else{
                                directory.setBackground(Color.RED);
                            }
                        }
                        catch(Exception ex){
                            directory.setBackground(Color.RED);
                        }                                
                    }
                }
        });
        query.addMouseListener(new MouseAdapter() {
                @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    Path path = Paths.get(directory.getText());
                    if (Files.exists(path)) {
                        directory.setBackground(Color.GREEN);
                    }
                    else{
                        directory.setBackground(Color.RED);
                    }
                    query.setText("");
                }
                catch(Exception ex){
                    directory.setBackground(Color.RED);
                }
            }
        });

        query.setFont(new Font("Consolas", Font.PLAIN, 14));
        query.setText("the crystalline lens in vertebrates including humans");
        query.setColumns(10);
        query.setBounds(100, 80, 524, 30);
        content.add(query);


        // query browse
        JButton queryBrowse = new JButton("...");
        queryBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                String choosertitle = "Queries Directory";

                chooser = new JFileChooser(); 
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // disable the "All files" option.
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(frmInformationRetrievalSystem) == JFileChooser.APPROVE_OPTION) {
                    queryDirectory.setText(""+chooser.getSelectedFile());

                    if (Files.exists(chooser.getSelectedFile().toPath())) {
                        queryDirectory.setBackground(Color.GREEN);
                    }
                    else{
                        queryDirectory.setBackground(Color.RED);
                    }
                }
            }
        });

        queryBrowse.setBounds(636, 163, 32, 30);
        content.add(queryBrowse);

         // test system
        JButton testSystem = new JButton("Test System");

        testSystem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                try{
                    Path path = Paths.get(queryDirectory.getText());
                    if (Files.exists(path)) {
                        File file = new File(queryDirectory.getText());

                        if(file.list().length > 0) {
                            directory.setBackground(Color.GREEN);
                            queryDirectory.setBackground(Color.GREEN);

                            String documentsPath = directory.getText();
                            String queryPath = queryDirectory.getText();

                            Main.testSystem(queryPath, documentsPath);
                        }
                        else {
                            directory.setBackground(Color.RED);
                            queryDirectory.setBackground(Color.RED);
                        }
                    }
                }
                catch(Exception ex){
                    directory.setBackground(Color.RED);
                    queryDirectory.setBackground(Color.RED);
                }
            }
        });

        testSystem.setBounds(290, 203, 120, 30);
        content.add(testSystem);

    }

    // prints input to window
    public <I>void print(I input) {
        results.append(input.toString() +"\n");
    }


    public void clearWindow() {
        results.setText("");
    }
}