package com.moelive.live.demo.player.model;

import com.moelive.live.demo.player.activity.LocalFragment;
import com.moelive.live.demo.player.util.Video;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * Created by liubohua on 16/7/15.
 */
public class GetList {
    private ArrayList<Video> mylist;

    public void getFileList(final ArrayList<Video> list, final File file) {

        getVideoFile(list, file);
        mylist = new ArrayList<Video>();
        LocalFragment.mHandler.obtainMessage(3, mylist).sendToTarget();


    }

    public void getVideoFile(final ArrayList<Video> list, final File file) {// 获得视频文件

        new Thread(new Runnable() {
            @Override
            public void run() {
                file.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(File file) {
                        // sdCard找到视频名称
                        String name = new String(file.getName());

                        int i = name.lastIndexOf('.');
                        if (i != -1) {
                            name = name.substring(i);
                            if (name.equalsIgnoreCase(".mp4")
                                    || name.equalsIgnoreCase(".3gp")
                                    || name.equalsIgnoreCase(".wmv")
                                    || name.equalsIgnoreCase(".ts")
                                    || name.equalsIgnoreCase(".rmvb")
                                    || name.equalsIgnoreCase(".mov")
                                    || name.equalsIgnoreCase(".m4v")
                                    || name.equalsIgnoreCase(".avi")
                                    || name.equalsIgnoreCase(".m3u8")
                                    || name.equalsIgnoreCase(".3gpp")
                                    || name.equalsIgnoreCase(".3gpp2")
                                    || name.equalsIgnoreCase(".mkv")
                                    || name.equalsIgnoreCase(".flv")
                                    || name.equalsIgnoreCase(".divx")
                                    || name.equalsIgnoreCase(".f4v")
                                    || name.equalsIgnoreCase(".rm")
                                    || name.equalsIgnoreCase(".asf")
                                    || name.equalsIgnoreCase(".ram")
                                    || name.equalsIgnoreCase(".mpg")
                                    || name.equalsIgnoreCase(".v8")
                                    || name.equalsIgnoreCase(".swf")
                                    || name.equalsIgnoreCase(".m2v")
                                    || name.equalsIgnoreCase(".asx")
                                    || name.equalsIgnoreCase(".ra")
                                    || name.equalsIgnoreCase(".ndivx")
                                    || name.equalsIgnoreCase(".xvid")) {
                                Video v = new Video();
                                v.setTitle(file.getName());
                                v.setPath(file.getAbsolutePath());
                                list.add(v);
                                mylist.add(v);
                                if ((list.size() == 15)) {
                                    LocalFragment.mHandler.obtainMessage(2, mylist).sendToTarget();
                                }
                                return true;
                            }
                        } else if (file.isDirectory()) {
                            Video v = new Video();
                            v.setTitle(file.getName());
                            v.setPath(file.getAbsolutePath());
                            list.add(v);
                            mylist.add(v);
                            if ((list.size() == 15)) {
                                LocalFragment.mHandler.obtainMessage(2, mylist).sendToTarget();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
        }).start();

    }
}
