import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditorWindow{
    private static ImageEditor ie;
    private static JLabel im;
    private static JFrame imageWindow;
    private static JFrame frame;
    private static JPanel panel;
    private static TextField sx = new TextField();
    private static TextField sy = new TextField();
    private static TextField sa = new TextField();
    private static TextField sr = new TextField();
    private static TextField sg = new TextField();
    private static TextField sb = new TextField();

    private static TextField percent = new TextField();

    public static void main (String[] args){  
            ie = null;

            imageWindow = new JFrame();

            frame = new JFrame("Image Editor");
            panel = new JPanel(); 
            
            frame.setVisible(true);
            frame.setSize(1280,720);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(panel);

            JButton openButton = new JButton("Browse Files");
            panel.add(openButton);
            openButton.addActionListener (new Browse()); 

            JButton setPixel = new JButton("Set Pixel");
            panel.add(setPixel);
            setPixel.addActionListener(new SetPixel());

            JButton grayscale = new JButton("Grayscale");
            panel.add(grayscale);
            grayscale.addActionListener(new Grayscale());

            JButton blackAndWhite = new JButton("Black & White");
            panel.add(blackAndWhite);
            blackAndWhite.addActionListener(new BlackAndWhite());

            JButton adjustBrightness = new JButton("Adjust Brightness");
            panel.add(adjustBrightness);
            adjustBrightness.addActionListener(new AdjustBrightness());

            JButton reverseContrast = new JButton("Reverse Contrast");
            panel.add(reverseContrast);
            reverseContrast.addActionListener(new ReverseContrast());

            JButton deepfry = new JButton("Deepfry");
            panel.add(deepfry);
            deepfry.addActionListener(new Deepfry());

            JButton generalize = new JButton("Generalize Colors");
            panel.add(generalize);
            generalize.addActionListener(new Generalize());

            JButton generalizeRGB = new JButton("Generalize RGB");
            panel.add(generalizeRGB);
            generalizeRGB.addActionListener(new GeneralizeRGB());

            JButton flipH = new JButton("Flip Horizontal");
            panel.add(flipH);
            flipH.addActionListener(new FlipH());

            JButton flipV = new JButton("Flip Vertical");
            panel.add(flipV);
            flipV.addActionListener(new FlipV());

            JButton mirror0 = new JButton("Mirror Top");
            panel.add(mirror0);
            mirror0.addActionListener(new Mirror0());

            JButton mirror1 = new JButton("Mirror Bottom");
            panel.add(mirror1);
            mirror1.addActionListener(new Mirror1());

            JButton mirror2 = new JButton("Mirror Left");
            panel.add(mirror2);
            mirror2.addActionListener(new Mirror2());

            JButton mirror3 = new JButton("Mirror Right");
            panel.add(mirror3);
            mirror3.addActionListener(new Mirror3());

            JButton save = new JButton("Save Image");
            panel.add(save);
            save.addActionListener(new Save());

    }
    
    static class Browse implements ActionListener {        
        public void actionPerformed(ActionEvent e) {
            //Handle open button action.
            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnVal = fc.showOpenDialog(null);
    
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                ie = new ImageEditor(fc.getSelectedFile());
                imageWindow = new JFrame(ie.getAbsolutePath());
                im = new JLabel();
                //This is where a real application would open the file.
                System.out.println("Opening: " + ie.getName() + "."/* + newline*/);
                addImage();
            } else {
                System.out.println("Open command cancelled by user."/* + newline*/);
            }
        }
    }

    public static void addImage(){ 
        im.setIcon(null);
        im.setVisible(true);
        //ie.save(); // EVIL, CAUSED SO MANY PROBLEMS
        imageWindow.setName(ie.getAbsolutePath());
        imageWindow.setAlwaysOnTop(true);
        System.out.println("Displaying: " + ie.getAbsolutePath());
        imageWindow.setVisible(true);
        imageWindow.add(im);
        im.setIcon(null);
        ImageIcon ii = new ImageIcon(ie.getAbsolutePath());
        ii.getImage().flush();
        im.setIcon(scaleDown(ii));
        imageWindow.pack();
        

    }

    public static void deleteImage(String path){
        File f = new File("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
        if (f.delete()) { 
        System.out.println("Deleted the file: " + f.getName());
        } else {
        System.out.println("Failed to delete the file.");
        } 
    }


    public static void onActionTrigger(Action a){
        im.setIcon(new ImageIcon(ie.save().getAbsolutePath()));
        
    }

    public static ImageIcon scaleDown(ImageIcon imageIcon){
        int width = ie.getWidth();
        int height = ie.getHeight();

        if(width > 1280){
            float scalar = 1280f / (float)width;
            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance((int)(1280*scalar), (int)(720*scalar),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  // transform it back
        }
        if(height > 720){
            float scalar = 720f / (float)height;
            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance((int)(1280*scalar), (int)(720*scalar),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  // transform it back
        }
        return imageIcon;
    }


    static class SetPixel implements ActionListener { 
        public void actionPerformed(ActionEvent e){
            JFrame sp = new JFrame("Set Pixel");
            sp.setVisible(true);
            sp.setSize(300, 40);
            JLabel label = new JLabel("Choose Coordinates");
            JPanel p = new JPanel();

            sx = new TextField("x", 5);
            p.add(sx);
            sy = new TextField("y", 5);
            p.add(sy);
            sa = new TextField("alpha", 3);
            p.add(sa);
            sr = new TextField("red", 3);
            p.add(sr);
            sg = new TextField("green", 3);
            p.add(sg);
            sb = new TextField("blue", 3);
            p.add(sb);

            sp.add(p);
            p.add(label); 
            

            
            JButton set = new JButton("Set");
            p.add(set);
            set.addActionListener (new Set());
        }
    }  
    static class Set implements ActionListener { 
        public void actionPerformed(ActionEvent ee){
            int x = 0;
            int y = 0;
            int a = 0;
            int r = 0;
            int g = 0;
            int b = 0;
            System.out.println("sx.text={"+sx.getText()+"}");
            try{ x = Integer.parseInt(sx.getText()); }
            catch(Exception ex){ panel.add(new JLabel("Please enter a number.")); }
            try{ y = Integer.parseInt(sy.getText()); }
            catch(Exception ex){ panel.add(new JLabel("Please enter a number.")); } 
            try{ a = Integer.parseInt(sa.getText()); }
            catch(Exception ex){ panel.add(new JLabel("Please enter a number.")); }
            try{ r = Integer.parseInt(sr.getText()); }
            catch(Exception ex){ panel.add(new JLabel("Please enter a number.")); }
            try{ g = Integer.parseInt(sg.getText()); }
            catch(Exception ex){ panel.add(new JLabel("Please enter a number.")); }
            try{ b = Integer.parseInt(sb.getText()); }
            catch(Exception ex){ panel.add(new JLabel("Please enter a number.")); }
            
            ie.setPixel(x, y, a, r, g, b);
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class Grayscale implements ActionListener { 
        public void actionPerformed(ActionEvent ee){
            ie.grayscale();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class BlackAndWhite implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.blackAndWhite();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class AdjustBrightness implements ActionListener { 
        public void actionPerformed(ActionEvent e){
            JFrame ab = new JFrame("Adjust Brightness");
            ab.setVisible(true);
            ab.setSize(300, 40);
            JLabel label = new JLabel("Choose Adjustment %");
            JPanel p = new JPanel();

            percent = new TextField("%", 5);
            p.add(percent);

            ab.add(p);
            p.add(label); 
            

            
            JButton apply = new JButton("Apply");
            p.add(apply);
            apply.addActionListener (new Apply());
        }
    }  
    static class Apply implements ActionListener { 
        public void actionPerformed(ActionEvent ee){
            int perc = 0;
            System.out.println("ab.text={"+percent.getText()+"}");
            try{ perc = Integer.parseInt(percent.getText()); }
            catch(Exception ex){ panel.add(new JLabel("Please enter a number.")); }
            
            ie.adjustBrightness(perc);
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class ReverseContrast implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.reverseContrast();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }   

    static class Deepfry implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.deepfry();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }   

    static class Generalize implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.generalize();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class GeneralizeRGB implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.generalizeRGB();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class FlipH implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.flipH();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class FlipV implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.flipV();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class Mirror0 implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.mirror0();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class Mirror1 implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.mirror1();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class Mirror2 implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.mirror2();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class Mirror3 implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.mirror3();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            addImage();
        }
    }

    static class Save implements ActionListener {
        public void actionPerformed(ActionEvent ee){
            ie.mirror0();
            ie.save("./Temp/" + ie.getName().substring(0, ie.getName().indexOf(".")) + "(edit)" + ie.getName().substring(ie.getName().indexOf(".")));
            ie.save();
            System.out.println("Saving: " + ie.getAbsolutePath());
            addImage();
        }
    }
}