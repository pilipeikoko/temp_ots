package gui;

import figures.Point;

public class CircleMoveThread extends Thread {

    private final DrawableJPanel jPanel;
    private final Point point;

    boolean isActive;

    public CircleMoveThread(DrawableJPanel jPanel, Point point) {
        this.jPanel = jPanel;
        this.point = point;
        isActive = true;
    }

    @Override
    public void run() {

        while (isActive) {

            java.awt.Point mousePosition = jPanel.getMousePosition();

            if (mousePosition != null) {
                point.x = mousePosition.x;
                point.y = mousePosition.y;
                jPanel.revalidate();
                jPanel.repaint();
            }
        }

    }

    public void disable() {
        isActive = false;
        interrupt();
    }
}
