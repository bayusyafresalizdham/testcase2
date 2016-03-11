package com.test.testcase2;

import java.util.Map;
import java.util.Objects;

/**
 * Created by user on 3/11/16.
 */
public class Model {
    public String title;
    public String artwork_url;
    public String waveform_url;
    public Map<String, Object> user;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    public void setArtwork_url(String artwork_url) {
        this.artwork_url = artwork_url;
    }

    public String getWaveform_url() {
        return waveform_url;
    }

    public void setWaveform_url(String waveform_url) {
        this.waveform_url = waveform_url;
    }


}
