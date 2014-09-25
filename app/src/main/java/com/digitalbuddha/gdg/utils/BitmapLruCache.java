/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalbuddha.gdg.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
//Image Cache
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache
{
    //default cache size
    public static int getDefaultLruCacheSize(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }
    //constructors
    public BitmapLruCache()
    {
        this(getDefaultLruCacheSize());
    }
    public BitmapLruCache(int sizeInKiloBytes)
    {
        super(sizeInKiloBytes);
    }

    //override methods of LruCache
    @Override
    protected int sizeOf(String key, Bitmap value) {return value.getRowBytes() * value.getHeight() / 1024;}
    @Override
    public Bitmap getBitmap(String url) {       return get(url);}
    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        put(url, bitmap);
    }
}