package Model;



public abstract class MyEvent extends java.util.EventObject {
    //here's the constructor
    public MyEvent(Object source) {
        super(source);
    }
}
