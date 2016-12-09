package fish.payara.nucleus.hazelcast;

import java.util.List;


public class SillyClassLoader extends ClassLoader {
    private List<ClassLoader> appClassloaders;

    public SillyClassLoader(ClassLoader parent, List<ClassLoader> appClassloaders) {
        super(parent);
        this.appClassloaders = appClassloaders;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException e) {
            for (ClassLoader cl : appClassloaders) {
                try {
                    return cl.loadClass(name);
                } catch (ClassNotFoundException ignored) { }
            }
            throw e;
        }
    }
}
