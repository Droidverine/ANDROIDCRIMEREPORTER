package com.droidverine.androidcrimereporter.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;


public class MySingltone {
    private  static MySingltone mInstance;
    private RequestQueue requestQueue;
    private Context mctx;
    ImageLoader mImageLoader;

    public MySingltone(Context context) {
        mctx=context;
        requestQueue=getRequestQueue();
        mImageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }
    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(mctx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            requestQueue.start();
        }

//    { if (requestQueue==null)
//    {
//requestQueue= Volley.newRequestQueue(mctx.getApplicationContext());
//
//    }
        return  requestQueue;
    }
    public  synchronized MySingltone getmInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance=new MySingltone(context);



        }
return mInstance;
    }
    public<T> void addTorequestquere(Request<T> request)
    {
        requestQueue.add(request);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
