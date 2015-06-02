/*
*
*  Copyright 2015 University of Wisconsin - Parkside
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*
*
*/


package u.ready_wisc.Emergency_Main;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by nathaneisner on 5/29/15.
 * License Apache 2.0
 */
public class ImagesCache {

    private LruCache<String, Bitmap> imagesWarehouse;

    private static ImagesCache cache;
    private static int imageCount;

    public static ImagesCache getInstance() {
        if (cache == null) {
            cache = new ImagesCache();
        }

        return cache;
    }

    public void initializeCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        imageCount = 0;
        //find available memory
        final int cacheSize = maxMemory / 8;
        //use 1/8 of available heap to cache images
        System.out.println("cache size = " + cacheSize);

        imagesWarehouse = new LruCache<String, Bitmap>(cacheSize) {
            protected int sizeOf(String key, Bitmap value) {
                // The cache size will be measured in kilobytes rather than number of items.

                int bitmapByteCount = value.getRowBytes() * value.getHeight();

                return bitmapByteCount / 1024;
            }
        };
    }

    public void addImage(Bitmap value) {
        String key = String.valueOf(imageCount);
        if (imagesWarehouse != null && imagesWarehouse.get(key) == null) {
            imageCount ++;
            imagesWarehouse.put(key, value);
        }
    }

    public Bitmap getImage(String key) {
        if (key != null) {
            return imagesWarehouse.get(key);
        } else {
            return null;
        }
    }

    public void removeImage(String key) {
        imageCount --;
        imagesWarehouse.remove(key);
    }

    public void clearCache() {
        if (imagesWarehouse != null) {
            imageCount = 0;
            imagesWarehouse.evictAll();
        }
    }

    public int getImageCount() {
        return imageCount;
    }

}
