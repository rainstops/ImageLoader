package com.rainstops.imageloader;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * @file ImageDownloader.java
 * @author RotineYoung
 * @brief  
 * @date 2015-2-26 create
 *
 */
public class ImageDownloader {

	public Bitmap downloadImage(String path, int width, int height) {
		return ImageResizer.decodeSampledBitmapFromFile(path, width, height);
	}
	
	public Bitmap downloadApk(Context context, String path, int width, int height) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
			if (info != null) {
				ApplicationInfo appInfo = info.applicationInfo;
				appInfo.sourceDir = path;
				appInfo.publicSourceDir = path;
				Drawable drawable = pm.getApplicationIcon(appInfo);
				return ((BitmapDrawable) drawable).getBitmap();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Bitmap downloadVideo(String path, int width, int height) {
		Bitmap bitmap = null;
		bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MICRO_KIND);
		return bitmap;
	}
	
	public Bitmap downloadAudio(String path, int width, int height) {
		Bitmap album = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(path);
			byte[] albumBytes = retriever.getEmbeddedPicture();
			album = BitmapFactory.decodeByteArray(albumBytes, 0, albumBytes.length);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			retriever.release();
		}
		return album;
	}
}
