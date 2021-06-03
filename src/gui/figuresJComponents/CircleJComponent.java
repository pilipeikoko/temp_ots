package gui.figuresJComponents;

import figures.Circle;
import figures.Point;

import javax.swing.*;
import java.awt.*;

public class CircleJComponent extends JComponent {
    public Circle circle;
    public Color color;
    public int type;

    public CircleJComponent() {
    }

    public CircleJComponent(Point point, int type) {
        this.type = type;
        this.circle = new Circle(point);
        this.color = Color.black;
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(color);
        graphics2D.drawRect(circle.point.x-Circle.RADIUS,circle.point.y-Circle.RADIUS,100,30);
        //  graphics2D.drawOval(circle.point.x-Circle.RADIUS,circle.point.y-Circle.RADIUS,24,24);
        graphics2D.setColor(Color.darkGray);
        graphics2D.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        graphics2D.drawString("Subsystem",circle.point.x-Circle.RADIUS/2,circle.point.y+Circle.RADIUS/2);
        graphics2D.setColor(Color.black);
        graphics2D.setFont(new Font(Font.DIALOG, Font.ITALIC, 12));
        //graphics2D.drawOval(circle.point.x - Circle.RADIUS, circle.point.y - Circle.RADIUS, 2 * Circle.RADIUS, 2 * Circle.RADIUS);
        graphics2D.drawString(circle.identifier, circle.point.x + 60, circle.point.y + 30);
    }

    public void chooseObject() {
        this.color = Color.lightGray;
    }

    public void rejectObject() {
        this.color = Color.black;
    }

    public void setIdentifier(String identifier) {
        this.circle.identifier = identifier;
    }


}