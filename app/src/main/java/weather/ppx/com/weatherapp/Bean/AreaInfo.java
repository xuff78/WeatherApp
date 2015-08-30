package weather.ppx.com.weatherapp.Bean;

/**
 * Created by 可爱的蘑菇 on 2015/8/30.
 */
public class AreaInfo {

    int resId=0;
    float xpos=0f;
    float ypos=0f;

    public AreaInfo(int resId, float xpos, float ypos) {
        this.resId = resId;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public float getXpos() {
        return xpos;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
    }
}
