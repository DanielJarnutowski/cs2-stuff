/*
Daniel Jarnutowski
email: naruto016@live.com
 */
import java.util.ArrayList; 
import java.util.Collections;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class GraphPanel extends JPanel
{
    private final int SIZE = 10;  // radius of each node

    private Point point1 = null, point2 = null;
    private double alpha;
    private ArrayList<Point> nodeList;   // Graph nodes
    private ArrayList<Edge> edgeList;    // Graph edges

    private JButton create,delete, pathbutton; 

    static ArrayList<Integer> path= new ArrayList<Integer>();
    private JLabel mode, startnode, endnode;
    private static int[][] a = new int[100][100];  // Graph adjacency matrix
    private static ArrayList<String> stringList = new ArrayList<String>(); //String array
    JTextField input1, input2;
    boolean create1 = true;
    //int nodey = Integer.parseInt(input2.getText());
    //int nodex = Integer.parseInt(input1.getText());

    public GraphPanel() //constructor
    {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));// sets box layout

        create = new JButton("Create");
        delete = new JButton("Delete");
        mode = new JLabel("Create mode");
        startnode = new JLabel("X node");
        endnode = new JLabel("Y node");
        startnode.setForeground(Color.blue);
        startnode.setFont(mode.getFont().deriveFont(34.0f));
        endnode.setForeground(Color.blue);
        endnode.setFont(mode.getFont().deriveFont(34.0f));
        pathbutton= new JButton("Find Path");
        input1 = new JTextField(10);
        input2 = new JTextField(10);
        input1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25)); // makes size of texfield smaller
        input2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        create.setForeground(Color.green);
        create.addActionListener (new ButtonListener2()); 
        delete.addActionListener (new ButtonListener2());
        pathbutton.addActionListener( new ButtonListener3());
        input1.addActionListener(new ButtonListener3());
        input2.addActionListener(new ButtonListener3());

        nodeList = new ArrayList<Point>();
        edgeList = new ArrayList<Edge>();

        GraphListener listener = new GraphListener();
        addMouseListener (listener);
        addMouseMotionListener (listener);

        JButton print = new JButton("Print adjacency matrix");
        print.addActionListener (new ButtonListener());

        setBackground (Color.black);
        setPreferredSize (new Dimension(400, 300)); //sets size
        add(print);

        add(create);

        add(delete);

        add(pathbutton);

        add(mode);//adds buttons and labels
        add(Box.createRigidArea(new Dimension(0,700)));
        add(startnode);
        add(input1);
        add(endnode);
        add(input2);

        mode.setForeground(Color.green); //sets label color to green
        mode.setFont(mode.getFont().deriveFont(34.0f)); // change font size of label

        create.setAlignmentX(Component.RIGHT_ALIGNMENT); //right alignment for all buttons and label
        delete.setAlignmentX(Component.RIGHT_ALIGNMENT);
        print.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mode.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pathbutton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        input1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        input2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        startnode.setAlignmentX(Component.RIGHT_ALIGNMENT);
        endnode.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }

    private void arrow(Graphics page, int x, int y, int len, double alpha)//arrow method to get arrows at end of line segments
    {
        page.setColor (Color.green);//sets arrow color to green
        int x1 = x+(int)(len*Math.sin(alpha));
        int y1 = y+(int)(len*Math.cos(alpha));
        page.drawLine (x, y, x1, y1);
        page.drawLine (x1, y1, x1+(int)(20*Math.sin(alpha+2.5)), y1+(int)(20*Math.cos(alpha+2.5)));
        page.drawLine (x1, y1, x1+(int)(20*Math.sin(alpha+3.7)), y1+(int)(20*Math.cos(alpha+3.7)));
    }

    public void paintComponent (Graphics page)//draws graph
    {
        super.paintComponent(page);
        // Draws the edge that is being dragged
        page.setColor (Color.green);
        if (point1 != null && point2 != null) {
            page.drawLine (point1.x, point1.y, point2.x, point2.y);
            alpha = Math.atan((double)(point2.y-point1.y)/(point2.x-point1.x));
            if (point1.x > point2.x) alpha = alpha + Math.PI;
            if (point1.x < point2.x && point1.y > point2.y) alpha = alpha + 2*Math.PI;
            page.drawString(String.valueOf(alpha),5,15);

            arrow(page,point2.x,point2.y,0,1.57-alpha);// creates arrow head
        }

        for (int i=0; i<nodeList.size(); i++) {//draws nodes
            page.setColor (Color.green);
            page.fillOval (nodeList.get(i).x-SIZE, nodeList.get(i).y-SIZE, SIZE*2, SIZE*2);
            page.setColor (Color.black); 
            page.drawString (String.valueOf(i), nodeList.get(i).x-SIZE/2, nodeList.get(i).y+SIZE/2); 
        }

        for (int i=0; i<edgeList.size(); i++) {//draw
            Point point1 = edgeList.get(i).a;
            Point point2 = edgeList.get(i).b; 

            page.setColor (Color.green); //set mode button color to green
            page.drawLine (edgeList.get(i).a.x, edgeList.get(i).a.y,edgeList.get(i).b.x, edgeList.get(i).b.y); //draws edges
            alpha = Math.atan((double)(point2.y-point1.y)/(point2.x-point1.x));
            if (point1.x > point2.x) alpha = alpha + Math.PI;
            if (point1.x < point2.x && point1.y > point2.y) alpha = alpha + 2*Math.PI;

            arrow(page,point2.x,point2.y,0,1.57-alpha);// creates arrow head
        }

    }

    private class GraphListener implements MouseListener, MouseMotionListener
    {
        public void mouseClicked (MouseEvent event)
        {

            if (create1 == true) //if create button is pressed add nodes
                nodeList.add(event.getPoint());
            else if(create1 == false) //if satement for if delete button pressed 
            {

                for (int i=0; i < nodeList.size(); i++)

                {
                    Point2D point1;
                    Point2D point2 = new Point2D.Double(event.getPoint().x, event.getPoint().y);    
                    point1 = new Point2D.Double(nodeList.get(i).x, nodeList.get(i).y);

                    if (point2.distance(point1) <2 *SIZE)

                    {
                        nodeList.remove(i); // delete nodes

                    }
                    repaint();
                }

                for (int i=0; i < edgeList.size(); i++)
                {
                    Point point3 = event.getPoint();
                    if ((distance(edgeList.get(i).a, point3)+distance(point3, edgeList.get(i).b)-distance(edgeList.get(i).a,edgeList.get(i).b))<SIZE) //distance formula used to delete edges

                        edgeList.remove(i); //deletes edges 

                }
                repaint();
            }

            repaint(); 
        }

        public void mousePressed (MouseEvent event)
        {

            point1 = event.getPoint();

        }

        public void mouseDragged (MouseEvent event)
        {

            point2 = event.getPoint();
            repaint(); 

        }

        public void mouseReleased (MouseEvent event)
        {

            point2 = event.getPoint();
            if ((point1.x != point2.x || point1.y != point2.y) && create1 == true)
            {
                edgeList.add(new Edge(point1,point2)); //creates new edge when mouse button is realesed
                repaint();
            }

        }

        public void mouseEntered (MouseEvent event) {}

        public void mouseExited (MouseEvent event) {}

        public void mouseMoved (MouseEvent event) {}
    }

    private class Edge {
        Point a, b;

        public Edge(Point a, Point b) 
        {
            this.a = a;
            this.b = b;
        }
    }
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {

            //Initializes graph adjacency matrix
            for (int i=0; i<nodeList.size(); i++)
                for (int j=0; j<nodeList.size(); j++) a[i][j]=0;

            // Includes the edges in the graph adjacency matrix
            for (int i=0; i<edgeList.size(); i++)
            {
                for (int j=0; j<nodeList.size(); j++)
                    if (distance(nodeList.get(j),edgeList.get(i).a)<=SIZE+3)
                        for (int k=0; k<nodeList.size(); k++)
                            if (distance(nodeList.get(k),edgeList.get(i).b)<=SIZE+3) 
                            {
                                System.out.println(j+"->"+k);
                                a[j][k]=1;
                            }
            }
            // Prints the graph adjacency matrix
            for (int i=0; i<nodeList.size(); i++)
            {
                for (int j=0; j<nodeList.size(); j++)
                    System.out.print(a[i][j]+"\t");
                System.out.println();
            }    
        }
    }
    // Euclidean distance function      
    private int distance(Point p1, Point p2) {
        return (int)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y)); // returns distance value from point to point

    }

    private class ButtonListener2 implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            if 

            (event.getSource()==create) // if statement for create button
            {
                create1 = true; // sets boolean value true when create button is pressed 
                mode.setText("Create"); //label shows create when create button is pressed
                create.setForeground(Color.green); //changes color for create button to green when create is pressed
                delete.setForeground(Color.black); // makes sure delete button isn't highlighed when create button is pressed
            }
            else if 

            (event.getSource()==delete) //if statement for delete button
            {
                create1 = false; //  sets boolean value false when delete button is pressed 
                delete.setForeground(Color.green);// sets delete button to green when pressed
                create.setForeground(Color.black);//makes sure create button isn't highlighted when delete is pressed
                mode.setText("Delete"); // sets label text to delete when delete button is pressed
            }

        }
    }

    private class ButtonListener3 implements ActionListener // button listener for path finding
    {
        public void actionPerformed (ActionEvent event)
        {
            

            if (event.getSource() == pathbutton)// button for getting path
           
               // stringList.clear();
                if (findPath(Integer.parseInt(input1.getText()),Integer.parseInt(input2.getText())) == true) { // compares input integers to nodes created and if path is made from 1st node to last node as specified
                    //Collections.reverse(stringList);
                   // for(int i =0; i< stringList.size(); i++) 
                       // System.out.println(stringList.get(i)); //loops throught nodes to print them all
                    
                    path.add(0); // add first node to array
                    Collections.reverse(path); // reverses array list order to get proper order
                    System.out.println(path);// prints the path
                }
                else
                {
                    System.out.println("There is no path"); // if there is no path the user is notified

                }

        }    
    }         
    public static void main (String[] args)
    {
        
        System.out.println(path);
    }

    public static boolean findPath(int x, int y) 
    {
        boolean found = false;
        if (a[x][y]==1) 
        {
            System.out.println(x + " -> " + y );
            //stringList.add(x + " -> " + y );
            found = true;
         
            path.add(y);
        } 
        else 

        
        {
            int z;
            for (z= a.length - 1; !found& z > 0; z--) 
            { 
                if (a[x][z]==1) 
                {
                    a[x][z]=2; 
                    found = findPath(z,y);
                    
                }
                if (found){
                    
                     
                    path.add(z);
                    //stringList.add(x + " -> " + z );
                    System.out.println(x + " -> " + z);
                }
                
            }
        }
        return found;

    }

}
