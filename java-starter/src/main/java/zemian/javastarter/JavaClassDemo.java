package zemian.javastarter;

public class JavaClassDemo {
    public static void main(String[] args) {
        Class<?> cls = int.class;
        System.out.println(cls);
        System.out.println(cls.getName());
        System.out.println(cls.getCanonicalName());
        System.out.println(cls.getTypeName());
        System.out.println(cls.getClass());
        System.out.println(cls.getComponentType());
    }
}
