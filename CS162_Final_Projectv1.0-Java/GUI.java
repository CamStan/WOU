import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.*;
import java.awt.Color.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.*;

/**
 * The interface for the BookManager program.
 * 
 * @author Cameron Stanavige
 * @version 5/27/2014
 */
public class GUI extends JFrame
{
    //Fields
    private BookManager manager;
    private BookList project;
    private static final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    private File currentFile;

    private JLabel bookNameLabel;

    private JMenu currentBooks;
    private ArrayList<JMenuItem> bookNamesArray;

    private JTextArea outputArea;
    private JTextField buttonOutput;
    private JTextField averageInput;

    private JTextField startInput;
    private JTextField endInput;

    private ButtonGroup averageGroup;
    private JRadioButton countOption;
    private JRadioButton timeOption;

    /**
     * Constructor for the GUI of ProjectManager
     * @param project The ProjectManager using this GUI.
     */
    public GUI (BookManager manager, BookList project)
    {
        super("Book Manager");
        this.manager = manager;
        this.project = project;
        makeFrame();
        bookNamesArray = new ArrayList<JMenuItem>();
        fileChooser.setCurrentDirectory(new File("Projects"));
        currentFile = null;
        //         about();
        //         instructions();
        //         pleaseOpen();
    }

    /**
     * Private method
     * Makes the overall Frame of the GUI.
     */
    private void makeFrame()
    {
        makeMenuBar();
        makeContentPane();

        this.setMinimumSize(new Dimension(425, 490));
        this.pack();
        this.setVisible(true);
    }

    /**
     * Private method
     * Makes the menu bar for the frame.
     */
    private void makeMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menubar.add(file);
        //File items
        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        open.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {open();}
            });
        file.add(open);

        file.addSeparator();
        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {save();}
            });
        file.add(save);

        JMenuItem saveAs = new JMenuItem("SaveAs");
        saveAs.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {saveAs();}
            });
        file.add(saveAs);

        file.addSeparator();
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {close();}
            });
        file.add(close);

        JMenu projectMenu = new JMenu("Project");
        projectMenu.setMnemonic(KeyEvent.VK_P);
        menubar.add(projectMenu);
        //Project items
        JMenuItem newBook = new JMenuItem("New Book");
        newBook.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {newBook();}
            });
        projectMenu.add(newBook);

        JMenuItem allBooks = new JMenuItem("All Books");
        allBooks.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {allBooks();}
            });
        projectMenu.add(allBooks);

        currentBooks = new JMenu("Select Book");
        projectMenu.add(currentBooks);

        JMenu entry = new JMenu("Entry");
        entry.setMnemonic(KeyEvent.VK_E);
        menubar.add(entry);
        //Entry items
        JMenuItem addEntry = new JMenuItem("Add New");
        addEntry.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        addEntry.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {addEntry();}
            });
        entry.add(addEntry);

        JMenu editMenu = new JMenu("Edit");
        entry.add(editMenu);

        JMenuItem editCount = new JMenuItem("Word Count");
        editCount.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {editEntryCount();}  
            });
        editMenu.add(editCount);

        JMenuItem editTime = new JMenuItem("Time Spent");
        editTime.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {editEntryTime();}
            });
        editMenu.add(editTime);

        JMenuItem editDate = new JMenuItem("Date");
        editDate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {editEntryDate();}
            });
        editMenu.add(editDate);

        JMenuItem sort = new JMenuItem("Sort");
        sort.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {sort();}
            });
        entry.add(sort);

        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        menubar.add(help);
        //Help items
        JMenuItem how = new JMenuItem("View Help");
        how.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {instructions();}
            });
        help.add(how);

        JMenuItem about = new JMenuItem("About BookManager");
        about.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {about();}
            });
        help.add(about);
    }

    /**
     * Private method
     * Makes the content pane for the frame.
     */
    private void makeContentPane()
    {
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setBorder(new EmptyBorder(10,10,10,10));
        contentPane.setLayout(new BorderLayout(8,8));
        //contentPane.setBackground(new Color(200, 225, 250));

        //Project name panel
        JPanel projectName = new JPanel();
        bookNameLabel = new JLabel("Select a Project");
        projectName.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1),
                new EtchedBorder()));
        projectName.add(bookNameLabel);
        contentPane.add(projectName, BorderLayout.NORTH);

        //Output panel
        outputArea = new JTextArea(25, 50);
        outputArea.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1),
                new EtchedBorder()));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //Button panel
        JPanel buttonPanel = makeButtonPane();
        contentPane.add(buttonPanel, BorderLayout.WEST);

        //Button output panel
        JPanel bottomPanel = makeBottomPane();
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Private method
     * Makes the button pane in the west of the content pane.
     * @return The west button pane.
     */
    private JPanel makeButtonPane()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        //Total buttons
        JPanel totalPane = new JPanel();
        totalPane.setLayout(new GridLayout(4, 1, 10, 10));

        JButton WCButton = new JButton("Word Count");
        WCButton.setBorder(new BevelBorder(0));
        WCButton.setPreferredSize(new Dimension(75, 22));
        WCButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {displayWordCount();}
            });
        totalPane.add(WCButton);
        JButton TSButton = new JButton("Time Spent");
        TSButton.setBorder(new BevelBorder(0));
        TSButton.setPreferredSize(new Dimension(75, 22));
        TSButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {displayTimeSpent();}
            });
        totalPane.add(TSButton);
        JButton NoEButton = new JButton("# of Entries");
        NoEButton.setBorder(new BevelBorder(0));
        NoEButton.setPreferredSize(new Dimension(75, 22));
        NoEButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {displayNumEntries();}
            });
        totalPane.add(NoEButton);
        JButton VEButton = new JButton("View Entries");
        VEButton.setBorder(new BevelBorder(0));
        VEButton.setPreferredSize(new Dimension(75, 22));
        //VEButton.setMnemonic(KeyEvent.VK_V);
        VEButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {displayEntries();}
            });
        totalPane.add(VEButton);

        //Average buttons
        JPanel averagePane = new JPanel();
        averagePane.setLayout(new BorderLayout(4,4));

        JPanel averageBP = new JPanel();
        averageBP.setLayout(new BorderLayout(8,8));
        averageBP.add(new JLabel("Daily Average by Months"), BorderLayout.NORTH);

        //Radio Buttons*****
        //Time/Average options
        JPanel averageOptions = new JPanel();
        averageOptions.setLayout(new GridLayout(2,1,2,2));

        averageGroup = new ButtonGroup();
        countOption = new JRadioButton("Word Count");
        countOption.setSelected(true);
        averageGroup.add(countOption);
        averageOptions.add(countOption);
        timeOption = new JRadioButton("Time Spent");
        averageGroup.add(timeOption);
        averageOptions.add(timeOption);

        averageBP.add(averageOptions, BorderLayout.CENTER);

        JPanel monthButtons = new JPanel();
        monthButtons.setLayout(new GridLayout(1, 5, 5, 5));
        //Grid buttons
        JButton m1 = new JButton("1");
        m1.setBorder(new BevelBorder(0));
        m1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {monthlyAverage(30);}
            });
        monthButtons.add(m1);
        JButton m3 = new JButton("3");
        m3.setBorder(new BevelBorder(0));
        m3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {monthlyAverage(91);}
            });
        monthButtons.add(m3);
        JButton m6 = new JButton("6");
        m6.setBorder(new BevelBorder(0));
        m6.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {monthlyAverage(182);}
            });
        monthButtons.add(m6);
        JButton m9 = new JButton("9");
        m9.setBorder(new BevelBorder(0));
        m9.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {monthlyAverage(274);}
            });
        monthButtons.add(m9);
        JButton m12 = new JButton("12");
        m12.setBorder(new BevelBorder(0));
        m12.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {monthlyAverage(365);}
            });
        monthButtons.add(m12);

        averageBP.add(monthButtons, BorderLayout.SOUTH);
        //Date input
        JPanel dateInput = new JPanel();
        dateInput.setLayout(new BorderLayout());

        JPanel startDate = new JPanel();
        startDate.add(new JLabel("Start Date"));
        startInput = new JTextField("mm/dd/yyyy", 6);
        startInput.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {startInput.setText("");}

                public void focusLost(FocusEvent e) {}
            });
        startDate.add(startInput);

        JPanel endDate = new JPanel();
        endDate.add(new JLabel("End Date"));
        endInput = new JTextField("mm/dd/yyyy",6);
        endInput.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {endInput.setText("");}

                public void focusLost(FocusEvent e) {}
            });
        endDate.add(endInput);

        JPanel submitPanel = new JPanel();
        JButton submit = new JButton("Submit");
        submit.setBorder(new BevelBorder(0));
        submit.setPreferredSize(new Dimension(50, 22));
        submit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {submit();}
            });
        submitPanel.add(submit);

        dateInput.add(startDate, BorderLayout.NORTH);
        dateInput.add(endDate, BorderLayout.CENTER);
        dateInput.add(submitPanel, BorderLayout.SOUTH);

        //End assembly
        averagePane.add(averageBP, BorderLayout.NORTH);
        averagePane.add(dateInput, BorderLayout.SOUTH);

        buttonPanel.add(totalPane, BorderLayout.NORTH);
        buttonPanel.add(averagePane, BorderLayout.SOUTH);
        return buttonPanel;
    }

    /**
     * Private method
     * Makes the panel for the south part of the content pane.
     * @return The south panel.
     */
    private JPanel makeBottomPane()
    {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        //Average input
        JPanel input = new JPanel();

        averageInput = new JTextField(5);
        averageInput.setBorder(new BevelBorder(1));
        //averageInput.setBackground(Color.WHITE);

        JLabel days = new JLabel("Days");

        JButton enter = new JButton("Enter");
        enter.setBorder(new BevelBorder(0));
        enter.setPreferredSize(new Dimension(40, 22));
        enter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {enter();}
            });

        input.add(averageInput);
        input.add(days);
        input.add(enter);

        //Button output
        JPanel output = new JPanel();

        buttonOutput = new JTextField(12);
        buttonOutput.setBorder(new BevelBorder(1));
        buttonOutput.setBackground(new Color(200, 250, 225));
        buttonOutput.setEditable(false);

        //Clear button
        JPanel clearPane = new JPanel();
        JButton clear = new JButton("Clear Display");
        clear.setBorder(new BevelBorder(0));
        clear.setPreferredSize(new Dimension(90, 25));
        //clear.setMnemonic(KeyEvent.VK_X);
        clear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {clearDisplay();}
            });

        output.add(buttonOutput);
        clearPane.add(clear);

        bottomPanel.add(input, BorderLayout.WEST);
        bottomPanel.add(output, BorderLayout.CENTER);
        bottomPanel.add(clearPane, BorderLayout.EAST);
        return bottomPanel;
    }

    //Menu methods
    /**
     * Private method
     * Opens a .data file applicable to this program.
     */
    private void open()
    {
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Data Files", "data"));
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            File selected = fileChooser.getSelectedFile();
            try{
                FileInputStream f_in = new FileInputStream(selected);
                ObjectInputStream obj_in = new ObjectInputStream(f_in);
                Object obj = obj_in.readObject();
                if(obj instanceof BookList) {
                    project = (BookList) obj;
                    manager.setBookList((BookList) obj);
                    currentFile = selected;
                    fillSelectBookMenu();
                    String bnLabel = selected.getName();
                    bookNameLabel.setText(bnLabel.substring(0, bnLabel.indexOf(".")) + " project opened");
                }
            }
            catch (FileNotFoundException e) {
                println(e);
                open();
            }
            catch (ClassNotFoundException e) {
                println(e);
            }
            catch (IOException e) {
                println(e);
            }
        }
    }

    /**
     * Private method
     * Fills the select book menu when a saved project is opened.
     */
    private void fillSelectBookMenu()
    {
        List<Book> allBooks = project.getAllBooks();
        for(Book book : allBooks) {
            String title = book.getTitle();
            addCurrentBookMenuItem(title);
        }
    }

    /**
     * Private method
     * Saves the currently existing file.
     */
    private void save()
    {
        if(currentFile != null) {
            try{
                FileOutputStream f_out = new FileOutputStream(currentFile);
                ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
                obj_out.writeObject(project);
                buttonOutput.setText("project saved");
            }
            catch (FileNotFoundException e) {
                println(e);
            }
            catch (IOException e) {
                println(e);
            }
        }
        else {
            saveAs();
        }
    }

    /**
     * Private method
     * Saves the program with desired name.
     */
    private void saveAs()
    {
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Data Files", "data"));
        int result = fileChooser.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            File selected = fileChooser.getSelectedFile();

            try{
                FileOutputStream f_out;
                if(selected.getPath().endsWith(".data")) {
                    f_out = new FileOutputStream(selected);
                }
                else {
                    f_out = new FileOutputStream(selected + ".data");
                }
                ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
                obj_out.writeObject(project);
                currentFile = selected;
                buttonOutput.setText("project saved"); 
            }
            catch (FileNotFoundException e) {
                println(e);
            }
            catch (IOException e) {
                println(e);
            }
        }
    }

    /**
     * Private method
     * Closes the program.
     */
    private void close()
    {
        int result = JOptionPane.showConfirmDialog(this, "Do you want to save before quiting?\nP.S. I Love You!",
                "Close Program", 1);
        if(result == JOptionPane.YES_OPTION) {
            save();
            System.exit(0);
        }
        if(result == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Private method
     * Opens a pop up window and adds the new book to the collection.
     */
    private void newBook()
    {
        JPanel nb = new JPanel(new BorderLayout(5,5));

        JPanel titlePanel = new JPanel(new BorderLayout(5,5));
        JPanel titleLabel = new JPanel();
        titleLabel.add(new JLabel("Title:", SwingConstants.RIGHT));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        JPanel titleInput = new JPanel();
        JTextField title = new JTextField(11);
        titleInput.add(title);
        titlePanel.add(titleInput, BorderLayout.CENTER);

        JPanel genreLabel = new JPanel();
        genreLabel.add(new JLabel("Genre:", SwingConstants.RIGHT));

        JPanel genre = new JPanel(new GridLayout(4,1,2,2));
        ButtonGroup group = new ButtonGroup();
        JRadioButton fant = new JRadioButton("Fantasy");
        group.add(fant);
        genre.add(fant);
        JRadioButton na = new JRadioButton("New Adult");
        group.add(na);
        genre.add(na);
        JRadioButton nf = new JRadioButton("Nonfiction");
        group.add(nf);
        genre.add(nf);
        JRadioButton yaf = new JRadioButton("Young Adult Fiction");
        yaf.setSelected(true);
        group.add(yaf);
        genre.add(yaf);

        nb.add(genreLabel, BorderLayout.WEST);
        nb.add(genre, BorderLayout.CENTER);
        nb.add(titlePanel, BorderLayout.NORTH);

        int result = JOptionPane.showConfirmDialog(this, nb, "New Book",
                JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            try {
                String bookName = title.getText();
                ButtonModel selected = group.getSelection();
                String genreType;
                if(selected.equals(fant.getModel())) { genreType = "fantasy"; }
                else if(selected.equals(na.getModel())) { genreType = "na"; }
                else if(selected.equals(nf.getModel())) { genreType = "nf"; }
                else { genreType = "yaf"; }

                project.newBookProject(bookName, genreType);

                addCurrentBookMenuItem( bookName);

                manager.setCurrentBook(bookName);
                bookNameLabel.setText(bookName);
            }
            catch(InvalidParameterException e) {
                println(e);
                newBook();
            }
        }
    }

    /**
     * Private method
     * Changes the interface to accessing all books' stats.
     */
    private void allBooks()
    {
        manager.selectViewingAllBooks();
        bookNameLabel.setText("All Books");
    }

    /**
     * Private method
     * Changes the interface to access one particular book.
     */
    private void selectBook(String bookName)
    {
        manager.setCurrentBook(bookName);
        bookNameLabel.setText(bookName);
    }

    /**
     * Private method
     * Adds a book to the projects select book menu.
     */
    private void addCurrentBookMenuItem(String book)
    {
        final String bookName = book;
        JMenuItem bn = new JMenuItem(bookName);
        bn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {selectBook(bookName);}
            });
        bookNamesArray.add(bn);
        currentBooks.add(bn);
    }

    /**
     * Private method
     * Creates a popup window to add a new entry to the current book.
     * Adds that new entry if currently working on a book.
     */
    private void addEntry()
    {
        JPanel ne = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(2,1,2,2));
        labels.add(new JLabel("Word Count:", SwingConstants.RIGHT));
        labels.add(new JLabel("Time Spent (in minutes):", SwingConstants.RIGHT));
        ne.add(labels, BorderLayout.WEST);

        JPanel inputs = new JPanel(new GridLayout(2,1,2,2));
        final JTextField wordCount = new JTextField();
        inputs.add(wordCount);
        JTextField timeSpent = new JTextField();
        inputs.add(timeSpent);
        ne.add(inputs, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, ne, "Add New Entry",
                JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            int count = Integer.parseInt(wordCount.getText());
            int time = Integer.parseInt(timeSpent.getText());
            boolean success = manager.addNewEntry(count, time);
            if(success == false) {
                JOptionPane.showMessageDialog(this, "Please select/create a project " +
                    "before adding a new entry.");
            }
        }
    }

    /**
     * Private method
     * Creates a popup window to edit the word count of a specific entry.
     */
    private void editEntryCount()
    {
        JPanel ec = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(2,1,2,2));
        labels.add(new JLabel("Entry Number:", SwingConstants.RIGHT));
        labels.add(new JLabel("New Word Count:", SwingConstants.RIGHT));
        ec.add(labels, BorderLayout.WEST);

        JPanel inputs = new JPanel(new GridLayout(2,1,2,2));
        JTextField entryNum = new JTextField();
        inputs.add(entryNum);
        JTextField newCount = new JTextField();
        inputs.add(newCount);
        ec.add(inputs, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, ec, "Edit Entry Word Count",
                JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            int number = Integer.parseInt(entryNum.getText());
            int count = Integer.parseInt(newCount.getText());
            boolean success = manager.editCurrentBook_entryCount(number, count);
            if(success == false) {
                JOptionPane.showMessageDialog(this, "Please select a project " +
                    "before attempting to edit an entry.");
            }
            else {
                JOptionPane.showMessageDialog(this, "Word count of entry " + number +
                    " successfully changed to " + count + ".");
            }
        }
    }

    /**
     * Private method
     * Creates a popup window to edit the time spent of a specific entry.
     */
    private void editEntryTime()
    {
        JPanel et = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(2,1,2,2));
        labels.add(new JLabel("Entry Number:", SwingConstants.RIGHT));
        labels.add(new JLabel("New Time Spent:", SwingConstants.RIGHT));
        et.add(labels, BorderLayout.WEST);

        JPanel inputs = new JPanel(new GridLayout(2,1,2,2));
        JTextField entryNum = new JTextField();
        inputs.add(entryNum);
        JTextField newTime = new JTextField();
        inputs.add(newTime);
        et.add(inputs, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, et, "Edit Entry Time Spent",
                JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            int number = Integer.parseInt(entryNum.getText());
            int time = Integer.parseInt(newTime.getText());
            boolean success = manager.editCurrentBook_entryTime(number, time);
            if(success == false) {
                JOptionPane.showMessageDialog(this, "Please select a project " +
                    "before attempting to edit an entry.");
            }
            else {
                JOptionPane.showMessageDialog(this, "Time spent of entry " + number +
                    " successfully changed to " + time + ".");
            }
        }
    }

    /**
     * Private method
     * Creates a popup window to edit the date of a specific entry.
     */
    private void editEntryDate()
    {
        JPanel ed = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(1,4,2,2));
        labels.add(new JLabel("Entry Number", SwingConstants.CENTER));
        labels.add(new JLabel("New Month", SwingConstants.CENTER));
        labels.add(new JLabel("New Day", SwingConstants.CENTER));
        labels.add(new JLabel("New Year", SwingConstants.CENTER));

        ed.add(labels, BorderLayout.NORTH);

        JPanel inputs = new JPanel(new GridLayout(1,4,2,2));
        JTextField entryNum = new JTextField();
        inputs.add(entryNum);
        JTextField newMonth = new JTextField();
        inputs.add(newMonth);
        JTextField newDay = new JTextField();
        inputs.add(newDay);
        JTextField newYear = new JTextField();
        inputs.add(newYear);
        ed.add(inputs, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, ed, "Edit Entry Date",
                JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            int number = Integer.parseInt(entryNum.getText());
            int month = Integer.parseInt(newMonth.getText());
            int day = Integer.parseInt(newDay.getText());
            int year = Integer.parseInt(newYear.getText());
            boolean success = manager.editCurrentBook_entryDate(number, year, month, day);
            if(success == false) {
                JOptionPane.showMessageDialog(this, "Please select a project " +
                    "before attempting to edit an entry.");
            }
            else {
                JOptionPane.showMessageDialog(this, "Date of entry " + number +
                    " successfully changed to " + month + "/" + day + "/" + year + ".");
            }
        }
    }

    /**
     * Sorts the entries by date of the current book being worked on.
     */
    private void sort()
    {
        boolean success = manager.sortCurrentEntries();
        if(success == false) {
            JOptionPane.showMessageDialog(this, "Please select a project before attempting " +
                "to sort its entries.");
        }
        else {
            JOptionPane.showMessageDialog(this, "Entries successfully sorted.");
        }
    }

    private void instructions()
    {
        JPanel instructionPanel = new JPanel(new BorderLayout());

        instructionPanel.add(new JLabel("How to use Book Manager"), BorderLayout.NORTH);

        JTextArea instructions = new JTextArea(18, 40);
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);

        instructions.append("~Welcome to Book Manager~\nThis menu it to lay out where the CS162 final project" +
            " specifications are located within this program as well as how to use this overall program.\n\n" +
            "~CS 162 Project Specification Locations~\n\n1. Gui to run your application.\n" +
            "Location: Class called 'GUI'\n\n4. Variety of components. At least 3 from list.\n" +
            "a. Button: Several used on the main GUI interface.\n     -Source Code: Most of the" +
            " buttons are used in the method 'makeButtonPane()'\n      the fourth method down in the GUI class" +
            " after the constructor\ne. Radio Buttons: Two are located on the main interface to the" +
            " left and another group are found in the 'Project' menu in the 'New Book' popup menu\n     -Source" +
            " Code: Located in the method 'makeButtonPane()' as well as in the\n      'newBook()' method, the tenth" +
            " method down after the constructor or half\n      way down the GUI's source code.\ng. Popup Menu: " +
            "Some of the menu items bring up a popup menu, including the 'New Book' under the 'Project' menu," +
            " the 'Add New' and the 'Edit' items under the 'Entry' menu, and both 'Help' menu options.\n     -Source" +
            "Code: Located in the 'newBook()' method which is tenth/half way\n      down, the 'addEntry()' and editEntry " +
            "methods, which are just over half way\n      down, and the 'instructions()' and 'about()' methods located " +
            "about\n      three-fourths of the way down the GUI source code.\n\n5. Menu bar with at least one menu and " +
            "two menu items.\nLocation: Menu bar with four menus along the top of the GUI interface.\n     -Source " +
            "Code: The 'makeMenuBar()' method, second method after the constructor.\n\n6. At least one text " +
            "component.\nLocation: JTextArea and two JTextFields on the main interface, as well as others in popup " +
            "menus.\n     -Source Code: In the 'makeContentPane()' method, third after the constructor,\n      as well as " +
            "the 'makeBottomPane()' method, fifth after the constructor or a third\n      of the way down.\n\n7. Implement" +
            " file I/O.\nLocation: The 'File' menu, 'Open', 'Save', and 'SaveAs' options.\n     -Source Code: The 'open()'," +
            " 'save()', and 'saveAs()' methods. They are the\n      sixth, seventh, and eighth methods down after the constructor" +
            " or almost\n      halfway down the source code.\n\n8. Use try/catch blocks and write at least one Exception class.\n" +
            "Location: InvalidParameterException class.\n     -Source Code: The 'BookList' class' methods, 'newBookProject()' " +
            "and\n      'getBook()', both throw this exception. They are the first and fourth\n      methods after the constructor. " +
            "BookManager catches this exception in the\n      'setCurrentBook()' method, about three fourths of the way down. The GUI\n" +
            "      class' 'newBook() method, about half way down, and 'addEntry()' method also\n      catch this method.\n\n9. Use " +
            "inheritance and one abstract class.\nLocation: The 'Book' class is abstract and is also the superclass that the " +
            "'Fantasy', 'NA', 'YAF', and 'NF' classes inherit from.\n     -Source Code: The 'Book' class has two abstract methods at " +
            "the bottom of the\n      source code, 'getTitle()' and 'getGenre()'.\n\n\n" +
            "~How to Use Book Manager~\n\nThere is a 'Demo.data' file available in the Projects folder that can be opened which " +
            "contains a sample program with a couple of books and several entries already input. This demo can simply be used to get " +
            "an idea of how this overall program can work.\nThese instructions, however, will relay how to start a project from " +
            "scratch.\n\n-Overview)\nUpon first opening the program the main window has a series of buttons on the main interface " +
            "and several menu options available on the menu bar.\nThe menu bar options are primarily used for creating, adding to, or " +
            "manipulating projects.\nThe buttons on the main window are used for viewing information and stats within the current " +
            "project being worked on.\n\n-Step 1) New Project\nUnder the 'Project' menu, select 'New Book'. In the popup menu, enter a title and " +
            "select a genre to create a new book project. Upon creation, you will automatically be working within that book. Each new " +
            "project made will be added to the 'Select Book' menu inside the 'Project' menu to allow you to cycle between projects." +
            "\n\n-Step 2) Adding Entries\nAfter you complete a writing session on your book, track your progress by adding a new Entry. In the 'Entry' " +
            "menu, select 'Add New'. Enter your word count and time spent in minutes that you just spent on writing your book. The " +
            "entry will be added to your book with today's date. See Step 4 to see how to edit an entry.\n\n-Step 3) Viewing Totals\nAt any time while " +
            "working on a project you can click the buttons on the main interface. The four big buttons on the top left display totals " +
            "within your project as well and displaying all your entries on the screen.\nTo view these totals for all of your projects " +
            "combined, click the 'All Books' option in the 'Project' menu.\n\n-Step 4) Viewing & Editing Entries\nClicking the 'View Entries' " +
            "button displays a numbered list of all the entries for the current project. If an entry is wrong, any of its three attributes can " +
            "be edited by clicking on the appropriate option in the 'Edit' menu inside the 'Entry' menu. The entry number required in each " +
            "option to edit is the number next to the entry on the displayed list. If upon entering a new entry, or editing, it is noticed " +
            "that the entries are not in order by date, the 'Sort' option in the 'Entry' menu will sort the entries by date\nNOTE: The displayed " +
            "list of entries will not be numbered when 'All Books' is selected, as you cannot edit an entry while viewing all projects.\n\n" +
            "-Step 5) Daily Averages by Months\nThe numbered '1', '3', '6', '9', and '12' buttons will display the daily average word count or " +
            "time spent(depending on which button is selected) on a project(or all projects) over the respective number of months.\n\n-Step 6) " +
            "Daily Averages Between Two Specific Dates\nThe 'Start Date' and 'End Date' inputs allow the daily averages to be displayed between " +
            "those two dates when the 'Submit' button is pressed. Be sure to input the dates as displayed(mm/dd/yyyy).\n\n-Step 7) Daily Averages " +
            "for Desired Amount of Days\nThe input box in the bottom left is used for finding the daily averages for any number of days in the " +
            "past. Simply input a number and it will display the daily average from the input number of days ago till today.\n\n-Step 8) Clear " +
            "Display\nThe 'Clear Display' button in the bottom right can be pressed any time to empty the display screen.\n\n-Step 9) Saving " +
            "and Opening a Project\nA project can be saved at any time using the 'Save' or 'SaveAs' option in the 'File' menu. The default " +
            "location that the files are saved to is a folder called 'Projects' located within this programs files. However, the user can " +
            "save the file anywhere on their system. Simply input a name to save the file under and the '.data' will be automatically added. " +
            "\nOpen a file simply by going to the 'Open' option under the 'File' menu and locate the desired project to open.");

        JScrollPane instructionPane = new JScrollPane(instructions);
        instructionPanel.add(instructionPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, instructionPanel, "Instructions and Help",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void pleaseOpen()
    {
        JOptionPane.showMessageDialog(this, "Please go to the 'File' and 'Open' menu and open the\n" +
            "'Demo.data' file to load a sample program for you to test.", "Open File to Start",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Private method
     * Opens a window to give basic info about this program.
     */
    private void about()
    {
        JPanel aboutPanel = new JPanel(new BorderLayout());

        Icon logo = new ImageIcon("pin.jpg");

        JPanel version = new JPanel(new GridLayout(2,1,2,2));
        JLabel programName = new JLabel("Book Manager", SwingConstants.CENTER);
        version.add(programName);
        JLabel versionNum = new JLabel("Version: 1.0", SwingConstants.CENTER);
        version.add(versionNum);

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.append("\n\n\nBook Manager is a newly designed program made specifically\n" +
            "       for authors that wish to track their productivity over time." +
            "\n\nThis program is still in its early stages as there are additional\n" +
            "    features and improvements planned and suggestions are\n" +
            "                                         encouraged." +
            "\n\nVisit the 'View Help' menu item for instructions on how to use\n" + 
            "          this program and for where the CS162 final project\n" + 
            "                             requirements are located.");

        JPanel created = new JPanel(new GridLayout (4,1,2,2));
        JLabel createdBy = new JLabel("Created by:", SwingConstants.CENTER);
        created.add(createdBy);
        JLabel author = new JLabel("Cameron Stanavige", SwingConstants.CENTER);
        created.add(author);
        JLabel createdFor = new JLabel("For:", SwingConstants.CENTER);
        created.add(createdFor);
        JLabel wife = new JLabel("Kjerstin Stanavige", SwingConstants.CENTER);
        created.add(wife);

        aboutPanel.add(version, BorderLayout.NORTH);
        aboutPanel.add(info, BorderLayout.CENTER);
        aboutPanel.add(created, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, aboutPanel, "About BookManager",
            JOptionPane.INFORMATION_MESSAGE, logo);
    }

    //ContentPane methods
    /**
     * Private method
     * Displays the word count for the current book or all books.
     */
    private void displayWordCount()
    {
        if(manager.checkCurrentBook() == true) {
            buttonOutput.setText(manager.getWordCount());
        }
        else if(manager.checkViewingAllBooks() == true) {
            buttonOutput.setText(project.getTotal_wordCount_allBooks());
        }
        else {
            buttonOutput.setText("Select a book");
        }
    }

    /**
     * Private method
     * Displays the time spent on the current book or all books.
     */
    private void displayTimeSpent()
    {
        if(manager.checkCurrentBook() == true) {
            buttonOutput.setText(manager.getTimeSpent());
        }
        else if(manager.checkViewingAllBooks() == true) {
            buttonOutput.setText(project.getTotal_timeCount_allBooks());
        }
        else {
            buttonOutput.setText("Select a book");
        }
    }

    /**
     * Private method
     * Displays the total number of entries for the current book or all books.
     */
    private void displayNumEntries()
    {
        if(manager.checkCurrentBook() == true) {
            buttonOutput.setText(manager.getNumEntries());
        }
        else if(manager.checkViewingAllBooks() == true) {
            buttonOutput.setText(project.getTotal_entries_allBooks());
        }
        else {
            buttonOutput.setText("Select a book");
        }
    }

    /**
     * Private method
     * Prints out the entries for the current book or all books.
     */
    private void displayEntries()
    {
        if(manager.checkCurrentBook() == true) {
            manager.printBookEntries();
        }
        else if(manager.checkViewingAllBooks() == true) {
            manager.printAll_bookEntries();
        }
        else {
            buttonOutput.setText("Select a book");
        }
    }

    /**
     * Private method
     * Gets the daily average word count or time spent between today and the
     * input number of previous days.
     * @param days The number of days in the past.
     */
    private void monthlyAverage(int days)
    {
        ButtonModel selected = averageGroup.getSelection();
        String option;
        if(selected.equals(countOption.getModel())) { option = "count"; }
        else { option = "time"; }

        if(manager.checkCurrentBook() == true) {
            if(option.equals("count")) {
                buttonOutput.setText(manager.averageWordCount(days));
            }
            else {
                buttonOutput.setText(manager.averageTimeSpent(days));
            }
        }
        else if(manager.checkViewingAllBooks() == true) {
            if(option.equals("count")) {
                buttonOutput.setText(project.averageCount_allBooks(days));
            }
            else {
                buttonOutput.setText(project.averageTime_allBooks(days));
            }
        }
        else {
            buttonOutput.setText("Select a book");
        }
    }

    /**
     * Private method
     * Gets the average word count or time spent between two imputed dates.
     */
    private void submit()
    {
        ButtonModel selected = averageGroup.getSelection();
        String option;
        if(selected.equals(countOption.getModel())) { option = "count"; }
        else { option = "time"; }

        String start = startInput.getText();
        String end = endInput.getText();
        if((start.equals("") || start.equals("mm/dd/yyyy")) || (end.equals("") || end.equals("mm/dd/yyyy"))) {
            buttonOutput.setText("Enter proper dates");
        }
        else if((start.length() != 10) || (end.length() != 10)) {
            buttonOutput.setText("Enter proper dates");
        }
        else if(manager.checkCurrentBook() == true) {
            if(option.equals("count")) {
                buttonOutput.setText(manager.processDates_wordCount(start, end));
            }
            else {
                buttonOutput.setText(manager.processDates_timeSpent(start, end));
            }
        }
        else if(manager.checkViewingAllBooks() == true) {
            if(option.equals("count")) {
                buttonOutput.setText(manager.processDates_wordCount(start, end));
            }
            else {
                buttonOutput.setText(manager.processDates_timeSpent(start, end));
            }
        }
        else {
            buttonOutput.setText("Select a book");
        }
        startInput.setText("mm/dd/yyyy");
        endInput.setText("mm/dd/yyyy");
    }

    /**
     * Private method
     * Gets the daily average word count or time spent between today and the
     * input number of previous days.
     */
    private void enter()
    {
        String numDays = averageInput.getText();
        if(numDays.equals("")) {
            buttonOutput.setText("Enter proper number");
        }
        else {
            int days = Integer.parseInt(numDays);
            monthlyAverage(days);
        }
        averageInput.setText("");
    }

    /**
     * Private method
     * Empties the two output areas.
     */
    private void clearDisplay()
    {
        outputArea.setText("");
        buttonOutput.setText("");
    }

    /**
     * Prints to the output area.
     * @param str The String to print.
     */
    public void print(String str)
    {
        outputArea.append(str);
        outputArea.setCaretPosition(outputArea.getText().length());
    }

    /**
     * Prints to the output area and goes to next line.
     * @param str The String to print.
     */
    public void println(Object str)
    {
        outputArea.append(str + "\n");
        outputArea.setCaretPosition(outputArea.getText().length());
    }

    /**
     * Prints a next line to the output area.
     */
    public void println()
    {
        outputArea.append("\n");
        outputArea.setCaretPosition(outputArea.getText().length());
    }
}
