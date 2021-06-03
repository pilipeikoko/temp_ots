package gui.figuresJComponents;

import figures.Circle;
import figures.Point;

import java.awt.*;

public class ElementJComponent extends CircleJComponent{
    public ElementJComponent(){super();}

    public ElementJComponent(Point point,int type) {
        super(point,type);
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(color);
        graphics2D.drawRect(circle.point.x-Circle.RADIUS,circle.point.y-Circle.RADIUS,70,30);
        //  graphics2D.drawOval(circle.point.x-Circle.RADIUS,circle.point.y-Circle.RADIUS,24,24);
        graphics2D.setColor(Color.darkGray);
        graphics2D.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        graphics2D.drawString("Element",circle.point.x-Circle.RADIUS/2,circle.point.y+Circle.RADIUS/2);
        graphics2D.setColor(Color.black);
        graphics2D.setFont(new Font(Font.DIALOG, Font.ITALIC, 12));
       // graphics2D.drawRect(circle.point.x - Circle.RADIUS, circle.point.y - Circle.RADIUS, 2 * Circle.RADIUS, 2 * Circle.RADIUS);
        graphics2D.drawString(circle.identifier, circle.point.x + 60, circle.point.y + 30);
    }
}
