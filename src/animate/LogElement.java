package animate;

public class LogElement extends logging.LogElement{
    protected long value;

    public LogElement() {
        super();
        value = 0;
    }

    public LogElement(int step, String description, long value){
        this.step=step;
        this.description=description;
        this.value=value;
    }

    public long getValue(){
        return value;
    }

}
