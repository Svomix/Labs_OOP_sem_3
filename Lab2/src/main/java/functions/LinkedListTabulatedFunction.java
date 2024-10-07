package functions;

import java.util.Iterator;

public class LinkedListTabulatedFunction extends AbstractTabulateFunction implements TabulatedFunction, Insertable, Removable {
    static class Node {
        public Node next;
        public Node prev;
        public double x;
        public double y;

        public Node(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        public Node(double x, double y, Node next, Node prev) {
            this(x, y);
            this.next = next;
            this.prev = prev;
        }
    }
    private Node head;

    private void addNode(double x, double y) {
        if (head == null) {
            head = new Node(x, y);
            head.next = head;
            head.prev = head;
        } else {
            Node newNode = new Node(x, y);
            newNode.prev = head.prev;
            newNode.prev.next = newNode;
            newNode.next = head;
            head.prev = newNode;
        }
        ++count;
    }

    protected Node getNode(int index) throws IllegalArgumentException {
        if(index < 0)
            throw new IllegalArgumentException("Index can't be less than zero");
        if(index > count)
            throw new IllegalArgumentException("Index can't be more than length");
        Node cur = head;
        if (index < count / 2) {
            for (int i = 0; i < index; ++i)
                cur = cur.next;
        } else {
            for (int i = count - 1; i >= index; --i)
                cur = cur.prev;
        }
        return cur;
    }

    protected Node floorNodeOfX(double x) throws IllegalArgumentException {
        if(x < head.x)
            throw new IllegalArgumentException("x less than left bound of the list");
        Node cur = head;
        int i = 0;
        do {
            if (cur.x >= x)
                return i == 0 ? getNode(0) : getNode(i - 1);
            ++i;
            cur = cur.next;
        } while (cur != head);
        return getNode(count);
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) throws IllegalArgumentException{
        if(xValues.length < 2)
            throw new IllegalArgumentException("The count of the X points must be 2 at least");
        if(xValues.length != yValues.length)
            throw new IllegalArgumentException("The count of the Y points and X points must be the same");
        for (int i = 0; i < xValues.length; ++i) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) throws IllegalArgumentException{
        if(count < 2)
            throw new IllegalArgumentException("The count of the points must be 2 at least");
        if (xFrom > xTo) {
            xFrom = xFrom + xTo;
            xTo = xFrom - xTo;
            xFrom = xFrom - xTo;
        }
        if (xFrom == xTo) {
            for (int i = 0; i < count; i++) {
                addNode(xFrom, source.apply(xFrom));
            }
        } else {
            double deltaX = (xTo - xFrom) / (count - 1);
            double x;
            for (int i = 0; i < count - 1; i++) {
                x = xFrom + i * deltaX;
                addNode(x, source.apply(x));
            }
            if (head != null)
                addNode(xTo, source.apply(xTo));
        }
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    @Override
    public double getX(int index) throws IllegalArgumentException
    {
        if(index < 0)
            throw new IllegalArgumentException("Index can't be less than zero");
        if(index > count)
            throw new IllegalArgumentException("Index can't be more than length");
        return getNode(index).x;
    }

    @Override
    public double getY(int index) throws IllegalArgumentException {
        if(index < 0)
            throw new IllegalArgumentException("Index can't be less than zero");
        if(index > count)
            throw new IllegalArgumentException("Index can't be more than length");
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double y) throws IllegalArgumentException {
        if(index < 0)
            throw new IllegalArgumentException("Index can't be less than zero");
        if(index > count)
            throw new IllegalArgumentException("Index can't be more than length");
        getNode(index).y = y;
    }

    @Override
    public int indexOfX(double x) {
        Node cur = head;
        int i = 0;
        do {
            if (cur.x == x)
                return i;
            ++i;
            cur = cur.next;
        } while (cur != head);
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) throws IllegalArgumentException {
        if(x < head.x)
            throw new IllegalArgumentException("x less than left bound of the list");
        Node cur = head;
        int i = 0;
        do {
            if (cur.x >= x)
                return i == 0 ? 0 : i - 1;
            ++i;
            cur = cur.next;
        } while (cur != head);
        return count;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x,head.x,head.next.x,head.y,head.next.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        Node floorNode = getNode(floorIndex);
        return interpolate(x,floorNode.x,floorNode.next.x,floorNode.y,floorNode.next.y);
    }

    @Override
    public Iterator<Point> iterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOfY(double y) {
        Node cur = head;
        int i = 0;
        do {
            if (cur.y == y)
                return i;
            ++i;
            cur = cur.next;
        } while (cur != head);
        return -1;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public double apply(double x) {
        if (getX(0) > x)
            return extrapolateLeft(x);
        else if (getX(count - 1) < x)
            return extrapolateRight(x);
        else if (indexOfX(x) != -1)
            return getY((indexOfX(x)));
        Node floorNode = floorNodeOfX(x);
        return interpolate(x, floorNode.x, floorNode.next.x, floorNode.y, floorNode.next.y);
    }

    private void changeHead(double x, double y, Node cur) {
        Node newNode = new Node(x, y);
        newNode.next = cur;
        newNode.prev = cur.prev;
        cur.prev.next = newNode;
        cur.prev = newNode;
        head = newNode;
        count++;
    }

    @Override
    public void insert(double x, double y) {
        if (head == null) {
            addNode(x, y);
            return;
        }
        Node cur = head;
        if (cur.x > x) {
            changeHead(x, y, cur);
            return;
        } else if (cur.x == x) {
            cur.y = y;
            return;
        } else if (cur.prev.x < x) {
            addNode(x, y);
            return;
        }
        while (cur.next != head.prev) {
            if (cur.next.x > x) {
                Node newNode = new Node(x, y);
                newNode.next = cur.next;
                newNode.prev = cur;
                cur.next = newNode;
                newNode.next.prev = newNode;
                count++;
                return;
            } else if (cur.next.x == x) {
                cur.next.y = y;
                return;
            }
            cur = cur.next;
        }
    }

    @Override
    public void remove(int index) throws IllegalArgumentException {
        if(index < 0)
            throw new IllegalArgumentException("Index can't be less than zero");
        if(index > count)
            throw new IllegalArgumentException("Index can't be more than length");
        Node remNode = getNode(index);
        remNode.prev.next = remNode.next;
        remNode.next.prev = remNode.prev;
        if (head == remNode)
            head = remNode.next;
        --count;
    }
}
