package gui;

import figures.Point;
import gui.figuresJComponents.CircleJComponent;
import gui.figuresJComponents.NotOrientedArrowJComponent;
import graph.Vertex;

import javax.swing.*;
import java.awt.*;

public class ComponentMenuBarThread extends Thread {

    private JMenuBar componentMenuBar;
    private final DrawableJPanel drawableJPanel;
    private JFrame getIdentifierFrame;

    public ComponentMenuBarThread(DrawableJPanel panel) {
        this.drawableJPanel = panel;
    }

    @Override
    public void run() {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        LayoutManager grid = new GridLayout(2, 0);

        componentMenuBar = new JMenuBar();
        componentMenuBar.setFont(new Font("TimesRoman", Font.BOLD, 12));
        componentMenuBar.setLayout(grid);
        componentMenuBar.setBounds(drawableJPanel.getMousePosition().x + DrawableJPanel.radius, drawableJPanel.getMousePosition().y, 140, 50);

        if (drawableJPanel.chosenComponent instanceof CircleJComponent) {

            JButton changeIdentifierButton = new JButton("Change identifier");
            JButton removeObjectButton = new JButton("Remove object");

            changeIdentifierButton.addActionListener(e -> changeIdentifier(dimension));

            removeObjectButton.addActionListener(e -> deleteComponent());

            componentMenuBar.add(changeIdentifierButton);
            componentMenuBar.add(removeObjectButton);

        } else if (drawableJPanel.chosenComponent instanceof NotOrientedArrowJComponent) {

            JButton changeWeightButton = new JButton("Change weight");
            JButton removeObjectButton = new JButton("Remove object");

            changeWeightButton.addActionListener(e -> changeWeight(dimension));

            removeObjectButton.addActionListener(e -> deleteComponent());

            componentMenuBar.add(changeWeightButton);
            componentMenuBar.add(removeObjectButton);

        }
        drawableJPanel.add(componentMenuBar);

    }

    private void changeWeight(Dimension dimension) {
        JFrame getWeightFrame = new JFrame("Set identifier");
        getWeightFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getWeightFrame.setResizable(false);
        getWeightFrame.setLayout(new GridLayout(2, 0));
        getWeightFrame.setPreferredSize(new Dimension(200, 100));
        getWeightFrame.setVisible(true);
        getWeightFrame.setLocation(dimension.width / 2 - 50, dimension.height / 2 - 50);

        JButton OKButton = new JButton("Ok");
        JTextField textField = new JTextField("");

        textField.setSize(180, 80);

        getWeightFrame.add(textField);
        getWeightFrame.add(OKButton);
        getWeightFrame.pack();

        OKButton.addActionListener(e12 -> {
            drawableJPanel.componentMenuBarThread.disable();

            Point sourcePoint = ((NotOrientedArrowJComponent) drawableJPanel.chosenComponent).arrow.sourcePoint;
            Point targetPoint = ((NotOrientedArrowJComponent) drawableJPanel.chosenComponent).arrow.targetPoint;

            for (int i = 0; i < drawableJPanel.graph.setOfArcs.size(); ++i) {
                if (drawableJPanel.graph.setOfArcs.get(i).sourcePoint == sourcePoint
                        && drawableJPanel.graph.setOfArcs.get(i).targetPoint == targetPoint) {
                    try {
                        drawableJPanel.graph.setOfArcs.get(i).weight = Integer.parseInt(textField.getText());
                        ((NotOrientedArrowJComponent) drawableJPanel.chosenComponent).setWeight(Integer.parseInt(textField.getText()));
                    } catch (NumberFormatException exception) {
                        drawableJPanel.graph.setOfArcs.get(i).weight = 1;
                        ((NotOrientedArrowJComponent) drawableJPanel.chosenComponent).setWeight(1);
                    }
                    break;
                }
            }

            getWeightFrame.dispose();
            drawableJPanel.revalidate();
            drawableJPanel.repaint();
            drawableJPanel.grabFocus();
        });
    }

    private void deleteComponent() {
        drawableJPanel.deleteChosenComponent();
        drawableJPanel.componentMenuBarThread.disable();
        drawableJPanel.grabFocus();
    }

    private void changeIdentifier(Dimension dimension) {
        getIdentifierFrame = new JFrame("Set identifier");
        getIdentifierFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getIdentifierFrame.setResizable(false);
        getIdentifierFrame.setLayout(new GridLayout(2, 0));
        getIdentifierFrame.setPreferredSize(new Dimension(200, 100));
        getIdentifierFrame.setVisible(true);
        getIdentifierFrame.setLocation(dimension.width / 2 - 50, dimension.height / 2 - 50);

        JButton OKButton = new JButton("Ok");
        JTextField textField = new JTextField("");

        textField.setSize(180, 80);

        getIdentifierFrame.add(textField);
        getIdentifierFrame.add(OKButton);
        getIdentifierFrame.pack();

        OKButton.addActionListener(e1 -> {
            drawableJPanel.componentMenuBarThread.disable();
            getIdentifierFrame.dispose();

            Point point = ((CircleJComponent) drawableJPanel.chosenComponent).circle.point;

            Vertex vertex;
            for (int i = 0; i < drawableJPanel.graph.setOfVertexes.size(); ++i) {
                vertex = drawableJPanel.graph.setOfVertexes.get(i);
                if (vertex.point.equals(point)) {
                    drawableJPanel.graph.setOfVertexes.get(i).setIdentifier(textField.getText());
                    ((CircleJComponent) drawableJPanel.chosenComponent).setIdentifier(textField.getText());
                    break;
                }
            }
            drawableJPanel.revalidate();
            drawableJPanel.repaint();
            drawableJPanel.grabFocus();
        });

    }

    public void disable() {
        drawableJPanel.remove(componentMenuBar);
        interrupt();
    }
}
