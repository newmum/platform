package test.proxy;

public class TargetImpl implements Target {
    @Override
    public void execute() {
        System.out.println("execute");
    }
}
