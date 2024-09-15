package functions;

import java.util.logging.XMLFormatter;

public class LinkedListTabulatedFunction extends AbstractTabulateFunction implements TabulatedFunction
{
    class Node
    {
        public Node next;
        public Node prev;
        public double x;
        public  double y;
        public Node(final double x, final double y)
        {
            this.x = x;
            this.y = y;
        }
        public Node(final double x, final double y, final Node next, final Node prev)
        {
            this(x,y);
            this.next = next;
            this.prev = prev;
        }
    }
    private Node head;
    private void addNode(final double x, final double y)
    {
        if(head == null)
        {
            head = new Node(x,y);
            head.next = head;
            head.prev = head;
        }
        else
        {
            Node newNode = new Node(x,y);
            newNode.prev= head.prev;
            newNode.prev.next = newNode;
            newNode.next = head;
            head.prev = newNode;
        }
        ++count;
    }
    private Node getNode(int index)
    {
        Node cur = head;
        if(index > count / 2)
        {
            for (int i = 0; i < index; ++i)
                cur = cur.next;
        }
        else
        {
            for (int i = 0; i < count - index; i++)
                cur = cur.prev;

        }
        return cur;
    }
    public LinkedListTabulatedFunction(double[] x, double[] y)
    {
        for (int i = 0; i < x.length; ++i)
        {
            addNode(x[i],y[i]);
        }
    }
    public LinkedListTabulatedFunction(MathFunction source, double xFrom,double xTo, int count)
    {
        if(xFrom > xTo)
        {
            xFrom = xFrom+xTo;
            xTo = xFrom - xTo;
            xFrom = xFrom - xTo;
        }
        if(xFrom == xTo)
        {
            for (int i = 0; i < count; i++)
            {
                addNode(xFrom,source.apply(xFrom));
            }
        }
        else
        {
            double deltaX = (xFrom - xTo) / count;
            double x;
            for (int i = 0; i < count - 1; i++)
            {
            x = xFrom + i * deltaX;
            addNode(x,source.apply(x));
            }
            if(head != null)
                addNode(xTo,source.apply(xTo));
        }
    }
    @Override
    public double leftBound()
    {
        return head.x;
    }
    @Override
    public double rightBound()
    {
        return head.prev.x;
    }
    @Override
    public double getX(int index)
    {
        return getNode(index).x;
    }
    @Override
    public double getY(int index)
    {
        return getNode(index).y;
    }
    @Override
    public void setY(int index,double y)
    {
        getNode(index).y = y;
    }

    @Override
    public int indexOfX(double x)
    {
        Node cur = head;
        int i = 0;
        do{
            if(cur.x == x)
                return i;
            ++i;
            cur = cur.next;
        }while (cur != head);
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x)
    {
        Node cur = head;
        int i = 0;
       do
       {
           if(cur.x >= x)
               return i == 0? 0: i - 1;
           ++i;
           cur = cur.next;
       }while (cur != head);
       return count;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return 0;
    }

    @Override
    protected double extrapolateRight(double x) {
        return 0;
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        return 0;
    }

    @Override
    public int indexOfY(double y)
    {
        Node cur = head;
        int i = 0;
        do{
            if(cur.y == y)
                return i;
            ++i;
            cur = cur.next;
        }while (cur != head);
        return -1;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

}
