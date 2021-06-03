package gui.figuresJComponents;

import figures.Circle;
import figures.Point;

import java.awt.*;

public class SystemJComponent extends CircleJComponent{
    public SystemJComponent(){super();}

    public SystemJComponent(Point point,int type) {
        super(point,type);
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;;
        graphics2D.setColor(this.color);
        if (circle.point != null) {
            graphics2D.setStroke(new BasicStroke(4));
            graphics2D.drawRect(circle.point.x-Circle.RADIUS,circle.point.y-Circle.RADIUS,70,30);
            graphics2D.setColor(Color.darkGray);
            graphics2D.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
            graphics2D.drawString("System",circle.point.x-Circle.RADIUS/2,circle.point.y+Circle.RADIUS/2);
            graphics2D.setColor(Color.black);
            graphics2D.setFont(new Font(Font.DIALOG, Font.ITALIC, 20));
            graphics2D.drawString(circle.identifier, circle.point.x, circle.point.y + 35);
        }
    }
}
