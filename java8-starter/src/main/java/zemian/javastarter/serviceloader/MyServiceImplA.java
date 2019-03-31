package zemian.javastarter.serviceloader;

public class MyServiceImplA implements MyService {
    public MyServiceImplA(String param) {
        System.out.println(param);
    }

    @Override
    public void run() {
        System.out.println("MyServiceImplA");
    }
}
