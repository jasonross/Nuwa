package cn.jiajixin.nuwasample;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import cn.jiajixin.nuwa.Nuwa;

/**
 * Created by jixin.jia on 15/11/2.
 */
public class NuwaApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Nuwa.init(this);
        Nuwa.loadPatch(this, Environment.getExternalStorageDirectory().getAbsolutePath().concat("/patch.jar"));
    }
}
