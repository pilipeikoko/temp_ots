package gui.figuresJComponents;

import figures.Circle;
import figures.Arrow;
import figures.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class NotOrientedArrowJComponent extends JComponent {
    public Arrow arrow;
    protected Color color;
    protected boolean isFinish = false;

    public Line2D line;

    int weight;

    public NotOrientedArrowJComponent() {

    }

    public NotOrientedArrowJComponent(Point sourcePoint, Point targetPoint) {
        this.arrow = new Arrow(sourcePoint, targetPoint, false);
        this.color = Color.black;
        this.weight = 1;
    }

    public NotOrientedArrowJComponent(Point sourcePoint, Point targetPoint, int weight) {
        this.arrow = new Arrow(sourcePoint, targetPoint, false);
        this.color = Color.black;
        this.weight = weight;
        isFinish = true;
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(color);

        Point targetPoint = arrow.targetPoint;
        Point sourcePoint = arrow.sourcePoint;
        int radius = Circle.RADIUS;

        double k = (double) (targetPoint.y - sourcePoint.y) / (targetPoint.x - sourcePoint.x);

        double a = Math.sqrt(Math.pow(radius, 2) / (Math.pow(k, 2) + 1));
        double b = a * k;
        int sourceDy, sourceDx, targetDy, targetDx;

        if (targetPoint.x > sourcePoint.x) {
            sourceDy = (int) (sourcePoint.y + b);
            sourceDx = (int) (sourcePoint.x + a);
            targetDy = (int) (targetPoint.y - b);
            targetDx = (int) (targetPoint.x - a);
        } else if (targetPoint.x < sourcePoint.x) {
            sourceDy = (int) (sourcePoint.y - b);
            sourceDx = (int) (sourcePoint.x - a);
            targetDy = (int) (targetPoint.y + b);
            targetDx = (int) (targetPoint.x + a);
        } else {
            sourceDy = targetPoint.y > sourcePoint.y ? sourcePoint.y + radius : sourcePoint.y - radius;
            sourceDx = sourcePoint.x;
            targetDy = sourcePoint.y > targetPoint.y ? targetPoint.y + radius : targetPoint.y - radius;
            targetDx = targetPoint.x;
        }

        if (isFinish)
            line = new Line2D.Double(sourceDx, sourceDy, targetDx, targetDy);
        else
            line = new Line2D.Double(sourceDx, sourceDy, targetPoint.x, targetPoint.y);

        graphics2D.draw(line);
        graphics2D.setColor(Color.red);

        int differenceY = sourcePoint.y > targetPoint.y ? 10 : -10;
        int differenceX = sourcePoint.x > targetPoint.x ? -10 : 10;

        int midX = (targetPoint.x + sourcePoint.x) / 2 + differenceX;
        int midY = (targetPoint.y + sourcePoint.y) / 2 + differenceY;

        if (weight != 1)
            graphics2D.drawString(String.valueOf(weight), midX, midY);

    }

    public void changeTarget(Point point) {
        isFinish = true;
        this.arrow.targetPoint = point;
    }

    public void chooseObject() {
        this.color = Color.green;
    }

    public void rejectObject() {
        this.color = Color.black;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
