package functions;

public class LinkedListTabulatedFunction extends AbstractTabulateFunction implements TabulatedFunction, Insertable, Removable {

    class Node {
        public Node next;
        public Node prev;
        public double x;
        public double y;

        public Node(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        public Node(final double x, final double y, final Node next, final Node prev) {
            this(x, y);
            this.next = next;
            this.prev = prev;
        }
    }

    private Node head;

    private void addNode(final double x, final double y) {
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

    private Node getNode(int index) {
        Node cur = head;
        if (index < count / 2) {
            for (int i = 0; i < index; ++i)
                cur = cur.next;
        } else {
            for (int i = count - 1; i >= index; --i) // подумать
                cur = cur.prev;

        }
        return cur;
    }

    protected Node floorNodeOfX(double x) {
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

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        for (int i = 0; i < xValues.length; ++i) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
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
    public double getX(int index) {
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double y) {
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
    protected int floorIndexOfX(double x) {
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
        if (count == 1)
            return head.y;
        return getNode(0).y + (getNode(1).y - getNode(0).y) / (getNode(1).x - getNode(0).x) * (x - getNode(0).x);

    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1)
            return head.y;
        return getNode(count - 2).y + (getNode(count - 1).y - getNode(count - 2).y) / (getNode(count - 1).x - getNode(count - 2).x) * (x - getNode(count - 2).x);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1)
            return head.y;
        return getNode(floorIndex).y + (getNode(floorIndex + 1).y - getNode(floorIndex).y) / (getNode(floorIndex + 1).x - getNode(floorIndex).x) * (x - getNode(floorIndex).x);
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
        if (getX(0) > 0)
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
    public void remove(int index) {
        Node remNode = getNode(index);
        remNode.prev.next = remNode.next;
        remNode.next.prev = remNode.prev;
        if (head == remNode)
            head = remNode.next;
        --count;
    }
}
