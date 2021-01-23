
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import javax.imageio.ImageIO;
import net.sf.image4j.codec.ico.ICODecoder;


public class IconCreator {

    private static String icoTOpng(String path, String url, String site){
        try{
            InputStream istr = new URL(url).openStream();
            List<BufferedImage> images = ICODecoder.read(istr);//QUIII
            ImageIO.write(images.get(0), "png", new File(path + site + ".png"));
            return path + site + ".png";
        } catch(Exception e){
            //e.printStackTrace();
        } 
        return null;
    }

    private static String saveImage(String picUrl, String site){
        String path = "C:\\passwordManagerGUI\\Icons\\";
        if (!(new File(path).exists())) {
            new File(path).mkdir();
        }

        site = site.replace(' ', '_');
        String extension = "";
        if (picUrl.equals("")) return null;
        if (picUrl.endsWith(".ico")) {
            return icoTOpng(path, picUrl, site);
        }
        if (picUrl.endsWith(".png")) extension = ".png";
        else if (picUrl.endsWith(".jpg")) extension = ".jpg";
        else if (picUrl.endsWith(".jpeg")) extension = ".jpeg";
        else if (picUrl.endsWith(".gif")) extension = ".gif";
        else if (picUrl.endsWith(".bmp")) extension = ".bmp";
        path = path + site + extension;
        try{
            URL url = new URL(picUrl);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
               out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
    
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(response);
            fos.close();
            
            return path;
        } catch (Exception e){
            //e.printStackTrace();
        }
        return null;
    }

    private static String chooseImage(String picUrl, String line){
        String[] lines = line.split("\"");
        ArrayList<String> sites = new ArrayList<String>();
        String ico = "ico";
        for (String l : lines) {
            if (l.endsWith(".png") || l.endsWith(".jpg") || l.endsWith(".jpeg")) sites.add(l);
            if (l.endsWith(".ico")) ico = l;
        }

        if (sites.size() > 0){
            try{
                BufferedImage bimg;
                if (picUrl.equals("")){
                    bimg = ImageIO.read(new URL(sites.get(0)));
                    picUrl = sites.get(0);
                }else{
                    bimg = ImageIO.read(new URL(picUrl));
                }
                
                for (int i=0; i<sites.size(); i++){
                    BufferedImage bimg2 = ImageIO.read(new URL(sites.get(i)));
                    if (bimg.getWidth() < bimg2.getWidth()){
                        bimg = bimg2;
                        picUrl = sites.get(i);
                    }
                    if (bimg.getWidth() >= 150) return picUrl;
                }
            } catch(Exception e){
                //e.printStackTrace();
            }  
        }
        if (!ico.equals("ico")){
            return ico;
        }
        return picUrl;
    }

    public static String imageUrl(String siteUrl, String siteName){
        siteName = siteName.replace('.', '_');
        if (siteUrl.equals("!")) return null;
        if (!siteUrl.contains(".")) return null;
        String urlPath = "http://favicongrabber.com/api/grab/" + siteUrl;
        String picUrl = "";
        try{
            URL url = new URL(urlPath);
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = read.readLine()) != null){
                picUrl = chooseImage(picUrl, line);                
            }
            read.close();

            return saveImage(picUrl, siteName);
        } catch(Exception e){
            //e.printStackTrace();
        }
        return null;
    }
}
