package gui;

import graph.Arc;
import graph.Vertex;
import gui.figuresJComponents.NotOrientedArrowJComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.util.Scanner;

public class MainGUI extends JFrame implements ComponentListener {

    ActionType actionType = ActionType.MAKE_SYSTEM;

    private DrawableJPanel drawableJPanel;

    private JScrollPane scrollPane;

    private JToolBar toolbar;

    JMenuBar menuBar;

    public MainGUI() {

        super("Graph redactor");

        setLayout(null);
        this.setBounds(300, 60, 1000, 700);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addComponentListener(this);

        addMenuBar();
        addToolBar();
        addDrawableJPanel();
        addScrollPane();
    }

    private void addScrollPane() {
        scrollPane = new JScrollPane();
        scrollPane.setLocation(40, 10);
        scrollPane.setViewportView(drawableJPanel);
        scrollPane.setSize(new Dimension(this.getWidth() - 60, this.getHeight() - 70));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }

    private void addDrawableJPanel() {
        drawableJPanel = new DrawableJPanel(this);
        drawableJPanel.setPreferredSize(new Dimension(2000, 1500));

        this.add(drawableJPanel, BorderLayout.PAGE_START);
    }

    private void addToolBar() {
        toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);
        toolbar.setBackground(Color.gray);
        toolbar.setBounds(0, 0, 40, this.getHeight());

        JButton systemButton = new JButton();
        systemButton.setText("1");
        systemButton.setFont(new Font(Font.DIALOG,  Font.BOLD, 40));

        systemButton.addActionListener(event -> {
            actionType = ActionType.MAKE_SYSTEM;
            drawableJPanel.grabFocus();
        });

        JButton subSystemButton = new JButton();
        subSystemButton.setText("2");
        subSystemButton.setFont(new Font(Font.DIALOG,  Font.BOLD, 40));

        subSystemButton.addActionListener(e -> {
            actionType = ActionType.MAKE_SUB_SYSTEM;
            drawableJPanel.grabFocus();
        });

        JButton elementButton = new JButton();
        elementButton.setText("3");
        elementButton.setFont(new Font(Font.DIALOG,  Font.BOLD, 40));

        elementButton.addActionListener(event -> {
            actionType = ActionType.MAKE_ELEMENT;
            drawableJPanel.grabFocus();
        });


        JButton notOrientedArcButton = new JButton();
        notOrientedArcButton.setText("4");
        notOrientedArcButton.setFont(new Font(Font.DIALOG,  Font.BOLD, 40));

        notOrientedArcButton.addActionListener(event -> {
            actionType = ActionType.MAKE_NOT_ORIENTED_ARC;
            drawableJPanel.grabFocus();
        });

        toolbar.add(systemButton);
        toolbar.add(subSystemButton);

        toolbar.add(elementButton);

        toolbar.add(notOrientedArcButton);

        this.add(toolbar);
    }

    private void addMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.gray);
        menuBar.setBounds(0, 0, this.getWidth(), 20);

        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setPreferredSize(new Dimension(50, 20));
        menuBar.add(fileMenu);

        JMenuItem save = new JMenuItem("Save");

        save.addActionListener(e -> saveToFile());
        fileMenu.add(save);

        JMenuItem open = new JMenuItem("Open");
        fileMenu.add(open);

        open.addActionListener(e -> openFile());


    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load from");

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            drawableJPanel.removeAll();
            drawableJPanel.graph.setOfVertexes.clear();
            drawableJPanel.graph.setOfArcs.clear();
            addAllComponents(fileChooser);
        }
    }

    private void addAllComponents(JFileChooser fileChooser) {
        try {
            File selectedFile = fileChooser.getSelectedFile();
            FileReader fileReader = new FileReader(selectedFile);
            Scanner scanner = new Scanner(fileReader);

            int amountOfVertexes = 0;
            int amountOfArcs = 0;

            if (scanner.hasNextInt())
                amountOfVertexes = scanner.nextInt();

            if (scanner.hasNextInt())
                amountOfArcs = scanner.nextInt();

            addALlVertexes(amountOfVertexes, scanner);
            addAllArcs(amountOfArcs, scanner);


            fileReader.close();
            revalidate();
            repaint();
            drawableJPanel.grabFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAllArcs(int amountOfArcs, Scanner scanner) {
        for (int i = 0; i < amountOfArcs; ++i) {
            int sourceX = scanner.nextInt();
            int sourceY = scanner.nextInt();
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();
            int weight = scanner.nextInt();

            int intIsDirected = scanner.nextInt();
            boolean isDirected = intIsDirected == 1;

            figures.Point sourcePoint = drawableJPanel.findTarget(sourceX, sourceY);
            figures.Point targetPoint = drawableJPanel.findTarget(targetX, targetY);

            drawableJPanel.graph.addArc(new Arc(sourcePoint, targetPoint, isDirected, weight));

            drawableJPanel.add(new NotOrientedArrowJComponent(sourcePoint, targetPoint, weight));

        }
    }

    private void addALlVertexes(int amountOfVertexes, Scanner scanner) {
        for (int i = 0; i < amountOfVertexes; ++i) {
            int type = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String identifier = scanner.nextLine();

            if (type == 0) {
                drawableJPanel.createSystem(x, y, identifier);
            }
            if (type == 1) {
                drawableJPanel.createSubSystem(x, y, identifier);
            }
            if (type == 2) {
                drawableJPanel.createElement(x, y, identifier);
            }
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save to");
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {

            try {
                pushAllInfo(fileChooser);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void pushAllInfo(JFileChooser fileChooser) throws IOException {
        File selectedFile = fileChooser.getSelectedFile();
        FileWriter fileWriter = new FileWriter(selectedFile);

        int amountOfVertexes = drawableJPanel.graph.setOfVertexes.size();
        int amountOfArcs = drawableJPanel.graph.setOfArcs.size();


        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();
        stringBuilder.append(amountOfVertexes).append(" ");
        stringBuilder.append(amountOfArcs).append("\n");
        fileWriter.write(stringBuilder.toString(), 0, stringBuilder.toString().length());

        pushVertexes(amountOfVertexes, stringBuilder, fileWriter);
        pushArcs(amountOfArcs, stringBuilder, fileWriter);

        fileWriter.close();
    }

    private void pushArcs(int amountOfArcs, StringBuilder stringBuilder, FileWriter fileWriter) throws IOException {
        for (int i = 0; i < amountOfArcs; ++i) {
            Arc arc = drawableJPanel.graph.setOfArcs.get(i);
            stringBuilder.delete(0, stringBuilder.length());

            int sourceX = arc.sourcePoint.x;
            int sourceY = arc.sourcePoint.y;
            int targetX = arc.targetPoint.x;
            int targetY = arc.targetPoint.y;
            int weight = arc.weight;
            boolean isDirected = arc.isDirected;
            int intIsDirected = isDirected ? 1 : 0;

            stringBuilder.append(sourceX).append(" ").append(sourceY).append(" ");
            stringBuilder.append(targetX).append(" ").append(targetY).append(" ");
            stringBuilder.append(weight).append(" ").append(intIsDirected).append("\n");

            fileWriter.write(stringBuilder.toString());
        }
    }

    private void pushVertexes(int amountOfVertexes, StringBuilder stringBuilder, FileWriter fileWriter) throws IOException {
        for (int i = 0; i < amountOfVertexes; ++i) {
            Vertex vertex = drawableJPanel.graph.setOfVertexes.get(i);
            stringBuilder.delete(0, stringBuilder.length());

            figures.Point point = vertex.point;
            String identifier = vertex.identifier;

            stringBuilder.append(vertex.type).append(" ");
            stringBuilder.append(point.x).append(" ");
            stringBuilder.append(point.y).append(" ");
            stringBuilder.append(identifier).append("\n");
            fileWriter.write(stringBuilder.toString());
        }
    }

    private boolean isConnected(Vertex vertex1, Vertex vertex2) {
        for (int i = 0; i < drawableJPanel.graph.setOfArcs.size(); ++i) {
            Arc currentArc = drawableJPanel.graph.setOfArcs.get(i);

            if ((currentArc.sourcePoint.equals(vertex1.point) && currentArc.targetPoint.equals(vertex2.point))
                    || (currentArc.targetPoint.equals(vertex1.point) && currentArc.sourcePoint.equals(vertex2.point))) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        MainGUI mainGUI = new MainGUI();
        mainGUI.setVisible(true);
    }


    @Override
    public void componentResized(ComponentEvent e) {
        toolbar.setSize(40, this.getHeight());
        scrollPane.setSize(new Dimension(this.getWidth() - 60, this.getHeight() - 70));
        revalidate();
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
