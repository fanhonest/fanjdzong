package fan.com.jd02.shouye.ashouyemian;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by fxf on 2018.05.17.
 */

public class BannerImageLoad extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);
        Glide.with(context).load(path).into(imageView);
//        com.nostra13.universalimageloader.core.ImageLoader instance = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
//        instance.displayImage((String) path, imageView);
    }
}