import javax.swing.UIManager;

public class App {
    public static void main(String[] args) throws Exception {
        try { // Make sure the UI works for MacOS
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calculator calculator = new Calculator();
    }
}
