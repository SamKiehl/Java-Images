import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageEditor{

    private File f = null;
    private BufferedImage img = null;
    private String name = null;
    private int width, height, p = 0;

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public File getFile(){ return f; }
    public BufferedImage getImg(){ return img; }
    public String getName(){ return name; }
    public String getAbsolutePath(){ return f.getAbsolutePath(); }

    public ImageEditor(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Image Filepath (include file extension): ");
        this.name = scanner.nextLine();
        scanner.close();
        //read image
        try{
        f = new File("./Input Images/" + name);
        img = ImageIO.read(f);
        }catch(IOException e){ e.printStackTrace(); }

        //get image width and height
        width = img.getWidth();
        height = img.getHeight();
    }

    public ImageEditor(File file){
        img = null;
        f = file;
        name = f.getName();
        //read image
        try{
            img = ImageIO.read(f);
        }catch(IOException e){ e.printStackTrace(); }

        //get image width and height
        width = img.getWidth();
        height = img.getHeight();
    }

    public int[] getRGBArr(int x, int y){
        int rgb = img.getRGB(x, y);
        int aa = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        int[] output = {aa, red, green, blue};
        return output;
    }
    
    public void setPixel(int x, int y, int a, int r, int g, int b){
        p = img.getRGB(0,0);
        p = (a<<24) | (r<<16) | (g<<8) | b;
        img.setRGB(x, y, p);
    }

    public static int getGrayscaleValue(int r, int g, int b){ return (r + g + b) / 3; }

    public void grayscale(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int[] rgb = getRGBArr(i, j);
                int gray = getGrayscaleValue(rgb[1], rgb[2], rgb[3]);
                setPixel(i, j, rgb[0], gray, gray, gray);
            }
        }
    }

    public void blackAndWhite(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int[] rgb = getRGBArr(i, j);
                if(getGrayscaleValue(rgb[1], rgb[2], rgb[3]) > 127)
                    setPixel(i, j, rgb[0], 255, 255, 255);
                else
                    setPixel(i, j, rgb[0], 0, 0, 0);

            }
        }
    }

    public void adjustBrightness(int percent){
        float multiplier = 1 + percent/100;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int[] rgb = getRGBArr(i, j);
                int r2 = (int)Math.max(0, Math.min(255, rgb[1] * multiplier));
                int g2 = (int)Math.max(0, Math.min(255, rgb[2] * multiplier));
                int b2 = (int)Math.max(0, Math.min(255, rgb[3] * multiplier));
                setPixel(i, j, rgb[0], r2, g2, b2);
            }
        }
    }

    public void reverseContrast(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int[] rgb = getRGBArr(i, j);
                int r2 = 255 - rgb[1];
                int g2 = 255 - rgb[2];
                int b2 = 255 - rgb[3];
                setPixel(i, j, rgb[0], r2, g2, b2);
            }
        }
    }

    public void deepfry(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int[] rgb = getRGBArr(i, j);

                int r2 = rgb[1];
                if(rgb[1] > rgb[2] && rgb[1] > rgb[3])
                    r2 = (int)Math.max(0, Math.min(255, rgb[1] * rgb[1]/2));
                int g2 = (int)Math.max(0, Math.min(255, rgb[2] / 2));
                int b2 = (int)Math.max(0, Math.min(255, rgb[3] / 2));
                setPixel(i, j, rgb[0], r2, g2, b2);
            }
        }
        adjustBrightness(50);
    }

    public void generalize(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int[] rgb = getRGBArr(i, j);
                int r2 = rgb[1];
                int g2 = rgb[2];
                int b2 = rgb[3];
                if(r2 > g2 && r2 > b2)
                    setPixel(i, j, rgb[0], r2, 0, 0);
                else if(g2 > r2 && g2 > b2)
                    setPixel(i, j, rgb[0], 0, g2, 0);
                else if(b2 > r2 && b2 > g2)
                    setPixel(i, j, rgb[0], 0, 0, b2);
                else
                    setPixel(i, j, rgb[0], 0, 0, 0);
            }
        }
    }

    public void generalizeRGB(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int[] rgb = getRGBArr(i, j);
                int r2 = rgb[1];
                int g2 = rgb[2];
                int b2 = rgb[3];
                if(r2 > g2 && r2 > b2)
                    setPixel(i, j, rgb[0], 255, 0, 0);
                else if(g2 > r2 && g2 > b2)
                    setPixel(i, j, rgb[0], 0, 255, 0);
                else if(b2 > r2 && b2 > g2)
                    setPixel(i, j, rgb[0], 0, 0, 255);
                else
                    setPixel(i, j, rgb[0], 0, 0, 0);
            }
        }
    }

    public void flip(boolean vertical){
        int[][] pixels = new int[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                pixels[vertical ? i : width - 1 - i][vertical ? height - 1 - j : j] = img.getRGB(i, j);
            }
        }
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                img.setRGB(i, j, pixels[i][j]);
            }
        }
    }

    public void flipH(){ flip(false); }

    public void flipV(){ flip(true); }

    public void mirror(int sector){
        switch(sector){
            case 0:
                for(int i = 0; i < width; i++)
                    for(int j = height/2; j >= 0; j--)
                        img.setRGB(i, height -1 - j, img.getRGB(i, j));
                break;
            case 1:
                for(int i = 0; i < width; i++)
                    for(int j = height/2+1; j < height; j++)
                        img.setRGB(i, height-1-j, img.getRGB(i, j));
                break;
            case 2:
                for(int i = width/2; i >= 0; i--)
                    for(int j = 0; j < height-1; j++)
                        img.setRGB(width - 1 - i, j, img.getRGB(i, j));
                break;
            case 3:
                for(int i = width/2+1; i < width; i++){
                    for(int j = 0; j < height-1; j++){
                        img.setRGB(width-1-i, j, img.getRGB(i, j));
                    }
                }
                break;
        }
    }

    public void mirror0(){ mirror(0); }
    
    public void mirror1(){ mirror(1); }

    public void mirror2(){ mirror(2); }

    public void mirror3(){ mirror(3); }

    public File save(){
        try{
            f = new File("./Output Images/" + name.substring(0, name.indexOf(".")) + "(edit)" + name.substring(name.indexOf(".")));
            ImageIO.write(img, "png", f);
            img.flush();
            return f;
        }catch(IOException e){
            System.out.println(e);
        }
        return null;
    }

    public File save(String path){
        try{
            f = new File(path);
            ImageIO.write(img, "png", f);
            img.flush();
            return f;
        }catch(IOException e){
            System.out.println(e);
        }
        return null;
    }
}