package com.nofatclips.crawler.automation;

import java.io.IOException;

import android.graphics.Bitmap;

import com.nofatclips.crawler.model.ImageCaptor;
import com.nofatclips.crawler.storage.ImageStorage;

public class ScreenshotFactory {
	
	private static ImageCaptor theImageCaptor = null;
	private static ImageStorage theImageStorage = null;

	public static ImageStorage getTheImageStorage() {
		return theImageStorage;
	}

	public static void setTheImageStorage(ImageStorage imageStorage) {
		theImageStorage = imageStorage;
	}

	public static ImageCaptor getImageCaptor() {
		return theImageCaptor;
	}

	public static void setImageCaptor(ImageCaptor theCaptor) {
		theImageCaptor = theCaptor;
	}

	public static boolean saveScreenshot(String id) {
		Bitmap b = theImageCaptor.captureImage();
		try {
			theImageStorage.saveImage(b, id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static String getFileExtension() {
		return theImageStorage.imageFormat();
	}
	
}