package library.app;

public class LibraryApp {
    private final static String APP_NAME = "Library v1.8";

    public static void main(String[] args) {
        System.out.println(APP_NAME);
        LibraryControl libControl = new LibraryControl();
        libControl.controlLoop();
    }
}
