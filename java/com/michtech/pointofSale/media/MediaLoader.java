package com.michtech.pointofSale.media;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

public class MediaLoader implements AlbumLoader {
    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(com.google.android.gms.cast.framework.R.drawable.cast_album_art_placeholder)
                .placeholder(com.google.android.gms.cast.framework.R.drawable.cast_album_art_placeholder)
                .into(imageView);
    }
}
